package brandonlagasse.c482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
  private static ObservableList<Part> allParts = FXCollections.observableArrayList();
  private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

  // Add parts/products //
  public static void addPart(Part newPart){
      allParts.add(newPart);

  }
  public static void addProduct(Product newProduct){
      allProducts.add(newProduct);
  }

  // Lookup parts/products //
  public static Part lookupPart(int partId){

      return null;
  }

  public static Product lookupProduct(int productId){
      return null;
  }

  public static ObservableList<Part> lookupPart(String partName){

      return null;
  }

  public static ObservableList<Product> lookupProduct(String productName){

      return null;
  }

  // Update parts/products //
  public static void updatePart(int index, Part selectedPart){

  }

  public static void updateProduct(int index, Product newProduct){

  }

  // Delete parts/products //

  public static boolean deletePart(Part selectedPart){

      return false;
  }

  public static boolean deleteProduct(Product selectedProduct){
      return false;
  }

  // Get all parts/products //

  public static ObservableList<Part> getAllParts() {
      return null;
  }

  public static ObservableList<Product> getAllProducts() {
      return null;
  }
}
