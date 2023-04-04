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
import java.util.ResourceBundle;

import static brandonlagasse.c482.Inventory.allParts;
import static brandonlagasse.c482.Inventory.getAllParts;

public class ModifyProduct implements Initializable {
    private static Product transferProduct;
    private static Part transferPart;
    ObservableList<Part>finalPartList = FXCollections.observableArrayList();

    public int productIndex = Inventory.allProducts.indexOf(transferProduct);
    public Button cancelModifyProduct;
    public TextField modIdField;
    public TextField modNameField;
    public TextField modInvField;
    public TextField modPriceField;
    public TextField modMaxField;
    public TextField modMinField;
    public TextField partQueryField;
    public TableView modPartTable;
    public Button partAddButton;
    public TableView modFinalPartTable;
    public Button partRemoveButton;
    public Button modifySaveButton;
    public TableColumn invPartIdCol;
    public TableColumn invPartNameCol;
    public TableColumn invStockCol;
    public TableColumn invCostCol;
    public TableColumn finalPartIdCol;
    public TableColumn finalPartNameCol;
    public TableColumn finalPartStockCol;
    public TableColumn finalCostCol;

    //Passing function for main to add product.
    public static void passProduct(Product product) {
        transferProduct = product;
    }
    public static void passPart(Part part) {
        transferPart = part;
    }


    public int modifyId = transferProduct.getId();
    public String modifyIdStr = String.valueOf(modifyId); //Converted int to String to display in field


    public String modifyName = transferProduct.getName();
    public int modifyStock = transferProduct.getStock();
    public String modifyStockStr = String.valueOf(modifyStock); //Converted int to String to display in field

    public double modifyCost = transferProduct.getPrice();
    public String modifyCostStr = String.valueOf(modifyCost); //Converted double to String to display in field

    public int modifyMax = transferProduct.getMax();
    public String modifyMaxStr = String.valueOf(modifyMax); //Converted int to String to display in field

    public int modifyMin = transferProduct.getMin();
    public String modifyMinStr = String.valueOf(modifyMin); //Converted int to String to display in field

    public void cancelModifyProduct(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    //PART SEARCH////////////////////////////////
    public void onPartQuery(ActionEvent actionEvent) {
        //Receive user input
        String query = partQueryField.getText();

        //Add query results to a list of parts
        ObservableList<Part>parts = searchByPartName(query);


        //Instead, if an id is used and the parts list is > 0, search by id and add to parts list.
        if(parts.size() == 0){
            int id = Integer.parseInt(query);
            Part part = searchByPartId(id);
            if (part != null)
                parts.add(part);
        }

        //Finally, add the parts list to the parts table.
        modPartTable.setItems(parts);

    }
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
    //PART SEARCH END////////////////////////////////
    public void onPartAdd(ActionEvent actionEvent) {

        transferProduct.addAssociatedPart((Part)modPartTable.getSelectionModel().getSelectedItem());
        finalPartList = transferProduct.getAllAssociatedParts();
        modFinalPartTable.setItems(finalPartList);


    }

    public void onPartRemove(ActionEvent actionEvent) {
        transferProduct.deleteAssociatedPart((Part)modPartTable.getSelectionModel().getSelectedItem());
        finalPartList = transferProduct.getAllAssociatedParts();
        modFinalPartTable.setItems(finalPartList);
    }

    public void onCancelModify(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    public void onModifySave(ActionEvent actionEvent) throws IOException {
        //Get Field text and add to strings
        String prodIdStr = modIdField.getText();
        String prodNameStr = modNameField.getText();
        String prodStockStr = modInvField.getText();
        String prodPriceStr = modPriceField.getText();
        String prodMaxStr = modMaxField.getText();
        String prodMinStr = modMinField.getText();

        //Convert strings into integers
        int id = Integer.parseInt(prodIdStr);
        int stock = Integer.parseInt(prodStockStr);
        double cost = Double.parseDouble(prodPriceStr);
        int max = Integer.parseInt(prodMaxStr);
        int min = Integer.parseInt(prodMinStr);

        //Product product = new Product(id,prodNameStr,cost,stock,max,min){};


        transferProduct.setId(id);
        transferProduct.setName(prodNameStr);
        transferProduct.setPrice(cost);
        transferProduct.setStock(stock);
        transferProduct.setMax(max);
        transferProduct.setMin(min);


        //Update inventory with the product
        Inventory.updateProduct(productIndex,transferProduct);



        //Go back to the main screen
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
        //RESUME HERE

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

        modPartTable.setItems(allParts);
        modFinalPartTable.setItems(transferProduct.getAllAssociatedParts());

        //Set fields to transferProduct attributes
        modIdField.setText(modifyIdStr);
        modNameField.setText(modifyName);
        modInvField.setText(modifyStockStr);
        modPriceField.setText(modifyCostStr);
        modMaxField.setText(modifyMaxStr);
        modMinField.setText(modifyMinStr);

        modIdField.setEditable(false);
    }
}
