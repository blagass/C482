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

    /**
     * This is the main method. This method sets the Parent root, stage, scene, and CSS>
     * @param actionEvent to be called when returning to main
     * @throws IOException for an I/O error
     */
    public void toMainScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,600);
        String css = this.getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * This method saves the part and returns to the main screen.
     * Field Strings are first changed to their proper types, and a new Part is created depending on which radio button is selected.
     * @param actionEvent to be fired when clicking on Save button.
     * @throws IOException for an I/O error
     */
    public void partSave(ActionEvent actionEvent) {
try {
    //If InHouse radio button is selected, create a new inHouse Part and add to inventory, else use Outsourced Part.
    if (inHouseRadio.isSelected()) {

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

        //CREATE AN IN HOUSE PART
        //Parse machineIdStr to an Int
        int machineId = Integer.parseInt(machineIdStr);

        //Create a new inHouse Part
        InHouse inHousePart = new InHouse(id, nameStr, cost, stock, max, min, machineId) {
        };

        //Transfer variables to new inHousePart
        inHousePart.setId(id);
        inHousePart.setName(nameStr);
        inHousePart.setPrice(cost);
        inHousePart.setStock(stock);
        inHousePart.setMax(max);
        inHousePart.setMin(min);
        inHousePart.setMachineId(machineId);
        try {
            if (inHousePart.getMin() > inHousePart.getMax()) {
                throw new ArithmeticException("Min is greater than Max.");
            }
            try {
                if (inHousePart.getStock() < inHousePart.getMin() || inHousePart.getStock() > inHousePart.getMax()) {
                    throw new ArithmeticException("Inventory must be between Min and Max");
                }

                //Add newly created part to the allParts list in Inventory
                Inventory.addPart(inHousePart);


                Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 800, 600);
                String css = this.getClass().getResource("style.css").toExternalForm();
                scene.getStylesheets().add(css);
                stage.setTitle("Add Part");
                stage.setScene(scene);
                stage.show();

            } catch (ArithmeticException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Uh oh");
                alert.setContentText("Inventory must be between Min and Max.");
                alert.showAndWait();
            }

        } catch (ArithmeticException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Uh oh");
            alert.setContentText("Max must be greater than Min.");
            alert.showAndWait();
        }
    }

    //If inHouse is not selected, make Outsourced Part instead.
    else {
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

        //Create an outsourced Part
        OutSourced outsourcedPart = new OutSourced(id, nameStr, cost, stock, min, max, machineIdStr) {
        };

        //Transfer variables to new outsourcedPart
        outsourcedPart.setId(id);
        outsourcedPart.setName(nameStr);
        outsourcedPart.setPrice(cost);
        outsourcedPart.setStock(stock);
        outsourcedPart.setMin(min);
        outsourcedPart.setMax(max);
        outsourcedPart.setCompanyName(machineIdStr);

        try {

            if (outsourcedPart.getMin() > outsourcedPart.getMax()) {
                throw new ArithmeticException("Min is greater than Max.");
            }
            try {

                if (outsourcedPart.getStock() < outsourcedPart.getMin() || outsourcedPart.getStock() > outsourcedPart.getMax()) {
                    throw new ArithmeticException("Inventory must be between Min and Max");
                }

                Inventory.addPart(outsourcedPart);

                Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 800, 600);
                String css = this.getClass().getResource("style.css").toExternalForm();
                scene.getStylesheets().add(css);
                stage.setTitle("Add Part");
                stage.setScene(scene);
                stage.show();

            } catch (ArithmeticException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Uh oh");
                alert.setContentText("Inventory must be between Min and Max.");
                alert.showAndWait();
            }
        } catch (ArithmeticException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Uh oh");
            alert.setContentText("Max must be greater than Min.");
            alert.showAndWait();
        }
    }
}catch (Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Uh oh!");
        alert.setContentText("Fields must not be blank.");
        alert.showAndWait();
}


    };

    /**
     * This action event sets the Radio label to In House.
     */
    public void onInHouseRadio() {
        machCoLabel.setText("Machine ID");
    }

    /**
     * This action event sets the Radio label to Outsourced.
     */
    public void onOutsourcedRadio() {
        machCoLabel.setText("Company Name");
    }

    /**
     *
     * @param url for future DB connection
     * @param resourceBundle resource pointer to the resources folder
     */
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
