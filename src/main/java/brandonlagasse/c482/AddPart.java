package brandonlagasse.c482;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class AddPart implements Initializable {
    public Button cancelAddPart;
    public RadioButton inHouseRadio;
    public RadioButton outsourcedRadio;
    public TextField partIdField;
    public TextField partNameField;
    public TextField partStockField;
    public TextField partCostField;
    public TextField partMaxField;
    public TextField partMachineCompanyField;
    public TextField partMinField;
    public Button savePartButton;
    public ToggleGroup addPartToggle;
    public Label machCoLabel;
    public Pane partId;

    public void toMainScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    public void partSave(ActionEvent actionEvent) throws IOException {
        //Receive String input from associated text fields
        String idStr = partIdField.getText();
        String nameStr = partNameField.getText();
        String stockStr = partStockField.getText();
        String costStr = partCostField.getText();
        String maxStr = partMaxField.getText();
        String minStr = partMinField.getText();
        String machineIdStr = partMachineCompanyField.getText();

        //Convert strings to integers
        int id = Integer.parseInt(idStr);
        int stock = Integer.parseInt(stockStr);
        double cost = Double.parseDouble(costStr);
        int max = Integer.parseInt(maxStr);
        int min = Integer.parseInt(minStr);



        //If InHouse radio button is selected, create a new inHouse Part and add to inventory, else use Outsourced Part.
        if(inHouseRadio.isSelected()) {
            //Parse machineIdStr to an Int
            int machineId = Integer.parseInt(machineIdStr);
            //Create a new inHouse Part
            InHouse inHousePart = new InHouse(id, nameStr, cost, stock, max, min, machineId) {
            };
            inHousePart.setId(id);
            inHousePart.setName(nameStr);
            inHousePart.setPrice(cost);
            inHousePart.setStock(stock);
            inHousePart.setMax(max);
            inHousePart.setMin(min);
            inHousePart.setMachineId(machineId);

            //if(inHousePart instanceof InHouse)
            //    ((InHouse)inHousePart).getMachineId();

            //Add newly created part to the allParts list in Inventory
            Inventory.allParts.add(inHousePart);

        }
            //If inHouse is not selected, make Outsourced Part instead.
            else {
                OutSourced outsourcedPart = new OutSourced(id, nameStr, cost, stock, min, max, machineIdStr);
                outsourcedPart.setId(id);
                outsourcedPart.setName(nameStr);
                outsourcedPart.setPrice(cost);
                outsourcedPart.setStock(stock);
                outsourcedPart.setMin(min);
                outsourcedPart.setMax(max);

                Inventory.allParts.add(outsourcedPart);

            }

        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();

    };


    public void onInHouseRadio(ActionEvent actionEvent) {
        machCoLabel.setText("Machine ID");
    }

    public void onOutsourcedRadio(ActionEvent actionEvent) {
        machCoLabel.setText("Company Name");
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Create a random object
        Random randomId = new Random();

        // Generate random ints from 0 to 999
        int idResult = randomId.nextInt(1000);

        partIdField.setText(String.valueOf(idResult));
        partIdField.setEditable(false);
    }
}
