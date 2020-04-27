import Ice, sys, datetime, traceback
sys.path.insert(1, './generated')
import SmartHouse

Ice.loadSlice('../slice/smarthouse.ice')


def check_if_off(power_state):
    if power_state == SmartHouse.PowerState.OFF:
        time_of_day = generate_time_of_the_day()
        raise SmartHouse.IsOffError(time_of_day, "Device is off. Turn it on first")


def generate_time_of_the_day():
    timestamp = datetime.datetime.now()
    return SmartHouse.TimeOfDay(timestamp.hour, timestamp.minute, timestamp.second)


class DeviceI(SmartHouse.IDevice):
    def __init__(self):
        self.power_state = SmartHouse.PowerState.ON

    def getPowerState(self, current=None):
        return self.power_state

    def turnOn(self, current=None):
        if self.power_state == SmartHouse.PowerState.ON:
            time_of_day = generate_time_of_the_day()
            raise SmartHouse.AlreadyOnError(time_of_day, "Device was already on")
        self.power_state = SmartHouse.PowerState.ON

    def turnOff(self, current=None):
        if self.power_state == SmartHouse.PowerState.OFF:
            time_of_day = generate_time_of_the_day()
            raise SmartHouse.AlreadyOnError(time_of_day, "Device was already off")
        self.power_state = SmartHouse.PowerState.OFF


class FridgeI(SmartHouse.Fridge, DeviceI):
    def __init__(self):
        super().__init__()
        self.temperature = 0

    def getTemperature(self, current=None):
        check_if_off(self.power_state)
        return self.temperature

    def setTemperature(self, temperature, current=None):
        check_if_off(self.power_state)
        if temperature not in range(-20, 10):
            time_of_day = generate_time_of_the_day()
            raise SmartHouse.OutOfRangeError(time_of_day, "Temperature out of range")
        self.temperature = temperature


class LampI(SmartHouse.Lamp, DeviceI):
    def __init__(self):
        super().__init__()
        self.color = SmartHouse.Color.YELLOW

    def getColor(self, current=None):
        check_if_off(self.power_state)
        return self.color

    def setColor(self, color, current=None):
        check_if_off(self.power_state)
        self.color = color


class DrinkMachineI(SmartHouse.IDrinkMachine, DeviceI):
    def __init__(self):
        super().__init__()
        self.sugar_amount = 20
        self.milk_amount = 50

    def addSugar(self, spoons, current=None):
        check_if_off(self.power_state)
        if spoons not in range(1, 11):
            time_of_day = generate_time_of_the_day()
            raise SmartHouse.OutOfRangeError(time_of_day, "Amount of sugar out of range 1 - 10")
        if self.sugar_amount - spoons < 0:
            time_of_day = generate_time_of_the_day()
            raise SmartHouse.NoIngredientsError(time_of_day, "Not enough sugar in machine")
        self.sugar_amount -= spoons

    def addMilk(self, mililitres, current=None):
        check_if_off(self.power_state)
        if mililitres not in range(60, 181):
            time_of_day = generate_time_of_the_day()
            raise SmartHouse.OutOfRangeError(time_of_day, "Amount of milk out of range 60 - 180 ml")
        if self.milk_amount - mililitres < 0:
            time_of_day = generate_time_of_the_day()
            raise SmartHouse.NoIngredientsError(time_of_day, "Not enough milk in machine")
        self.milk_amount -= mililitres


class CoffeeMachineI(SmartHouse.CoffeeMachine, DrinkMachineI, DeviceI):
    def __init__(self):
        super().__init__()
        self.water_amount = 400
        self.coffee_amount = 400

    def makeCoffee(self, coffee_type, current=None):
        check_if_off(self.power_state)
        if self.water_amount - 150 < 0:
            time_of_day = generate_time_of_the_day()
            raise SmartHouse.NoIngredientsError(time_of_day, "Not enough water in machine")
        if self.coffee_amount - 20 < 0:
            time_of_day = generate_time_of_the_day()
            raise SmartHouse.NoIngredientsError(time_of_day, "Not enough coffee in machine")
        self.water_amount -= 150
        self.coffee_amount -= 20

    def getAvailableCoffeeTypes(self, current=None):
        check_if_off(self.power_state)
        return [SmartHouse.CoffeeType.BLACK, SmartHouse.CoffeeType.WHITE, SmartHouse.CoffeeType.LATTE,
                SmartHouse.CoffeeType.ESPRESSO]


class TeaMachineI(SmartHouse.TeaMachine, DrinkMachineI, DeviceI):
    def __init__(self):
        super().__init__()
        self.water_amount = 400
        self.tea_amount = 400

    def makeTea(self, coffee_type, current=None):
        check_if_off(self.power_state)
        if self.water_amount - 150 < 0:
            time_of_day = generate_time_of_the_day()
            raise SmartHouse.NoIngredientsError(time_of_day, "Not enough water in machine")
        if self.tea_amount - 20 < 0:
            time_of_day = generate_time_of_the_day()
            raise SmartHouse.NoIngredientsError(time_of_day, "Not enough tea in machine")
        self.water_amount -= 150
        self.tea_amount -= 20

    def getAvailableTeaTypes(self, current=None):
        check_if_off(self.power_state)
        return [SmartHouse.TeaType.BLACK, SmartHouse.TeaType.GREEN, SmartHouse.TeaType.WHITE]


class ServerServantLocator(Ice.ServantLocator):

    def __init__(self):
        self.servants = {}

    def locate(self, current):
        name = current.id.name
        if name in self.servants.keys():
            return self.servants[name]
        servant = None
        if name == "fridge":
            servant = FridgeI()
        elif name == "lamp":
            servant = LampI()
        elif name == "coffeeMachine":
            servant = CoffeeMachineI()
        elif name == "teaMachine":
            servant = TeaMachineI()
        else:
            raise Ice.ObjectNotFoundException
        self.servants[name] = servant
        return servant

    def finished(self, current, servant, cookie):
        pass

    def deactivate(self, category):
        pass


with Ice.initialize(sys.argv) as communicator:
    try:
        adapter = communicator.createObjectAdapterWithEndpoints("SmartHouseAdapter", "default -p 10000")
        locator = ServerServantLocator()
        adapter.addServantLocator(locator, "")
        adapter.activate()
        print("Entering event processing loop...")
        communicator.waitForShutdown()
    except KeyboardInterrupt:
        print('Bye!')
    except:
        traceback.print_exc()





