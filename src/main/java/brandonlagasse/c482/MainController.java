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
import java.security.PrivateKey;
import java.util.ResourceBundle;

import static brandonlagasse.c482.Inventory.allParts;
import static brandonlagasse.c482.Inventory.allProducts;


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


    public void toAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add-part.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("add-product.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,600);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();
    }

    public void toModifyProduct(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("modify-product.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }


    public void toModifyPart(ActionEvent actionEvent) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("modify-part.fxml"));

        //MODIFY PART PASS
        //Get selected item in the table and send it to passPart method in ModifyPart controller.
        ModifyPart.passPart(partTable.getSelectionModel().getSelectedItem());  // MAY NOT BE WORKING


        //Loading ModifyPart scene
        FXMLLoader loader = new FXMLLoader(getClass().getResource("modify-part.fxml"));
        Parent root = loader.load();



        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,600);
        stage.setTitle("Modify Part");
        stage.setScene(scene);
        stage.show();
    }

    public void toDeletePart(ActionEvent actionEvent) {
    }
    public void toDeleteProduct(ActionEvent actionEvent) {

    }

    private void addTestData() {
        InHouse cool_part = new InHouse(10, "cool part", 10.00, 10, 1,20,101);
        Inventory.addPart(cool_part);
        OutSourced rad_part = new OutSourced(7, "rad part", 10.00, 10, 1,20,"Joes");
        Inventory.addPart(rad_part);


        Product nice_product= new Product(200,"nice product",400,20,1,40);
        Inventory.addProduct(nice_product);
        Product groovy_product = new Product(400,"groovy product",700,20,1,50);
        Inventory.addProduct(nice_product);

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTestData();


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

    // Results handler for Parts search field
    public void getPartResults(ActionEvent actionEvent){

        //Receive user input
        String query = partQuery.getText();

        //Add query results to a list of parts
        ObservableList<Part>parts = searchByPartName(query);


        //Instead, if an Id is used and the parts list is > 0, search by id and add to parts list.
        if(parts.size() == 0){
            int id = Integer.parseInt(query);
            Part part = searchByPartId(id);
            if (part != null)
                parts.add(part);
        }

        //Finally, add the parts list to the parts table.
        partTable.setItems(parts);

    };


    // Search by Part name
    private ObservableList<Part>searchByPartName(String partialName){

        //This is the collection for the result of the search
        ObservableList<Part>partNames = FXCollections.observableArrayList();

        //This list returns all parts in the inventory
        ObservableList<Part>allParts = Inventory.getAllParts();

        for(Part part : allParts){
            if(part.getName().contains(partialName)){
                partNames.add(part);
            };
        }

        return partNames;


    };

    //Search by Part ID
    private Part searchByPartId (int id){
        ObservableList<Part>allParts = Inventory.getAllParts();

        //For each part in our parts list, return the id if there is a match, else return null.
        for (Part part : allParts) {
            if (part.getId() == id) {
                return part;
            }
        }

        return null;
    };


    ////////   PRODUCT SEARCH   ////////

    //Results handler for the product search field
    public void getProductResults(ActionEvent actionEvent) {
        //Receive user input
        String query = productQuery.getText();

        //Add query results to a list of products
        ObservableList<Product>products = searchByProductName(query);


        //Instead, if an Id is used and the products list is > 0, search by id and add to products list.
        if(products.size() == 0){
            int id = Integer.parseInt(query);
            Product product = searchByProductId(id);
            if (product != null)
                products.add(product);
        }

        //Finally, add the parts list to the products table.
        productTable.setItems(products);
    }

    //Search by Product id
    private Product searchByProductId(int id) {
        ObservableList<Product>allProducts = Inventory.getAllProducts();

        //For each part in our products list, return the id if there is a match, else return null.
        for (Product product : allProducts) {
            if (product.getId() == id) {
                return product;
            }
        }

        return null;
    }

    //Search by Product name
    private ObservableList<Product> searchByProductName(String partialName) {
        //This is the collection for the result of the search
        ObservableList<Product>productNames = FXCollections.observableArrayList();

        //This list returns all parts in the inventory
        ObservableList<Product>allProducts = Inventory.getAllProducts();

        for(Product product : allProducts){
            if(product.getName().contains(partialName)){
                productNames.add(product);
            };
        }

        return productNames;

    };
}