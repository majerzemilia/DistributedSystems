import java.util.List;

import org.apache.zookeeper.*;;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.data.Stat;

public class DataMonitor implements Watcher, AsyncCallback.StatCallback, AsyncCallback.Children2Callback {

    private final ZooKeeper zk;
    private final String znode;
    boolean dead;
    private final DataMonitorListener listener;
    private int childrenCount;

    public DataMonitor(ZooKeeper zk, String znode, DataMonitorListener listener) {
        this.zk = zk;
        this.znode = znode;
        this.listener = listener;
        this.childrenCount = 0;
        zk.exists(znode, true, this, null);
        zk.getChildren(znode, true, this, null);
    }

    public interface DataMonitorListener {
        void handleProgram(boolean exists);
        void closing(int rc);
    }

    public void process(WatchedEvent event) {
        String path = event.getPath();
        if (event.getType() == Event.EventType.None) {
            switch (event.getState()) {
                case SyncConnected:
                    break;
                case Expired:
                    dead = true;
                    listener.closing(KeeperException.Code.SessionExpired);
                    break;
            }
        }
        else if(event.getType() == Event.EventType.NodeChildrenChanged){
            zk.getChildren(path, true, this, null);
        }
        else {
            if (path != null && path.equals(znode)) {
                zk.exists(znode, true, this, null);
                zk.getChildren(znode, true, this, null);
            }
        }
    }

    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        boolean exists;
        switch (rc) {
            case Code.Ok:
                exists = true;
                break;
            case Code.NoNode:
                exists = false;
                break;
            case Code.SessionExpired:
            case Code.NoAuth:
                dead = true;
                listener.closing(rc);
                return;
            default:
                zk.exists(znode, true, this, null);
                return;
        }
        listener.handleProgram(exists);
    }

    @Override
    public void processResult(int rc, String path, Object ctx, List<String> children, Stat stat) {
        for(String child: children){
            zk.getChildren(path + "/" + child, true, this, null);
        }
        int childrenCount = countChildren(znode) - 1;
        //if(childrenCount < this.childrenCount) System.out.println("Removed child. Children count = " + childrenCount);
        if(childrenCount > this.childrenCount) System.out.println("Added child. Children count = " + childrenCount);
        this.childrenCount = childrenCount;
    }

    private int countChildren(String path){
        int childrenCount = 1;
        try {
            List<String> children = zk.getChildren(path, false);
            for(String child: children){
                childrenCount += countChildren(path + "/" + child);
            }
        } catch (KeeperException.NoNodeException e){
            //it means that the node has been deleted
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return childrenCount;
    }
}