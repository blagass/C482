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
    //Part object used for transferring between main and add part windows.
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

    //Passing function for main to add part.
    public static void passPart(Part part) {
        transferPart = part;

    }
    //Transfer Part attributes to variable that label displays will pull from
    //DOING THIS OVER - I would execute the duplication of efforts in each controller to a single method
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


    ////////NEXT: ADD RADIO BUTTON GROUP AND MACHINE ID

    public void cancelModifyPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();
    }

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
    //Switch labels on radio buttons
    public void onModInHouse(ActionEvent actionEvent) {machLabel.setText("Machine ID");
    }

    public void onModOutsourced(ActionEvent actionEvent) {machLabel.setText("Company Name");
    }

    public void onSaveModifyPart(ActionEvent actionEvent) throws IOException {
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

        if(inHouseRadio.isSelected()){

            int machineId = Integer.parseInt(modifyMachCompString);

            //Create a new Part
            InHouse modifiedInHousePart = new InHouse(id, modifyNameString, cost, stock, max, min, machineId) {
            };
            //Add new part with new attributes to allParts table
            Inventory.allParts.remove(partIndex);
            Inventory.allParts.add(modifiedInHousePart);


        }
        else if(outsourcedRadio.isSelected()){ //Doing this over I would find a way to not separate house and outsourced cleaner.
            OutSourced modifiedOutSourcedPart = new OutSourced(id, modifyNameString, cost, stock, max, min, modifyMachCompString);
            Inventory.allParts.remove(partIndex);
            Inventory.allParts.add(modifiedOutSourcedPart);
        }
        //Load main scene
        Parent root = FXMLLoader.load(getClass().getResource("main-view.fxml"));
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800,600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();

    }
}
