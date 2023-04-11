package brandonlagasse.c482;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPart implements Initializable {

    public Button cancelModifyPart;
    private static Part transferPart = null;
    public Label machLabel;
    public TextField modifyPartId;
    public TextField modifyPartName;
    public TextField modifyPartStock;
    public TextField modifyPartCost;
    public TextField modifyPartMax;
    public TextField modifyPartMachComp;
    public TextField modifyPartMin;
    public RadioButton inHouseRadio;
    public RadioButton outsourcedRadio;
    public Button saveModifyPart;
    public ToggleGroup modifyButtonToggle;

    /**
     * Passing function for main to add part.
     * @param part to be set as transferPart
     */
    public static void passPart(Part part) {
        transferPart = part;

    }

    public int partIndex = Inventory.allParts.indexOf(transferPart);
    public int modifyId = transferPart.getId();
    public String modifyIdStr = String.valueOf(modifyId); //Converted int to String to display in field


    public String modifyName = transferPart.getName();
    public int modifyStock = transferPart.getStock();
    public String modifyStockStr = String.valueOf(modifyStock); //Converted int to String to display in field

    public double modifyCost = transferPart.getPrice();
    public String modifyCostStr = String.valueOf(modifyCost); //Converted double to String to display in field

    public int modifyMax = transferPart.getMax();
    public String modifyMaxStr = String.valueOf(modifyMax); //Converted int to String to display in field

    public int modifyMin = transferPart.getMin();
    public String modifyMinStr = String.valueOf(modifyMin); //Converted int to String to display in field

    /**
     * This method cancels the modify part function and returns to the main screen.
     * @param actionEvent for Cancel bbutton
     * @throws IOException for IOException
     */
    public void cancelModifyPart(ActionEvent actionEvent) throws IOException {
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
     * Initialize class. This method transfers the part from the main-screen.fxml to transferPart.
     * @param url for future DB connection
     * @param resourceBundle for resources folder
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //Set fields to transferPart attributes
        modifyPartId.setText(modifyIdStr);
        modifyPartName.setText(modifyName);
        modifyPartStock.setText(modifyStockStr);
        modifyPartCost.setText(modifyCostStr);
        modifyPartMax.setText(modifyMaxStr);
        modifyPartMin.setText(modifyMinStr);

        //Logic to see if the transferPart is an instance of InHouse or Outsourced.
        if(transferPart instanceof InHouse){ //Check to see if its in house
             int modifyMachComp = ((InHouse)transferPart).getMachineId();//Cast transferPart as InHouse
             String modifyMachCompStr = String.valueOf(modifyMachComp);//Update the transfer variable
            modifyPartMachComp.setText(modifyMachCompStr); //Update the text field
            inHouseRadio.fire(); // Set radio button to indicate instance type
        }

        else if(transferPart instanceof OutSourced){
            String modifyMachComp = ((OutSourced)transferPart).getCompanyName();
            modifyPartMachComp.setText(modifyMachComp);
            outsourcedRadio.fire();
        }

    }
    //Switch labels on radio button to in house

    /**
     * This method sets the label to Machine ID
     */
    public void onModInHouse() {machLabel.setText("Machine ID");
    }
    //Switch labels on the radio button to outsourced

    /**
     * This method sets the label to Company Name
     */
    public void onModOutsourced() {machLabel.setText("Company Name");
    }

    /**
     * This method saves the fields and final list and returns to the main screen.
     * @param actionEvent for Save button
     * @throws IOException for any IOException
     * RUNTIME ERROR:I had a difficult time isolating the try/catch block around these, and kept running into errors until I realized how to partition the abstract parts, and then check for exceptions. It took me a while to figure out that my initial structure threw an error because it was trying to parse a string into an integer.
     */
    public void onSaveModifyPart(ActionEvent actionEvent) throws IOException {
                    if (inHouseRadio.isSelected()) {
                        //Set new string for the appropriate text fields
                        String modifyIdString = modifyPartId.getText();
                        String modifyNameString = modifyPartName.getText();
                        String modifyStockString = modifyPartStock.getText();
                        String modifyCostString = modifyPartCost.getText();
                        String modifyMaxString = modifyPartMax.getText();
                        String modifyMinString = modifyPartMin.getText();
                        String modifyMachCompString = modifyPartMachComp.getText();

                        //Switch Strings to correct types
                        int id = Integer.parseInt(modifyIdString);
                        int stock = Integer.parseInt(modifyStockString);
                        double cost = Double.parseDouble(modifyCostString);
                        int max = Integer.parseInt(modifyMaxString);
                        int min = Integer.parseInt(modifyMinString);

                        //CREATE INHOUSE PART
                        int machineId = Integer.parseInt(modifyMachCompString);

                        //Create a new Part
                        InHouse modifiedInHousePart = new InHouse(id, modifyNameString, cost, stock, max, min, machineId) {};
                        //Transfer variables to new inHousePart
                        modifiedInHousePart.setId(id);
                        modifiedInHousePart.setName(modifyNameString);
                        modifiedInHousePart.setPrice(cost);
                        modifiedInHousePart.setStock(stock);
                        modifiedInHousePart.setMax(max);
                        modifiedInHousePart.setMin(min);
                        modifiedInHousePart.setMachineId(machineId);

                        try {
                            if (modifiedInHousePart.getMin() > modifiedInHousePart.getMax()) {
                                throw new ArithmeticException("Min is greater than Max.");
                            }
                            try {
                                if (modifiedInHousePart.getStock() < modifiedInHousePart.getMin() || modifiedInHousePart.getStock() > modifiedInHousePart.getMax()) {
                                    throw new ArithmeticException("Inventory must be between Min and Max");
                                }

                                Inventory.updatePart(partIndex, modifiedInHousePart);

                                Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
                                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                                Scene scene = new Scene(root, 800, 600);
                                String css = this.getClass().getResource("style.css").toExternalForm();
                                scene.getStylesheets().add(css);
                                stage.setTitle("Add Part");
                                stage.setScene(scene);
                                stage.show();

                            }catch(ArithmeticException e) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Uh oh");
                                alert.setContentText("Inventory must be between Min and Max.");
                                alert.showAndWait();
                            }
                        }catch(ArithmeticException e){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Uh oh");
                            alert.setContentText("Max must be greater than Min.");
                            alert.showAndWait();
                        }


                    } else if (outsourcedRadio.isSelected()) {
                        //Set new string for the appropriate text fields
                        String modifyIdString = modifyPartId.getText();
                        String modifyNameString = modifyPartName.getText();
                        String modifyStockString = modifyPartStock.getText();
                        String modifyCostString = modifyPartCost.getText();
                        String modifyMaxString = modifyPartMax.getText();
                        String modifyMinString = modifyPartMin.getText();
                        String modifyMachCompString = modifyPartMachComp.getText();

                        //Switch Strings to correct types
                        int id = Integer.parseInt(modifyIdString);
                        int stock = Integer.parseInt(modifyStockString);
                        double cost = Double.parseDouble(modifyCostString);
                        int max = Integer.parseInt(modifyMaxString);
                        int min = Integer.parseInt(modifyMinString);
                        //CREATE OUTSOURCED PART
                        OutSourced modifiedOutSourcedPart = new OutSourced(id, modifyNameString, cost, stock, max, min, modifyMachCompString);

                        //Transfer variables to new outsourcedPart
                        modifiedOutSourcedPart.setId(id);
                        modifiedOutSourcedPart.setName(modifyNameString);
                        modifiedOutSourcedPart.setPrice(cost);
                        modifiedOutSourcedPart.setStock(stock);
                        modifiedOutSourcedPart.setMin(min);
                        modifiedOutSourcedPart.setMax(max);
                        modifiedOutSourcedPart.setCompanyName(modifyMachCompString);

                        try {

                            if (modifiedOutSourcedPart.getMin() > modifiedOutSourcedPart.getMax()) {
                                throw new ArithmeticException("Min is greater than Max.");
                            }

                            try {

                                if (modifiedOutSourcedPart.getStock() < modifiedOutSourcedPart.getMin() || modifiedOutSourcedPart.getStock() > modifiedOutSourcedPart.getMax()) {
                                    throw new ArithmeticException("Inventory must be between Min and Max");
                                }

                                Inventory.updatePart(partIndex, modifiedOutSourcedPart);

                                Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
                                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                                Scene scene = new Scene(root, 800, 600);
                                String css = this.getClass().getResource("style.css").toExternalForm();
                                scene.getStylesheets().add(css);
                                stage.setTitle("Add Part");
                                stage.setScene(scene);
                                stage.show();

                            }catch(ArithmeticException e){
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Uh oh");
                                alert.setContentText("Inventory must be between Min and Max.");
                                alert.showAndWait();
                            }
                        }catch(ArithmeticException e){
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Uh oh");
                            alert.setContentText("Max must be greater than Min.");
                            alert.showAndWait();
                        }

                    }

    }
}
