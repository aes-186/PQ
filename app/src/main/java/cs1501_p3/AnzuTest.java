package cs1501_p3;

public class AnzuTest {

	public static void print(String args){
		System.out.println(args);
	}


	public static void main(String[] args) {

		//CarsPQ cpq = new CarsPQ("app/src/test/resources/cars.txt");

		CarsPQ cpq = new CarsPQ("/Users/Anzu/OneDrive - University of Pittsburgh/JavaPrograms/CS1501/Assignments/project3-aes-186/app/src/test/resources/anzuCars.txt");

		print("Miles");
		cpq.printMinMilesPQ();
		print("\n");

		print("Lowest Mileage Car: ");
		print(cpq.getLowMileage().getVIN());

		print("\n");
		print("Price");
		cpq.printMinPricePQ();


		print("Lowest Priced Car: ");
		print(cpq.getLowPrice().getVIN());
		print("\n");


		cpq.remove("X1U2PEJSC361L10MZ");
		print("Removed X1U2PEJSC361L10MZ");

		print("\n");
		print("Miles");
		cpq.printMinMilesPQ();
		print("\n");


		print("Lowest Mileage Car: ");
		print(cpq.getLowMileage().getVIN());

		print("\n");
		print("Price");
		cpq.printMinPricePQ();
		
		print("Lowest Priced Car: ");
		print(cpq.getLowPrice().getVIN());
		print("\n");


		print("\n");
		print("Remove PUAF85WU5R6L6H1P9");
		cpq.remove("PUAF85WU5R6L6H1P9");
		print("\n");

		print("\n");
		print("Price");
		cpq.printMinPricePQ();


		print("Lowest Priced Car: ");
		print(cpq.getLowPrice().getVIN());
		print("\n");

		print("Miles");
		cpq.printMinMilesPQ();
		print("\n");

		print("Lowest Mileage Car: ");
		print(cpq.getLowMileage().getVIN());
		
		// cpq.remove("5DZ623ZRW0C4N80YZ");
		// print("Removed 5DZ623ZRW0C4N80YZ");

		// print("Miles");
		// cpq.printMinMilesPQ();
		// print(cpq.getLowMileage().getVIN());
		// print("\n");

		// print("Price");
		// cpq.printMinPricePQ();

		// cpq.remove("UTJYU67091B71NGZ3");
		// cpq.remove("PUAF85WU5R6L6H1P9");
		// cpq.remove("Y9BXE6H7957YNKD2C");
		// cpq.remove("678PL45NTNWRED0RJ");

		



		// print("Lowest Mileage Car: ");
		// print(cpq.getLowMileage().getVIN());

	

		//cpq.getLowMileage();


		// print("Curr low mileage: ");
		// print(cpq.getLowMileage().getVIN());

		// cpq.remove("PUAF85WU5R6L6H1P9"); 
		// print("Removed PUAF85WU5R6L6H1P9");

		// print("Curr low mileage: ");
		// print(cpq.getLowMileage().getVIN());

        // System.out.println("Color (red): " + cpq.get("PUAF85WU5R6L6H1P9").getColor() );
		// System.out.println("Color (green): " + cpq.get("X1U2PEJSC361L10MZ").getColor());
		// System.out.println("Color (yellow): " + cpq.get("16Z2DPEHSUK5KCMEH").getColor());

        // String vin = "1Y5NWYGLY5F4PX4HH";
		// String newColor = "White";
		// print("Original color: " + cpq.get(vin).getColor());
		// cpq.updateColor(vin, newColor);
		// System.out.println("New Color: " +  cpq.get(vin).getColor());

        // String vin1 = "X1U2PEJSC361L10MZ";
		// cpq.get(vin1);
		// cpq.remove(vin1);
		// cpq.get(vin1); //should throw no such element exception - YES

        // Car c = cpq.getLowPrice();
		// boolean r = c.getVIN().equals("UTJYU67091B71NGZ3")
		// 		|| c.getVIN().equals("RAMM7ZJBSFZ0HRTTN")
		// 		|| c.getVIN().equals("SY719WJ4MMYVN0XNG");
       	// print( "Result: " + r );
		
		// c = cpq.getLowMileage();
		// r = c.getVIN().equals("PUAF85WU5R6L6H1P9")
		// 		|| c.getVIN().equals("GNX5TS04SM5V5EXP8");
        // print( "Result: " + r );

		//cpq.remove("5DZ623ZRW0C4N80YZ");
		//cpq.remove("16Z2DPEHSUK5KCMEH");
		//cpq.remove("5DZ623ZRW0C4N80YZ");

		//print( cpq.getLowMileage("Hyundai","Elantra").getVIN() );
		
		//print( cpq.getLowMileage().getVIN() );

		//print( cpq.getLowPrice().getVIN() ); 

		//print( cpq.getLowMileage("Ford", "Fiesta").getVIN() );

		//print( cpq.getLowPrice("Ford", "Fiesta").getVIN());

		//print( cpq.getLowMileage().getVIN() );

		// Car c = new Car("8BSM1K0A6GXY2CHD7","Ford","Escort",105, 60, "Blue");

		// cpq.add(c);

		// //Car x = cpq.get("8BSM1K0A6GXY2CHD7");

		// //print( x.getVIN());

		// cpq.updateMileage("8BSM1K0A6GXY2CHD7", 0);
		// //cpq.updatePrice("8BSM1K0A6GXY2CHD7",0);

		// print(cpq.getLowMileage().getVIN());

		// cpq.remove("8BSM1K0A6GXY2CHD7");
		// print("Removed 8BSM1K0A6GXY2CHD7");

		// //cpq.get("8BSM1K0A6GXY2CHD7");

		// print(cpq.getLowMileage().getVIN());

		// //print(cpq.getLowPrice().getVIN());

		


	}

    
}
