/**
 * CarsPQ (indexable priority queue) for CS1501 Project 3
 * @author	Anzu Sekikawa
 */

package cs1501_p3;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

// Good copy //

public class CarsPQ implements CarsPQ_Inter {


    // ********** Attributes ********* //

    private Car[] minPricePQ; //price - min priority queue

    private Car[] minMilesPQ; //miles - min priority queue
    
    private Car_TST indirection; // key = VIN

    private int n; // number of Cars in priority queue 

    // ******** constructor ********** //

    // constructor
    // take in single (String) file name, initialize list of cars under consideration from file
    // # VIN:Make:Model:Price:Mileage:Color
    /**
     * 
     * @param cars_fName
     */
    public CarsPQ( String cars_fName ){

        n=0; 

        indirection = new Car_TST();

        minPricePQ = new Car[10];
        minMilesPQ = new Car[10];

        // use File I/O to take file name and initialize list of cars

        File file = new File( cars_fName );

        Scanner sc = null;
            
        try {
            sc = new Scanner(file);

            // Check if there is another line of input
            while(sc.hasNextLine()){
                String str = sc.nextLine();

                // to ignore line in Cars.txt that starts with '#'
                if (str.startsWith("#")){
                    continue; 
                }
                parseLine(str); //another method
            }
        } catch (IOException  exp) {
            exp.printStackTrace();
        }
        
        sc.close();
    }

    /**
     * Take in a string line of text, formatted as 
     * # VIN:Make:Model:Price:Mileage:Color and create a new Car object 
     * and add it to the indexable priority queue
     * 
     * @param str line of text from file
     */
    private void parseLine(String str){

        String newVin;
        String newMake;
        String newModel;
        int newPrice; 
        int newMileage; 
        String newColor; 

        Scanner sc = new Scanner(str);

        sc.useDelimiter(":"); 
      
        // Check if there is another token 
        while(sc.hasNext()){ 

            newVin = sc.next();
                //System.out.println(newVin);
            newMake = sc.next();
                //System.out.println(newMake);
            newModel = sc.next();
                //System.out.println(newModel);
            newPrice = sc.nextInt(); 
                //System.out.println(newPrice);
            newMileage = sc.nextInt(); 
                //System.out.println(newMileage);
            newColor = sc.next(); 
                //System.out.println(newColor);
            
            // create new Car using Car constructor 
            Car newCar = new Car(newVin, newMake, newModel, newPrice, newMileage, newColor);

            // add new Car into indexable priority queue
            add(newCar);        

        }

        sc.close();
    }

    public boolean isEmpty() {
        return n == 0; 
    }

    /**
	 * Add a new Car to the data structure
	 * Should throw an `IllegalStateException` if there is already car with the
	 * same VIN in the datastructure.
	 *
	 * @param 	c Car to be added to the data structure
	 */
	public void add(Car c) throws IllegalStateException{

        // can use TST contains to check whether same VIN exists
        // O(1) runtime for the contains check 

        // must add Car to minPricePQ and minMilesPQ

        if ( indirection.contains( c.getVIN() )){
            throw new IllegalStateException("car with same VIN already in CarsPQ");
        } else {

            // Car with this VIN does not yet exist in data structure

            // add Car c to indirection (TST) - key is VIN, value is the Car ref 
            indirection.put( c.getVIN(), c ); 

            n++; // increment num cars

            // add Car to minPricePQ
            insertPrice(c);

            // add Car to minMilesPQ 
            insertMiles(c);
        }
    } 

    /**
     * Adds a new Car to the price priority queue
     * 
     * @param c the Car to be added to the price priority queue
     */
    private void insertPrice(Car c){

        // double size of array if necessary
        // if num cars is equal to index of largest index of pq array 
        // resize to double current array size 
        if (n == minPricePQ.length-1) resizePricePQ( 2*minPricePQ.length );

        // add c, swim up to maintain heap invariant
        // add c at 'n' (n has been incremented before we call this method)
        // because we want to add the car at 'numCars+1' since we don't use index 0 
        minPricePQ[n] = c; 
        // on the 'heap' - we are adding the car at the 'first available leaf' 
        // must restore heap invariant 

        indirection.putPriceIndex(c.getVIN(), n); 

        swimPrice(n); 

    }

    private void insertMiles(Car c){

        // double size of array if necessary
        if (n == minMilesPQ.length-1) resizeMilesPQ( 2*minMilesPQ.length );

        // add c, swim up to maintain heap invariant
        // add c at 'n' where n has already been incremented 
        minMilesPQ[n] = c; 

        indirection.putMilesIndex(c.getVIN(), n); 

        // restore heap invariant
        swimMiles(n); 

    }

    /**
     * helper method to ensure capacity - resize the min price priority queue array to size 'cap'
     * @param cap the new size of the min price priority queue 
     */
    private void resizePricePQ( int cap ){
        // resize is allowed to be O(n) 

        // ensure the new capacity is at least as large as num cars in priority queue
        assert cap > n; 

        Car[] temp = new Car[cap];

        // copy all Cars in min price priority queue into temp array 
        for (int i=1; i<=n; i++) {
            temp[i] = minPricePQ[i];
        }

        // set min price priority queue to temp 
        minPricePQ = temp; 
    }

    // same as resizePricePQ 
    private void resizeMilesPQ( int cap ){

        assert cap > n; 

        Car[] temp = new Car[cap];

        for (int i=1; i<=n; i++) {
            temp[i] = minMilesPQ[i];
        }
        minMilesPQ = temp; 

    }

    /**
     * Helper method to restore heap invariant 
     * 
     * @param k index of the Car stored in min price priority queue
     */
    private void swimPrice(int k){

        //print("swim " + k);

        // while k is a child, and k's parent's price is > than k's price 
        while (k>1 && greaterPrice(k/2, k)) {

            // swap Car at k with its parent 
            exchPrice(k,k/2);

            // after exchanging positions, now we need to check again whether we need to do another swap or have we reached the root
            k=k/2;
        }

    }

    /**
     * Helper method to restore heap invariant 
     * 
     * @param k index of Car stored in min miles priority queue 
     */
    private void swimMiles(int k){

        //print("Swim " + k);

        while (k>1 && greaterMiles(k/2,k)) {
            exchMiles(k,k/2);
            k=k/2;
        }

    }

    /**
     * Helper method - restore heap invariant
     * if Car at index k has child with higher priority, must swap with child 
     * 
     * used when we update price of a Car or when we delete a Car from min price priority queue 
     * 
     * @param k index of Car stored in min price priority queue 
     */
    private void sinkPrice(int k) {

        // while the child of Car at index k is still a valid index 
        while (2*k <= n) {

            int j = 2*k; // index of child of Car at k 

            // if j is not the last child 
            // and if j's price is greater than j's sibling's child
            // set j to be j's sibling's index 
            if (j<n && greaterPrice(j, j+1)) j++; 

            // if k's price is not greater than its child's price - do nothing 
            if (!greaterPrice(k,j)) break;

            //print("exchange " + k + " and " + j); 
            // k's price is greater than its child's price - thus swap k with the child of higher priority 
            exchPrice(k,j);

            // set k to be j and repeat 
            k=j; 
        }

    }

    // same as sinkPrice (except works with min miles priority queue array)
    private void sinkMiles(int k) {

        while (2*k <= n) {

            int j = 2*k; 
            if (j<n && greaterMiles(j, j+1)) j++;
            if (! greaterMiles(k,j)) break;
            exchMiles(k,j);
            k=j; 

        }

    }

    /**
     * Helper method to compare price of Car at i and Car at j in the min price priority queue
     * @param i index of a Car in the min price pq array
     * @param j index of a Car in the min price pq array 
     * @return true if price of Car at index i is greater than price of Car at index j 
     */
    private boolean greaterPrice( int i, int j) {

        return minPricePQ[i].getPrice() > minPricePQ[j].getPrice();

    }

    /**
     * Helper method to compare mileage of Car at i and Car at j in the min miles priority queue
     * @param i index of a Car in the min miles pq array
     * @param j index of a Car in the min miles pq array 
     * @return true if mileage of Car at index i is greater than mileage of Car at index j 
     */
    private boolean greaterMiles( int i, int j) {
        
        return minMilesPQ[i].getMileage() > minMilesPQ[j].getMileage();
    }

    /**
     * Helper method to swap parent and child if child is of higher priority 
     */
    private void exchPrice( int i, int j) {

        Car swap = minPricePQ[i];
        minPricePQ[i] = minPricePQ[j];
        minPricePQ[j] = swap; 

        // must make corresponding change in the Car_TST

        indirection.putPriceIndex(minPricePQ[j].getVIN(), j); 
        indirection.putPriceIndex(minPricePQ[i].getVIN(), i);

    }

    // same as exchPrice except deals with mileage instead of price 
    private void exchMiles( int i, int j ) {

        Car swap = minMilesPQ[i];
        minMilesPQ[i] = minMilesPQ[j];
        minMilesPQ[j] = swap; 

        // must make corresponding change in the Car_TST

        //print("Swap Car at index " + i + " and " + j);

        indirection.putMilesIndex( minMilesPQ[j].getVIN(), j); 
        indirection.putMilesIndex( minMilesPQ[i].getVIN(), i);

    }

    /**
	 * Retrieve a new Car from the data structure
	 * Should throw a `NoSuchElementException` if there is no car with the 
	 * specified VIN in the datastructure.
	 *
	 * @param 	vin VIN number of the car to be updated
	 */
	public Car get(String vin) throws NoSuchElementException{

        // O(1) 

        if ( ! indirection.contains(vin) ) {
            throw new NoSuchElementException("there is no car with specified VIN in the datastructure");
        }

        // TST's get method takes the key (vin) and returns ref to Car associated with that vin 
        return indirection.get(vin); 
    }

    /**
	 * Update the price attribute of a given car
	 * Should throw a `NoSuchElementException` if there is no car with the 
	 * specified VIN in the datastructure.
	 *
	 * @param 	vin VIN number of the car to be updated
	 * @param	newPrice The updated price value
	 */
	public void updatePrice(String vin, int newPrice) throws NoSuchElementException{

        // use TST - get ref to Car obj stored at Node, use setPrice() method to change price
        // must make changes accordingly in the minPricePQ
        // when we update price and do a swap to re-establish heap invariant - must update index in TST

        // array - that represents binary tree - heap - must 'reheap' and make changes to indices in TST

        if ( ! indirection.contains(vin) ) {
            throw new NoSuchElementException("There is no car with specified VIN in the datastructure");
        }

        // Car with specified VIN exists in data structure 

        Car curr = indirection.get(vin); 

        // get ref to Car using TST and update price using setter from Car class 
        curr.setPrice(newPrice);

        // use indirection to get index of where Car with 'vin' is currently located in the min price pq array 
        int currPriceIndex = indirection.getPriceIndex(vin); 
        
        // must re-establish heap invariant here because we've changed the 'price'
        swimPrice(currPriceIndex);
        sinkPrice(currPriceIndex);

    }

    /**
	 * Update the mileage attribute of a given car
	 * Should throw a `NoSuchElementException` if there is not car with the 
	 * specified VIN in the datastructure.
	 *
	 * @param 	vin VIN number of the car to be updated
	 * @param	newMileage The updated mileage value
	 */
	public void updateMileage(String vin, int newMileage) throws NoSuchElementException{

        // same as updatePrice, except use setMileage()
        // must find index of where specific vin car is using TST get(vin)
        // go to minMilesPQ array, update the value at that index, re-establish heap property
        // must update index in the TST

        if ( ! indirection.contains(vin) ) {
            throw new NoSuchElementException("There is no car with specified VIN in the datastructure");
        }

        Car curr = indirection.get(vin); 

        // get ref to Car using TST and update price 
        curr.setMileage(newMileage);

        int currMilesIndex = indirection.getMilesIndex(vin); 
        
        // must re-establish heap invariant here
        swimMiles(currMilesIndex);
        sinkMiles(currMilesIndex);

    }

	/**
	 * Update the color attribute of a given car
	 * Should throw a `NoSuchElementException` if there is not car with the 
	 * specified VIN in the datastructure.
	 *
	 * @param 	vin VIN number of the car to be updated
	 * @param	newColor The updated color value
	 */
	public void updateColor(String vin, String newColor) throws NoSuchElementException{

        // use TST get(vin) and use setColor() to change color of Car obj stored in ref in TST

        if ( !indirection.contains(vin)) {
            throw new NoSuchElementException("There is no car with specified VIN in the datastructure");
        }

        Car curr = indirection.get(vin); 

        curr.setColor(newColor); 
    }

    private void removeMiles(String vin) {

        int milesIndex = indirection.getMilesIndex(vin);

        //print("Miles Index to remove " + milesIndex);


        //print("Exchange " + milesIndex + " and " + n);

        exchMiles(milesIndex, n); 

        minMilesPQ[n]=null; 

        if (milesIndex != n) {
            swimMiles(milesIndex);
            n--;
            sinkMiles(milesIndex);
            n++;
        }

        indirection.putMilesIndex(vin, 0); 

    }

    private void removePrice(String vin) {

        int priceIndex = indirection.getPriceIndex(vin); //get index of where the car is stored in min price pq arr

        //print("Price Index to remove: " + priceIndex); 
        //print("Exchange " + priceIndex + " with " + n); 

        exchPrice(priceIndex, n); // swap Car 
        minPricePQ[n]=null; 

        //print("sink and swim: " + priceIndex); 

        if ( priceIndex != n ){
            swimPrice(priceIndex);
            n--;
            sinkPrice(priceIndex);
            n++; 
        } 
        

        indirection.putPriceIndex(vin, 0);
    }

	/**
	 * Remove a car from the data structure
	 * Should throw a `NoSuchElementException` if there is not car with the 
	 * specified VIN in the datastructure.
	 *
	 * @param 	vin VIN number of the car to be removed
	 */
	public void remove(String vin) throws NoSuchElementException{

        // use TST to get index of car with VIN 
        // should I delete the key from TST by setting value as null for that node (?) - YES
        // use TST put() method, but set Value to null - thus 'removes' key from TST

        // must remove the price and mileage from minPricePQ and minMilesPQ 
        // must use sink and swim 
        // don't forget to update indices (in TST) of all cars involved in swaps
        // look at how MinIndexPQ does it in the file
        
        if ( !indirection.contains(vin)) {
            throw new NoSuchElementException("There is no car with specified VIN in the datastructure");
        }

        removePrice(vin); 

        removeMiles(vin); 
        
        // don't forget to delete the Car from the TST 
        indirection.put(vin, null); 

        // decrement num cars by 1 
        n = n-1; 

    }

	/**
	 * Get the lowest priced car (across all makes and models)
	 * Should return `null` if the data structure is empty
	 *
	 * @return	Car object representing the lowest priced car
	 */
	public Car getLowPrice(){

        // use minPricePQ, index 1 stores min priced car
        // return ref to that Car

        // O(1) runtime

        if (isEmpty()) {
            return null; 
        }

        return minPricePQ[1]; 

    }

	/**
	 * Get the lowest priced car of a given make and model
	 * Should return `null` if the data structure is empty
	 *
	 * @param	make The specified make
	 * @param	model The specified model
	 * 
	 * @return	Car object representing the lowest priced car
	 */
	public Car getLowPrice(String make, String model) {

        if (isEmpty()) return null;

        for (int i=1; i<=n; i++) {

            if ( minPricePQ[i].getMake().equals(make) && minPricePQ[i].getModel().equals(model)){
                return minPricePQ[i]; 
            }

        }

        throw new NoSuchElementException("Car with specified make and model does not exist in this data structure");
        
    }

	/**
	 * Get the car with the lowest mileage (across all makes and models)
	 * Should return `null` if the data structure is empty
	 *
	 * @return	Car object representing the lowest mileage car
	 */
	public Car getLowMileage(){
        // return ref at index 1 of minMilesPQ;

        // O(1) runtime

        if (isEmpty()) {
            return null; 
        }

        return minMilesPQ[1]; 
    }

	/**
	 * Get the car with the lowest mileage of a given make and model
	 * Should return `null` if the data structure is empty
	 *
	 * @param	make The specified make
	 * @param	model The specified model
	 *
	 * @return	Car object representing the lowest mileage car
	 */
	public Car getLowMileage(String make, String model) {  

        if (isEmpty()) return null; 

        for (int i=1; i<=n; i++) {

            if ( minMilesPQ[i].getMake().equals(make) && minMilesPQ[i].getModel().equals(model)){
                return minMilesPQ[i]; 
            }

        }

        throw new NoSuchElementException("Car with specified make and model does not exist in this data structure");

    }
    

    // ************* TST ************* // 

    private class Car_TST {

        private int n; // size

        private Node root; // root of TST

        // Nodes in TST
        // Value should be Car reference
        private class Node{
            private char c; // character
            private Node left, mid, right; // left, middle, right subtries
            private Car val; // value associated with string key 

            private int priceIndex; // for indirection
            private int milesIndex; // for indirection

            // only indices >=1 are valid indices

        }

        /**
         * Initialize empty string symbol table
         */
        public Car_TST(){
        }

        // number of key-value pairs in this symbol table
        public int size(){
            return n;
        }

        // checks with Car with given key exists in TST 
        public boolean contains(String key){
            if (key==null) throw new IllegalArgumentException("argument to contains() is null");
            return get(key) != null;
        }

        // returns ref to Car with specific vin
        // returns null if Car with specific vin not found in TST
        public Car get(String key){
            if (key==null) throw new IllegalArgumentException("calls get() with null argument");

            Node x = get(root, key, 0);
            if (x==null) return null;
            return x.val; 
        }

        //returns priceIndex of Car with given vin, or null if Car not found 
        public Integer getPriceIndex(String key) {
            if (key==null) throw new IllegalArgumentException("calls get() with null argument");

            Node x = get(root, key, 0);
            if (x==null) return null;
            return x.priceIndex;
        }

        // returns miles index of Car with given vin, or null if Car not found
        public Integer getMilesIndex(String key) {
            if (key==null) throw new IllegalArgumentException("calls get() with null argument");

            Node x = get(root, key, 0);
            if (x==null) return null;

            return x.milesIndex;
        }

        // return subtrie corresponding to given key
        private Node get(Node x, String key, int d){
            if (x==null) return null;
            if (key.length()==0) throw new IllegalArgumentException("key must have length >= 1");
            char c = key.charAt(d);
            if (c < x.c) return get(x.left, key, d);
            else if (c > x.c) return get(x.right, key, d);
            else if (d < key.length() - 1) return get(x.mid, key, d+1);
            else return x; 
        }

        // can use to update Val associated with key 
        public void put(String key, Car val){
            if (key==null) throw new IllegalArgumentException("calls put() with null key");
            if (!contains(key)) n++;
            else if (val==null) n--; //delete existing key
            root = put(root, key, val, 0);
        }

        public void setPriceIndex(String key, int newIndex) {
            if (key==null) throw new IllegalArgumentException("calls put() with null key");

            if(!contains(key)) throw new NoSuchElementException("Car with specific key not found"); 
            
            Node x = get(root, key, 0); // Node that stores ref to Car w this key

            x.priceIndex = newIndex; 
        }

        public void putPriceIndex(String key, int newIndex){
            if (key==null) throw new IllegalArgumentException("calls put() with null key");
            
            root = putPriceIndex(root, key, newIndex, 0);
        }

        private Node putPriceIndex(Node x, String key, int newIndex, int d){
            char c = key.charAt(d);
            if (x==null){
                x = new Node();
                x.c = c;
            }
            if (c < x.c) x.left = putPriceIndex(x.left, key, newIndex, d);
            else if (c > x.c) x.right = putPriceIndex(x.right, key, newIndex, d);
            else if (d < key.length() - 1) x.mid = putPriceIndex(x.mid, key, newIndex, d+1);
            else x.priceIndex = newIndex;
            return x;
        }

        public void setMilesIndex(String key, int newIndex) {
            if (key==null) throw new IllegalArgumentException("calls put() with null key");

            if(!contains(key)) throw new NoSuchElementException("Car with specific key not found"); 
            
            Node x = get(root, key, 0); // Node that stores ref to Car w this key

            x.milesIndex = newIndex;
        }

        public void putMilesIndex(String key, int newIndex){
            if (key==null) throw new IllegalArgumentException("calls put() with null key");
            
            root = putMilesIndex(root, key, newIndex, 0);
        }

        private Node putMilesIndex(Node x, String key, int newIndex, int d){
            char c = key.charAt(d);
            if (x==null){
                x = new Node();
                x.c = c;
            }
            if (c < x.c) x.left = putMilesIndex(x.left, key, newIndex, d);
            else if (c > x.c) x.right = putMilesIndex(x.right, key, newIndex, d);
            else if (d < key.length() - 1) x.mid = putMilesIndex(x.mid, key, newIndex, d+1);
            else x.milesIndex = newIndex;
            return x; 
        }

        private Node put(Node x, String key, Car val, int d){
            char c = key.charAt(d);
            if (x==null){
                x = new Node();
                x.c = c;
            }
            if (c < x.c) x.left = put(x.left, key, val, d);
            else if (c > x.c) x.right = put(x.right, key, val, d);
            else if (d < key.length() - 1) x.mid = put(x.mid, key, val, d+1);
            else x.val = val;
            return x; 
        }

    } // end Car_TST class

    // ******* DEBUG METHODS ******* //
    public void print(String arg){
        System.out.println(arg); 
    }

    public void printMinMilesPQ(){
        for (int i=0; i<minMilesPQ.length; i++){
            if (minMilesPQ[i] == null){
                print("NULL");
            } else {
                print("Index " + i + ": " + minMilesPQ[i].getVIN() + " Miles: " + minMilesPQ[i].getMileage() );
            }
        }
    }

    public void printMinPricePQ(){
        for (int i=0; i<minPricePQ.length; i++){
            if (minPricePQ[i] == null){
                print("NULL");
            } else {
                print("Index " + i + ": " + minPricePQ[i].getVIN() + " Price: " + minPricePQ[i].getPrice() );
            }
        }
    }

}


