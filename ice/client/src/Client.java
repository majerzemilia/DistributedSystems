import SmartHouse.*;
import com.zeroc.Ice.*;

import java.io.IOException;
import java.lang.Exception;

public class Client {

    private static java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));

    private static int chooseDevice() throws IOException {
        System.out.println("Available devices:\nFridge - 1\nLamp - 2\nCoffeeMachine - 3\nTeaMachine - 4\n");
        int device = -1;
        String line = null;
        while(device == -1){
            device = new Integer(in.readLine());
            if(device > 4 || device < 1) device = -1;
        }
        return device;
    }

    private static int chooseFridgeAction() throws IOException {
        System.out.println("Available actions:\nChange device - 0\nGet power state - 1\nTurn On - 2\nTurn Off - 3\n" +
                "Get temperature - 4\nSet temperature - 5\n");
        int action = -1;
        while(action == -1){
            action = new Integer(in.readLine());
            if(action > 5 || action < 0) action = -1;
        }
        return action;
    }

    private static int chooseLampAction() throws IOException {
        System.out.println("Available actions:\nChange device - 0\nGet power state - 1\nTurn On - 2\nTurn Off - 3\n" +
                "Get color - 4\nSet color - 5");
        int action = -1;
        while(action == -1){
            action = new Integer(in.readLine());
            if(action > 5 || action < 0) action = -1;
        }
        return action;
    }

    private static void checkForDefaultAction(IDevicePrx object, int action){
        switch(action) {
            case 1:
                PowerState state = object.getPowerState();
                System.out.println("Device power state: " + state + "\n");
                break;
            case 2:
                try {
                    object.turnOn();
                } catch (AlreadyOnError alreadyOnError) {
                    printError(alreadyOnError);
                }
                System.out.println("Action completed!\n");
                break;
            case 3:
                try {
                    object.turnOff();
                } catch (AlreadyOffError alreadyOffError) {
                    printError(alreadyOffError);
                }
                System.out.println("Action completed!\n");
                break;
        }
    }

    private static void makeFridgeAction(FridgePrx fridge, int action) throws IOException {
        checkForDefaultAction(fridge, action);
        switch(action){
            case 4:
                float temperature = 0;
                try {
                    temperature = fridge.getTemperature();
                } catch (IsOffError isOffError) {
                    printError(isOffError);
                }
                System.out.println("Current temperature: " + temperature + "\n");
                break;
            case 5:
                System.out.println("Enter temperature:\n");
                temperature = new Float(in.readLine());
                try {
                    fridge.setTemperature(temperature);
                } catch (IsOffError | OutOfRangeError error) {
                    printError(error);
                }
                System.out.println("Action completed!\n");
        }
    }

    private static void makeLampAction(LampPrx lamp, int action) throws IOException {
        checkForDefaultAction(lamp, action);
        switch(action){
            case 4:
                Color color = null;
                try {
                    color = lamp.getColor();
                } catch (IsOffError isOffError) {
                    printError(isOffError);
                }
                System.out.println("Current color: " + color + "\n");
                break;
            case 5:
                System.out.println("Enter color from: RED, BLUE, GREEN, YELLOW, WHITE (default=YELLOW)\n");
                String c = in.readLine();
                color = decodeColor(c);
                try {
                    lamp.setColor(color);
                } catch (IsOffError error) {
                    printError(error);
                }
                System.out.println("Action completed!\n");
        }
    }

    private static Color decodeColor(String colorname){
        switch(colorname){
            case "RED": return Color.RED;
            case "BLUE": return Color.BLUE;
            case "GREEN": return Color.GREEN;
            case "WHITE": return Color.WHITE;
            default: return Color.YELLOW;
        }
    }

    private static void checkForDrinkMachineAction(IDrinkMachinePrx machine, int action) throws IOException {
        switch(action){
            case 6:
                System.out.println("Enter spoons of sugar:\n");
                int spoons = new Integer(in.readLine());
                try {
                    machine.addSugar(spoons);
                } catch (NoIngredientsError | IsOffError | OutOfRangeError error) {
                    printError(error);
                }
                System.out.println("Action completed!\n");
                break;
            case 7:
                System.out.println("Enter mililitres of milk:\n");
                int mililitres = new Integer(in.readLine());
                try {
                    machine.addSugar(mililitres);
                } catch (NoIngredientsError | IsOffError | OutOfRangeError error) {
                    printError(error);
                }
                System.out.println("Action completed!\n");
                break;
        }
    }

    private static int chooseCoffeeMachineAction() throws IOException {
        System.out.println("Available actions:\nChange device - 0\nGet power state - 1\nTurn On - 2\nTurn Off - 3\n" +
                "Make coffee - 4\nGet available coffee types - 5\nAdd sugar - 6\nAdd milk - 7");
        int action = -1;
        while(action == -1){
            action = new Integer(in.readLine());
            if(action > 7 || action < 0) action = -1;
        }
        return action;
    }

    private static void makeCoffeeMachineAction(CoffeeMachinePrx coffeeMachine, int action) throws IOException {
        checkForDefaultAction(coffeeMachine, action);
        checkForDrinkMachineAction(coffeeMachine, action);
        switch(action){
            case 4:
                System.out.println("Enter type of coffee from BLACK, WHITE, LATTE, ESPRESSO (default=BLACK):\n");
                String c = in.readLine();
                CoffeeType type = decodeCoffeeType(c);
                try {
                    coffeeMachine.makeCoffee(type);
                } catch (IsOffError | NoIngredientsError error) {
                    printError(error);
                }
                System.out.println("Action completed!\n");
                break;
            case 5:
                try {
                    CoffeeType[] types = coffeeMachine.getAvailableCoffeeTypes();
                    for(CoffeeType availableType: types) System.out.println(availableType);
                } catch (IsOffError isOffError) {
                    printError(isOffError);
                }
                System.out.println("Action completed!\n");
                break;
        }
    }

    private static CoffeeType decodeCoffeeType(String type){
        switch(type){
            case "WHITE": return CoffeeType.WHITE;
            case "ESPRESSO": return CoffeeType.ESPRESSO;
            case "LATTE": return CoffeeType.LATTE;
            default: return CoffeeType.BLACK;
        }
    }

    private static int chooseTeaMachineAction() throws IOException {
        System.out.println("Available actions:\nChange device - 0\nGet power state - 1\nTurn On - 2\nTurn Off - 3\n" +
                "Make tea - 4\nGet available tea types - 5\nAdd sugar - 6\nAdd milk - 7");
        int action = -1;
        while(action == -1){
            action = new Integer(in.readLine());
            if(action > 7 || action < 0) action = -1;
        }
        return action;
    }

    private static void makeTeaMachineAction(TeaMachinePrx teaMachine, int action) throws IOException {
        checkForDefaultAction(teaMachine, action);
        checkForDrinkMachineAction(teaMachine, action);
        switch(action){
            case 4:
                System.out.println("Enter type of coffee from BLACK, GREEN, WHITE:\n");
                String t = in.readLine();
                TeaType type = decodeTeaType(t);
                try {
                    teaMachine.makeTea(type);
                } catch (IsOffError | NoIngredientsError error) {
                    printError(error);
                }
                System.out.println("Action completed!\n");
                break;
            case 5:
                try {
                    TeaType[] types = teaMachine.getAvailableTeaTypes();
                    for(TeaType availableType: types) System.out.println(availableType);
                } catch (IsOffError isOffError) {
                    printError(isOffError);
                }
                System.out.println("Action completed!\n");
                break;
        }
    }

    private static TeaType decodeTeaType(String type){
        switch(type){
            case "GREEN": return TeaType.GREEN;
            case "WHITE": return TeaType.WHITE;
            default: return TeaType.BLACK;
        }
    }

    private static void printError(SmartHouse.Error error){
        System.out.println("Time: " + error.errorTime.hour + ":" + error.errorTime.minute + ":" + error.errorTime.second
                + "\n" + error.reason);
    }

    public static void main(String[] args)
    {
        int status = 0;
        Communicator communicator = null;

        try {
            communicator = Util.initialize(args);
            ObjectPrx fridgeObj = communicator.stringToProxy("fridge:default -p 10000");
            ObjectPrx lampObj = communicator.stringToProxy("lamp:default -p 10000");
            ObjectPrx coffeeMachineObj = communicator.stringToProxy("coffeeMachine:default -p 10000");
            ObjectPrx teaMachineObj = communicator.stringToProxy("teaMachine:default -p 10000");

            FridgePrx fridge = FridgePrx.checkedCast(fridgeObj);
            LampPrx lamp = LampPrx.checkedCast(lampObj);
            CoffeeMachinePrx coffeeMachine = CoffeeMachinePrx.checkedCast(coffeeMachineObj);
            TeaMachinePrx teaMachine = TeaMachinePrx.checkedCast(teaMachineObj);
            if (fridge == null || lamp == null || coffeeMachine == null || teaMachine == null)
                throw new java.lang.Error("Invalid proxy");

            int device = -1;
            int action = -1;
            device = chooseDevice();
            while (true) {
                try {
                    while(device == 0){
                        device = chooseDevice();
                    }
                    while(device == 1){
                        action = chooseFridgeAction();
                        if(action == 0) device = 0;
                        else makeFridgeAction(fridge, action);
                    }
                    while(device == 2){
                        action = chooseLampAction();
                        if(action == 0) device = 0;
                        else makeLampAction(lamp, action);
                    }
                    while(device == 3){
                        action = chooseCoffeeMachineAction();
                        if(action == 0) device = 0;
                        else makeCoffeeMachineAction(coffeeMachine, action);
                    }
                    while(device == 4){
                        action = chooseTeaMachineAction();
                        if(action == 0) device = 0;
                        else makeTeaMachineAction(teaMachine, action);
                    }
                }
                catch (java.io.IOException ex)
                {
                    System.err.println(ex);
                }
            }

        } catch (LocalException e) {
            e.printStackTrace();
            status = 1;
        } catch (Exception e) {
            System.err.println(e);
            status = 1;
        }
        if (communicator != null) {
            try {
                communicator.destroy();
            } catch (Exception e) {
                System.err.println(e.getMessage());
                status = 1;
            }
        }
        System.exit(status);
    }


}
