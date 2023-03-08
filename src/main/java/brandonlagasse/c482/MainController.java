package brandonlagasse.c482;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
    public TableColumn partInventoryLevel;
    public TableColumn partCostPerUnit;
    public TableView productTable;
    public TableColumn productId;
    public TableColumn productName;
    public TableColumn productInventoryLevel;
    public TableColumn productCostPerUnit;

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
        Parent root = FXMLLoader.load(getClass().getResource("modify-part.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    public void toDeletePart(ActionEvent actionEvent) {
    }
    public void toDeleteProduct(ActionEvent actionEvent) {

    }

    private void addTestData() {
        InHouse I1 = new InHouse(10, "cool part", 10.00, 10, 1,20,101);
        Inventory.addPart(I1);
        InHouse I2 = new InHouse(9, "lame part", 1.00, 6, 1,20,102);
        Inventory.addPart(I2);
        InHouse I3 = new InHouse(8, "meh part", 5.00, 40, 1,20,103);
        Inventory.addPart(I3);
        OutSourced O1 = new OutSourced(7, "cool part", 10.00, 10, 1,20,"Joes");
        Inventory.addPart(O1);
        OutSourced O2 = new OutSourced(6, "lame part", 1.00, 6, 1,20,"Franks");
        Inventory.addPart(O2);
        OutSourced O3 = new OutSourced(5, "meh part", 5.00, 40, 1,20,"Ritas");
        Inventory.addPart(O3);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addTestData();

    }

}