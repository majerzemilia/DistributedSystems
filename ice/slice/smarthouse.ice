#ifndef SMARTHOUSE_ICE
#define SMARTHOUSE_ICE

module SmartHouse {

	enum PowerState { ON, OFF };
	enum CoffeeType { BLACK, WHITE, LATTE, ESPRESSO };
	enum TeaType { BLACK, GREEN, WHITE };
	enum Color { RED, BLUE, GREEN, YELLOW, WHITE };
	
	sequence <TeaType> AvailableTeaTypes;
	sequence <CoffeeType> AvailableCoffeeTypes;
	
	struct TimeOfDay {
		short hour;    // 0 - 23
		short minute;   // 0 - 59
		short second;    // 0 - 59
	}
	
	exception Error {
		TimeOfDay errorTime;
		string reason;
	}
	
	exception OutOfRangeError extends Error {}	
	exception AlreadyOffError extends Error {}
	exception AlreadyOnError extends Error {}
	exception NoIngredientsError extends Error {}
	exception IsOffError extends Error {}

	
	interface IDevice {
		idempotent PowerState getPowerState();
		void turnOn() throws AlreadyOnError;
		void turnOff() throws AlreadyOffError;
	}
	
	interface Fridge extends IDevice {
		idempotent float getTemperature() throws IsOffError;
		void setTemperature(float temperature) throws OutOfRangeError, IsOffError;
	}
	
	interface Lamp extends IDevice {
		idempotent Color getColor() throws IsOffError;
		void setColor(Color color) throws IsOffError;		
	}
	
	interface IDrinkMachine extends IDevice {
		void addSugar(int spoons) throws NoIngredientsError, OutOfRangeError, IsOffError;
		void addMilk(int mililitres) throws NoIngredientsError, OutOfRangeError, IsOffError;		
	}
	
	interface CoffeeMachine extends IDrinkMachine {
		void makeCoffee(CoffeeType coffeeType) throws NoIngredientsError, IsOffError;
		idempotent AvailableCoffeeTypes getAvailableCoffeeTypes() throws IsOffError;
	}
	
	interface TeaMachine extends IDrinkMachine {
		void makeTea(TeaType teaType) throws NoIngredientsError, IsOffError;
		idempotent AvailableTeaTypes getAvailableTeaTypes() throws IsOffError;
	}
	
}

#endif