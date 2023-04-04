package brandonlagasse.c482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
  static ObservableList<Part> allParts = FXCollections.observableArrayList();
  static ObservableList<Product> allProducts = FXCollections.observableArrayList();

  // Add parts/products //
  public static void addPart(Part newPart){
      allParts.add(newPart);

  }
  public static void addProduct(Product newProduct){

      allProducts.add(newProduct);
  }

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

  // Update parts/products //
  public static void updatePart(int index, Part selectedPart){
      allParts.remove(index);
      allParts.add(selectedPart);
  }

  public static void updateProduct(int index, Product newProduct){
      allProducts.remove(index);
      allProducts.add(newProduct);

  }

  // Delete parts/products //

  public static boolean deletePart(Part selectedPart){
      allParts.remove(selectedPart);
      return false;
  }

  public static boolean deleteProduct(Product selectedProduct){
      allProducts.remove(selectedProduct);
      return false;
  }

  // Get all parts/products //

  public static ObservableList<Part> getAllParts() {
      return allParts;
  }

  public static ObservableList<Product> getAllProducts() {
      return allProducts;
  }
}
