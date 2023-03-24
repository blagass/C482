package brandonlagasse.c482;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    public TextField modifyMachComp;
    public TextField modifyPartMin;

    //Passing function for main to add part.
    public static void passPart(Part part) {
        transferPart = part;

    }
    //Transfer Part attributes to variable that label displays will pull from
    public int modifyId = transferPart.getId();
    public String modifyName = transferPart.getName();
    public int modifyStock = transferPart.getStock();
    public double modifyCost = transferPart.getPrice();
    public int modifyMax = transferPart.getMax();
    public int modifyMin = transferPart.getMin();

    //Check for Part instance type, and set the label to the appropriate one.
/*   // public void partType(Part part) {
        if (part instanceof InHouse){
         machLabel.setText("Machine ID");
        }
        else if (part instanceof OutSourced)
            machLabel.setText("Company Name");
    }*/
/// START HERE BY PULLING IN RADIO BUTTON FUNCTIONALITY FROM MAIN VIEW





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


    }
}
