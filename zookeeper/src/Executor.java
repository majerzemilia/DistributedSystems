import java.io.*;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class Executor implements Watcher, Runnable, DataMonitor.DataMonitorListener{
    private final String znode;
    private final DataMonitor dm;
    private final ZooKeeper zk;
    private final String exec[];
    private Process child;

    public Executor(String hostPort, String exec[]) throws KeeperException, IOException {
        this.znode = "/z";
        this.exec = exec;
        zk = new ZooKeeper(hostPort, 3000, this);
        dm = new DataMonitor(zk, znode, this);
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Wrong number of arguments. Expected: hostPort program[args ...]");
            System.exit(2);
        }
        String hostPort = args[0];
        String exec[] = new String[args.length - 1];
        System.arraycopy(args, 1, exec, 0, exec.length);
        Executor executor = null;
        try {
            executor = new Executor(hostPort, exec);
        } catch (KeeperException | IOException e) {
            e.printStackTrace();
        }
        Executor finalExecutor = executor;
        new Thread(new Runnable() {
            @Override
            public void run() {
                assert finalExecutor != null;
                finalExecutor.listenForCommands();
            }
        }).start();
        executor.run();
    }

    private void listenForCommands(){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter ls to see /z tree's structure");
        while (true) {
            try {
                String line = br.readLine();
                if(line.equals("ls")) showStructure(this.znode);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showStructure(String path){
        try {
            List<String> children = zk.getChildren(path, false);
            System.out.println(path);
            for(String child: children) showStructure(path + "/" + child);
        } catch (KeeperException.NoNodeException e){
            System.out.println("Node does not exist");
        } catch (KeeperException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void process(WatchedEvent event) {
        dm.process(event);
    }

    public void run() {
        try {
            synchronized (this) {
                while (!dm.dead) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void closing(int rc) {
        synchronized (this) {
            notifyAll();
        }
    }

    public void handleProgram(boolean exists) {
        if (!exists) {
            if (child != null) {
                System.out.println("Killing process");
                child.destroy();
                try {
                    child.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            child = null;
        } else {
            try {
                System.out.println("Starting child");
                child = Runtime.getRuntime().exec(exec);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}