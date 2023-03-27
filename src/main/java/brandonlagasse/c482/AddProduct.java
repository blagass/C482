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

public class AddProduct implements Initializable {
    public Part transferPart = null;
    ObservableList<Part> finalPartList = FXCollections.observableArrayList();

    public Button cancelAddProduct;
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

    //I decided to make this dope
    public void cancelAddProduct(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,600); // delete this when done
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }
    public void onAddPartButton(ActionEvent actionEvent) {
        transferPart = (Part) partInventoryTable.getSelectionModel().getSelectedItem();
        finalPartList.add(transferPart);
        productFinalTable.setItems(finalPartList);
    }
    public void onRemovePartButton(ActionEvent actionEvent) {
        transferPart = (Part) productFinalTable.getSelectionModel().getSelectedItem();
        finalPartList.remove(transferPart);
        productFinalTable.setItems(finalPartList);
    }

    public void onCancelAddButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,600);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    public void onSaveProductButton(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set up column names for each table
        invPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        invPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        invCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInventoryTable.setItems(Inventory.getAllParts());

        finalPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        finalPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        finalPartStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        finalCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));



    }


}
