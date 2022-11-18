package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.OutSourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;


/** Implements main logic for the "modify part" page.
 *
 * */
public class ModifyPart implements Initializable {

    /** Radio Buttons and Save / Cancel Buttons*/
    public  RadioButton inHouse; public  RadioButton Outsourced;
    public Button savePart; public Button cancelButton; public  Label machineOrCompany;

    /** Radio Buttons and Save / Cancel Buttons*/

    /** Text Field Definitions **/
    public  TextField partID; public  TextField partName; public  TextField partInv; public  TextField partPrice;
    public  TextField partMax; public  TextField machineIDorCompany; public  TextField partMin;
    public Label errorBox;
    /** Text Field Definitions **/

    private int index;
    public Part thePart = null;

    /** Button Actions*/


    /**Cancel Button returns to the Main Form.
     *
     * @param actionEvent cancel button action
     * @throws IOException from FXMLLoader
     *
     * */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,1200,600);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

    /**Set's the in house text.
     *
     * @param actionEvent sets text
     *
     *
     * */
    public void onInHouse(ActionEvent actionEvent) {
        this.machineOrCompany.setText("Machine ID");
    }

    /**Set's the OutSourced text.
     *
     * @param actionEvent sets text
     *
     * */
    public void onOutsourced(ActionEvent actionEvent) {
        this.machineOrCompany.setText("Company Name");
    }



    /**Set info from selected part on main screen.
     *
     * @param selectedPart the part chosen from main screen
     *
     * */
    public void setPartInfo(Part selectedPart) {
        this.thePart = selectedPart;
        partID.setText(Integer.toString(thePart.getId()));

        index = Inventory.getAllParts().indexOf(selectedPart);

        partName.setText(selectedPart.getName());
        partPrice.setText(Double.toString(selectedPart.getPrice()));
        partInv.setText(Integer.toString(selectedPart.getStock()));
        partMax.setText(Integer.toString(selectedPart.getMax()));
        partMin.setText(Integer.toString(selectedPart.getMin()));

        if (selectedPart instanceof InHouse){
            inHouse.setSelected(true);
            this.machineOrCompany.setText("Machine ID");
            machineIDorCompany.setText(Integer.toString(((InHouse) selectedPart).getMachineID()));
        } else {
            Outsourced.setSelected(true);
            this.machineOrCompany.setText("Company Name");
            OutSourced os = (OutSourced) selectedPart;
            machineIDorCompany.setText(os.getCompanyName());
        }

    }

    /**Saves the users input for the new part.
     *
     * @param actionEvent save button action
     * @throws IOException from FXMLLoader
     *
     * */
    public void onSavePart(ActionEvent actionEvent) throws IOException {

        int inv = parseInt(partInv.getText());
        int max = parseInt(partMax.getText());
        int min = parseInt(partMin.getText());

        // Check for proper inputs
        if (max < min) {
            errorBox.setText("Error: Check Min and Max values");
        } else if (inv < min || inv > max) {

            errorBox.setText("Error: Check Min, Max, and Inv values");
        } else  {
            // New inHouse Part
            errorBox.setText("");
            if (inHouse.isSelected()) {
                try {

                    // Get Part ID, name , price
                    int ID = Integer.parseInt(partID.getText());
                    String name = partName.getText();
                    double price = Double.parseDouble(partPrice.getText());

                    // Get Machine ID
                    int machineId = parseInt(machineIDorCompany.getText());
                    //Create a part from the modifications
                    InHouse temp = new InHouse(ID, name, price, inv, min, max, machineId);

                    // Add the new part\
                    Inventory.updatePart(index, temp);

                    // Leave to go to Main Page after saving
                    Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 1200, 600);
                    stage.setTitle("Main Form");
                    stage.setScene(scene);
                    stage.show();
                }
                catch (NumberFormatException e ) {

                    errorBox.setText("Error: Check Machine ID!");
                }


            }  // New outSourced Part
            else if (Outsourced.isSelected()) {

                // Get Part ID, name , price
                int ID = Integer.parseInt(partID.getText());
                String name = partName.getText();
                double price = Double.parseDouble(partPrice.getText());

                // Get Company Name
                String compName = machineIDorCompany.getText();
                // create a part from the modifications
                OutSourced temp = new OutSourced(ID, name, price, inv, min, max, compName);
                // Add the new part
                Inventory.updatePart(index, temp);

                // Leave to go to Main Page after saving
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1200, 600);
                stage.setTitle("Main Form");
                stage.setScene(scene);
                stage.show();
                }
            }

    }

    /**
     * Initializes controller and sets in-house radio button to true.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }



}
