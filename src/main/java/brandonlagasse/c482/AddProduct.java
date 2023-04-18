package brandonlagasse.c482;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

import static brandonlagasse.c482.Inventory.allParts;
import static brandonlagasse.c482.Inventory.getAllParts;

public class AddProduct implements Initializable {
    public Part transferPart = null;
    public Product transferProduct;
    public TextField productIdField;
    public TextField productNameField;
    public TextField productStockField;
    public TextField productPriceField;
    public TextField productMaxField;
    public TextField productMinField;
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
    public TableView partTable;
    public TextField partQuery;

    /**
     * This event is fired when the Add Part button is clicked to transfer a Part from one table to another.
     */
    public void onAddPartButton() {
        transferPart = (Part) partTable.getSelectionModel().getSelectedItem();
        transferProduct.addAssociatedPart(transferPart);
        productFinalTable.setItems(transferProduct.getAllAssociatedParts());
        //This needs to be redone to work with the missing functions from Product.java
    }

    /**
     * This event is fired when the Remove Part button is clicked to remove a part from the Final Part Table.
     */
    public void onRemovePartButton() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Warning!");
        alert.setTitle("You're about to remove a part from the list.");
        alert.setContentText("Remove part?");

        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            transferPart = (Part) productFinalTable.getSelectionModel().getSelectedItem();
            transferProduct.deleteAssociatedPart(transferPart);
            productFinalTable.setItems(transferProduct.getAllAssociatedParts());
        }
    }

    /**
     * This action event cancels the Add Product screen. When the button is pressed, it sends the user back to the main screen.
     * @param actionEvent cancels the current product creation.
     * @throws IOException for an I/O error
     */
    public void onCancelAddButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,600);
        String css = this.getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This action even is fired when someone wants to save the changes they made on the screen. It will save the product and send the user back to the main screen.
     * @param actionEvent to save the current product build
     * @throws IOException for an I/O error
     */
    public void onSaveProductButton(ActionEvent actionEvent) throws IOException {
        try {
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

            try {

                if (transferProduct.getMin() > transferProduct.getMax()) {
                    throw new ArithmeticException("Min is greater than Max.");
                }
                    try {
                        if(transferProduct.getStock() < transferProduct.getMin() || transferProduct.getStock() > transferProduct.getMax()){
                            throw new ArithmeticException("Inventory must be between Min and Max");
                        }
                        //Add the final transferProduct to the allProducts list in Inventory
                        Inventory.addProduct(transferProduct);

                        //Go back to the main screen
                        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root, 800, 600);
                        String css = this.getClass().getResource("style.css").toExternalForm();
                        scene.getStylesheets().add(css);
                        stage.setTitle("Add Part");
                        stage.setScene(scene);
                        stage.show();
                    }
                    catch(Exception e){ //Alert for Stock
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Uh oh");
                        alert.setContentText("Inventory must be between Min and Max.");
                        alert.showAndWait();
                    }
            }
            catch (Exception e){ //Alert for Min/Max
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Uh oh");
                alert.setContentText("Max must be greater than Min.");
                alert.showAndWait();
            }
        }
        catch (NumberFormatException nfe) { // Alert for format and blanks.
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Uh oh");
            alert.setContentText("Incorrect format. Please check your input. Fields cannot be blank.");
            alert.showAndWait();
        }

    }

    /**
     * Initialing class. This sets the tables, and pulls in the transfer product from the main screen.
     * @param url possible db connection for future build
     * @param resourceBundle for the resources folder
     */
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

        partTable.setItems(allParts);

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


    public void onAddProductSearch(ActionEvent actionEvent) {
        ///Receive user input
        String query = partQuery.getText();

        if(query.isEmpty()){
            partTable.setItems(allParts);
            Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong");
            alert.setTitle("Uh oh!");
            alert.setContentText("Please enter a valid Part ID or Name. Field cannot be blank.");
            alert.showAndWait();
        }else {

            try {
                //Add query results to a list of parts
                ObservableList<Part> parts = Inventory.lookupPart(query);

                //Instead, if an Id is used and the parts list is = 0, search by id and add to parts list.
                if (parts.size() == 0) {
                    //Take the query and parse into an id.
                    int id = Integer.parseInt(query);

                    //Look up id and put into part
                    Part part = Inventory.lookupPart(id);

                    //If a part is found, add it to parts.
                    if (part != null) {
                        parts.add(part);
                    }else { //If no part is found, throw alert
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong");
                        alert.setTitle("Uh oh!");
                        alert.setContentText("No matching ID found.");
                        alert.showAndWait();
                    }
                }

                //Finally, add the parts list to the parts table.
                partTable.setItems(parts);

            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Something went wrong");
                alert.setTitle("Uh oh!");
                alert.setContentText("No matching name found.");
                alert.showAndWait();
            }
        }
    }
}
