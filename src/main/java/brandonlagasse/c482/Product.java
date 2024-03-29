package brandonlagasse.c482;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList(); //I had this in the constructor by accident and it set me back a while trying to figure out.
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor for Product
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * This method ads the part to the associatedparts list
     * @param part to be added to associatedParts list
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }

    /**
     * This method removes the selected part from the associated parts list.
     * @param selectedAssociatedPart
     * @return boolean for deltion error
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        associatedParts.remove(selectedAssociatedPart);
        return false;
    }

    /**
     * This method is the getter for the associatedParts list.
     * @return returns the associatedParts list
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }

}