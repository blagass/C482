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
import java.security.PrivateKey;
import java.util.Optional;
import java.util.ResourceBundle;

import static brandonlagasse.c482.Inventory.*;


public class MainController implements Initializable{

    public Button addPart;
    public Button addProduct;
    public Button modifyProduct;
    public Button deleteProduct;
    public Button modifyPart;
    public Button deletePart;
    public TableView<Part> partTable;
    public TableColumn partId;
    public TableColumn partName;
    public TableColumn partStock;
    public TableColumn partCost;
    public TableView<Product> productTable;
    public TableColumn productId;
    public TableColumn productName;
    public TableColumn productStock;
    public TableColumn productCost;
    public TextField partQuery;
    public TextField productQuery;

    /**
     * This is an action event to switch scenes to the Add Part view. This action also loads the common css file.
     * @param actionEvent for Add Part button
     * @throws IOException for I/O exceptions
     */
    public void toAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add-part.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,600);
        String css = this.getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This is the action event to switch to the Add Product view
     * @param actionEvent for Add Product button
     * @throws IOException for I/O exceptions
     */
    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add-product.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,600);
        String css = this.getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This is the action event to swtich scenes to the Modify Product view.
     * @param actionEvent for Modify Product button
     * @throws IOException for IOException
     */
    public void toModifyProduct(ActionEvent actionEvent) throws IOException{

        try {
            //Grab transfer part from ModifyProduct
            ModifyProduct.passProduct(productTable.getSelectionModel().getSelectedItem());

            //Load modify product scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modify-product.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 600);
            String css = this.getClass().getResource("style.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setTitle("Add Part");
            stage.setScene(scene);
            stage.show();
        }catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Uh oh!");
            alert.setContentText("You must select a Product to modify.");
            alert.showAndWait();
        }
    }

    /**
     * This is the action event to switch scenes to the Modify Part view
     * @param actionEvent for Modify Part button
     * @throws IOException for an I/O error
     */
    public void toModifyPart(ActionEvent actionEvent) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("modify-part.fxml"));
        try {
            //MODIFY PART PASS
            //Get selected item in the table and send it to passPart method in ModifyPart controller.
            ModifyPart.passPart(partTable.getSelectionModel().getSelectedItem());  // Get part and set to transfer part.


            //Loading ModifyPart scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modify-part.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 600);
            String css = this.getClass().getResource("style.css").toExternalForm();
            scene.getStylesheets().add(css);
            stage.setTitle("Modify Part");
            stage.setScene(scene);
            stage.show();
        }catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Uh oh!");
            alert.setContentText("You must select a Part to modify.");
            alert.showAndWait();
        }
    }

    /**
     * This is the action event to delete a part. This includes an Alert warning before the user deletes the part.
     * @param actionEvent for Delete Part button
     */
    public void toDeletePart(ActionEvent actionEvent) {
        Alert partAlert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this part? This cannot be undone.");
        Optional<ButtonType> result = partAlert.showAndWait();

        if(result.isPresent() && result.get() == ButtonType.OK) {
            Part part = partTable.getSelectionModel().getSelectedItem();
            if (part != null) {
                deletePart(partTable.getSelectionModel().getSelectedItem());
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING,"Something went wrong");
                alert.setTitle("Uh oh!");
                alert.setContentText("Please select something to delete.");
                alert.showAndWait();
            }

        }


    }

    /**
     * This method deletes a selected product.
     * @param actionEvent for Delete Product button
     */
    public void toDeleteProduct(ActionEvent actionEvent) {

        try{
            Product testProduct;

            testProduct = productTable.getSelectionModel().getSelectedItem();
            if(!testProduct.getAllAssociatedParts().isEmpty()){

                throw new Exception("Cannot delete a Product with associated Parts.");
            }
            else{
                Alert productAlert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete this product? It cannot be brought back");
                Optional<ButtonType> result = productAlert.showAndWait();


                if(result.isPresent() && result.get() == ButtonType.OK) {
                    Product product = productTable.getSelectionModel().getSelectedItem();

                    if (product != null) {
                        deleteProduct(productTable.getSelectionModel().getSelectedItem());
                    }else{
                        Alert alert = new Alert(Alert.AlertType.WARNING,"Something went wrong");
                        alert.setTitle("Uh oh!");
                        alert.setContentText("Please select something to delete.");
                        alert.showAndWait();
                    }
                }

            }

        } catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.WARNING,"Something went wrong");
            alert.setTitle("Uh oh!");
            alert.setContentText("Pleae select a product to delete.");
            alert.showAndWait();
        }


    }


    /**
     * Set Table and colum values.
     * @param url for future DB connection
     * @param resourceBundle for resource location
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //addTestData();


        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        productId.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCost.setCellValueFactory(new PropertyValueFactory<>("price"));

        partTable.setItems(allParts);
        productTable.setItems(allProducts);

    }

    //////// PART SEARCH ////////

    /**
     * Results handler for Parts search field
     * @param actionEvent for part search
     */
    public void getPartResults(ActionEvent actionEvent){
        //Receive user input
        String query = partQuery.getText();

        if(query.isEmpty()){
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

    ////////   PRODUCT SEARCH   ////////

    /**
     * Results handler for the product search field
     * @param actionEvent for product search
     */
    public void getProductResults(ActionEvent actionEvent) {
        //Receive user input
        String query = productQuery.getText();

        //Add query results to a list of products
        ObservableList<Product>products = Inventory.lookupProduct(query);

        //Instead, if an Id is used and the products list is > 0, search by id and add to products list.
        if (products.size() == 0) {
            int id = Integer.parseInt(query);
            Product product = Inventory.lookupProduct(id);
            if (product != null)
                products.add(product);
        }

        //Finally, add the parts list to the products table.
        productTable.setItems(products);
    }

}