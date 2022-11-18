package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Inventory;
import model.InHouse;
import model.OutSourced;
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

/** This controller class controls the add part screen */
public class AddPart implements Initializable {
    /** Button and Label Definitions **/
    public Button addPartCancel; public Button savePart; public RadioButton inHousePart;
    public RadioButton outsourcedPart;
    public Label machineOrCompany; public Label errorBox;
    /** End of Button and Label Definitions **/
    
    /** Text Field Definitions **/
    public TextField partName; public TextField partInv; public TextField partPrice;
    public TextField partMax; public TextField partMin; public TextField partIDorCompany;
    /** Text Field Definitions **/

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();


    /**
     * Exits to the main form.
     *
     * @param actionEvent Cancel button action
     * @throws IOException from FXMLLoader
     *
     */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,1200,600);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Will save the new part info being entered by the user.
     *
     * Error message if info doesn't save properly.
     * @param actionEvent saves part
     * @throws IOException from FXMLLoader
     *
     * */
    public void onSavePart(ActionEvent actionEvent) throws IOException {

        try {

            // Part Variable Definitions
            String name = partName.getText();
            int inv = parseInt(partInv.getText());
            double price = Double.parseDouble(partPrice.getText());
            int max = parseInt(partMax.getText());
            int min = parseInt(partMin.getText());


            // Check for proper inputs
             if (max < min) {
                errorBox.setVisible(true);
                errorBox.setText("Error: Check Min and Max values");
            }else if (inv < min || inv > max) {
                errorBox.setVisible(true);
                errorBox.setText("Error: Check Min, Max, and Inv values");
            } else  {
                // New inHouse Part
                if (inHousePart.isSelected()) {
                    // Get Machine ID
                    int machineId = parseInt(partIDorCompany.getText());
                    // Add the new part
                    Inventory.addPart(new InHouse(getID(), name, price, inv, min, max, machineId));
                    System.out.println("New in House part added!");

                }  // New outSourced Part
                else if (outsourcedPart.isSelected()) {
                    // Get Company Name
                    String compName = partIDorCompany.getText();
                    // Add the new part
                    Inventory.addPart(new OutSourced(getID(), name, price, inv, min, max, compName));
                    System.out.println("New outsourced part added!");

                }
                // Leave to go to Main Page after saving
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1200, 600);
                stage.setTitle("Main Form");
                stage.setScene(scene);
                stage.show();
            }
        }
        catch (Exception e) {
            errorBox.setVisible(true);
            errorBox.setText("Error: Please check input values");
        }

    }



    /**Function is generating unique ID's for the new parts.
     *
     * @return the ID generated
     *
     * */

    public static int getID(){
        int newID = 1;

        for (int i = 0; i < Inventory.getAllParts().size(); i++) {
            newID++;

        }
        return newID;

    }


    /**
     *
     * Sets InHouse option.
     *
     * @param actionEvent Sets text
     * */
    public void onInHouse(ActionEvent actionEvent) {

        this.machineOrCompany.setText("Machine ID");
    }
    /**
     *
     * Sets OutSourced option.
     *
     * @param actionEvent Sets text
     * */
    public void onOutsourcedPart(ActionEvent actionEvent) {

        this.machineOrCompany.setText("Company Name");
    }

    /**
     * Initializes controller.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
