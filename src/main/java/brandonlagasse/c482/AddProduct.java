package brandonlagasse.c482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import static brandonlagasse.c482.Inventory.allParts;
import static brandonlagasse.c482.Inventory.getAllParts;

public class AddProduct implements Initializable {
    public Part transferPart = null;
    public Product transferProduct;
    ObservableList<Part> finalPartList = FXCollections.observableArrayList();

    public TextField productIdField;
    public TextField productNameField;
    public TextField productStockField;
    public TextField productPriceField;
    public TextField productMaxField;
    public TextField productMinField;
    public TextField addProductSearch;
    public TableView partInventoryTable;
    public TableColumn invPartIdCol;
    public TableColumn invPartNameCol;
    public TableColumn invStockCol;
    public TableColumn invCostCol;
    public Button addPartButton;
    public TableView productFinalTable;
    public TableColumn finalPartIdCol;
    public TableColumn finalPartNameCol;
    public TableColumn finalPartStockCol;
    public TableColumn finalCostCol;
    public Button removePartButton;
    public Button cancelAddButton;
    public Button saveProductButton;


    public void onAddPartButton(ActionEvent actionEvent) {
        transferPart = (Part) partInventoryTable.getSelectionModel().getSelectedItem();
        transferProduct.addAssociatedPart(transferPart);
        productFinalTable.setItems(transferProduct.getAllAssociatedParts());
        //This needs to be redone to work with the missing functions from Product.java
    }
    public void onRemovePartButton(ActionEvent actionEvent) {
        transferPart = (Part) productFinalTable.getSelectionModel().getSelectedItem();
        transferProduct.deleteAssociatedPart(transferPart);
        productFinalTable.setItems(transferProduct.getAllAssociatedParts());

    }

    public void onCancelAddButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,600);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    public void onSaveProductButton(ActionEvent actionEvent) throws IOException {
        //Get field text and put it into strings
        String prodIdStr = productIdField.getText();
        String prodNameStr = productNameField.getText();
        String prodStockStr = productStockField.getText();
        String prodPriceStr = productPriceField.getText();
        String prodMaxStr = productMaxField.getText();
        String prodMinStr = productMinField.getText();

        //Convert strings to integers
        int id = Integer.parseInt(prodIdStr);
        int stock = Integer.parseInt(prodStockStr);
        double cost = Double.parseDouble(prodPriceStr);
        int max = Integer.parseInt(prodMaxStr);
        int min = Integer.parseInt(prodMinStr);

        //Create the new product
        //Product product = new Product(id,prodNameStr,cost,stock,max,min){};
        transferProduct.setId(id);
        transferProduct.setName(prodNameStr);
        transferProduct.setPrice(cost);
        transferProduct.setStock(stock);
        transferProduct.setMax(max);
        transferProduct.setMin(min);

        Inventory.allProducts.add(transferProduct);


        //Go back to the main screen
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set up column names for each table
        invPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        invPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        invCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        //partInventoryTable.setItems(Inventory.getAllParts());

        finalPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        finalPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        finalPartStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        finalCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        partInventoryTable.setItems(allParts);

        /////RANDOM ID GENERATOR
        Random randomId = new Random();

        // Generate random ints from 0 to 999
        int idResult = randomId.nextInt(1000);

        //Initialize a new product with default values
        Product product = new Product(0,"na",1,1,1,10);
        transferProduct = product;


        //Set the productId field to the random result and not be editable
        productIdField.setText(String.valueOf(idResult));
        productIdField.setEditable(false);


    }


}
