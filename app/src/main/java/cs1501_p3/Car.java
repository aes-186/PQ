/**
 * Car class 
 * store data about cars to buy
 * 
 * Track VIN number (17 char string of numbers and capital letters)
 * car's make (string)
 * car's model (string)
 * price to purchase (in whole dollars - int)
 * mileage of the car (whole miles - int)
 * color of car (string)
 * 
 *  @author	Anzu Sekikawa
 */

package cs1501_p3;

public class Car implements Car_Inter {

    // private attributes of Car object
    private String vin; //17 char string of numbers and capital letters
    private String make; //car's make
    private String model; //car's model
    private int price; //whole dollars
    private int mileage; //whole miles
    private String color; //color of car

    // constructor
    public Car(String vin, String make, String model, int price, int mileage, String color){
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.price = price;
        this.mileage = mileage;
        this.color = color; 
    }



	/**
	 * Getter for the VIN attribute
	 *
	 * @return 	String The VIN
	 */
	public String getVIN(){
        return vin; 
    }

	/**
	 * Getter for the make attribute
	 *
	 * @return 	String The make
	 */
	public String getMake(){
        return make; 
    }

	/**
	 * Getter for the model attribute
	 *
	 * @return 	String The model
	 */
	public String getModel(){
        return model;
    }

	/**
	 * Getter for the price attribute
	 *
	 * @return 	String The price
	 */
	public int getPrice(){
        return price;
    }

	/**
	 * Getter for the mileage attribute
	 *
	 * @return 	String The mileage
	 */
	public int getMileage(){
        return mileage;
    }

	/**
	 * Getter for the color attribute
	 *
	 * @return 	String The color
	 */
	public String getColor(){
        return color; 
    }

    // TODO: should I check for invalid input for setters? 

	/**
	 * Setter for the price attribute
	 *
	 * @param 	newPrice The new Price
	 */
	public void setPrice(int newPrice){

        this.price = newPrice;
    }

	/**
	 * Setter for the mileage attribute
	 *
	 * @param 	newMileage The new Mileage
	 */
	public void setMileage(int newMileage){
        this.mileage = newMileage;
    }

	/**
	 * Setter for the color attribute
	 *
	 * @param 	newColor The new color
	 */
	public void setColor(String newColor){
        this.color = newColor; 
    }
    
}
