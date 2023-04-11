package brandonlagasse.c482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
  static ObservableList<Part> allParts = FXCollections.observableArrayList();
  static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * This function adds new parts to the allParts observable List
     * @param newPart the new part to add to allParts observable
     */
// Add parts/products //
  public static void addPart(Part newPart){
      allParts.add(newPart);

  }

    /**
     * This function adds new products to the allProducts observable list.
     * @param newProduct the new product to add to allProducts
     */
  public static void addProduct(Product newProduct){

      allProducts.add(newProduct);
  }

    /**
     *This is the function for looking up a Part by the ID.
     * @param partId to look up
     * @return the part
     */

  // Lookup parts/products //
  public static Part lookupPart(int partId){

      ObservableList<Part>allParts = Inventory.getAllParts();

      //For each part in our parts list, return the id if there is a match, else return null.
      for (Part part : allParts) {
          if (part.getId() == partId) {
              return part;
          }
      }

      return null;
  }

    /**
     * This function looks up Products by ID.
     * @param productId to look up
     * @return the product
     */
  public static Product lookupProduct(int productId){
      ObservableList<Product>allProducts = Inventory.getAllProducts();

      //For each part in our products list, return the id if there is a match, else return null.
      for (Product product : allProducts) {
          if (product.getId() == productId) {
              return product;
          }
      }

      return null;
  }

    /**
     * Lookup Part by part Name.
     * @param partName part name to look up
     * @return the corresponding parts
     */
  public static ObservableList<Part> lookupPart(String partName){


      //This is the collection for the result of the search
      ObservableList<Part>partNames = FXCollections.observableArrayList();

      //This list returns all parts in the inventory
      ObservableList<Part>allParts = Inventory.getAllParts();

      for(Part part : allParts){
          if(part.getName().contains(partName)){
              partNames.add(part);
          };
      }

      return partNames;

  }

    /**
     * This method returns Products by name
     * @param productName the product to search for
     * @return the products that contain the name
     */
  public static ObservableList<Product> lookupProduct(String productName){

      //This is the collection for the result of the search
      ObservableList<Product>productNames = FXCollections.observableArrayList();

      //This list returns all parts in the inventory
      ObservableList<Product>allProducts = Inventory.getAllProducts();

      for(Product product : allProducts){
          if(product.getName().contains(productName)){
              productNames.add(product);
          };
      }

      return productNames;
  }

    /**
     * This method updates the allParts list with selected part
     * @param index of part to be removed
     * @param selectedPart to be updated
     */
  // Update parts/products //
  public static void updatePart(int index, Part selectedPart){
      allParts.remove(index);
      allParts.add(selectedPart);
  }

    /**
     * This method updates the allProducts list with the selected product
     * @param index product to be removed
     * @param newProduct product to be updated
     */
  public static void updateProduct(int index, Product newProduct){
      allProducts.remove(index);
      allProducts.add(newProduct);

  }

  // Delete parts/products //

    /**
     * This method removes the selected part from the allParts list.
     * @param selectedPart to be removed
     * @return boolean for confirmation
     */
  public static boolean deletePart(Part selectedPart){
      allParts.remove(selectedPart);
      return false;
  }

    /**
     * This method removes the selected product from the allProducts list.
     * @param selectedProduct
     * @return confirmation for deletion
     */

  public static boolean deleteProduct(Product selectedProduct){
      allProducts.remove(selectedProduct);
      return false;
  }

  // Get all parts/products //

    /**
     * This method returns the allParts list
     * @return get allParts list
     */
  public static ObservableList<Part> getAllParts() {
      return allParts;
  }

    /**
     * This method returns the allProducts list
     * @return get allProducts list
     */
  public static ObservableList<Product> getAllProducts() {
      return allProducts;
  }
}
