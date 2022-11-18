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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Integer.parseInt;

/** This controller class controls the add product screen.
 *
 * */
public class AddProduct implements Initializable {

    /** Text Fields */
    public TextField addProductID; public TextField addProductName; public TextField addProductInv;
    public TextField addProductPrice; public TextField addProductMax; public TextField addProductMin;
    public TextField addProductSearch;
    /** End of Text Fields*/

    /** Buttons */
    public Button removePartButton; public Button addProductSave; public Button addProductCancel;
    public Button Add;
    /** End of Buttons*/


    /** Table and Error Definitions */
    // Current Parts
    public TableView partsTable;
    public TableColumn partID; public TableColumn partName; public TableColumn inventoryLvlPart; public TableColumn priceCostPart;


    // New part Table
    public TableView newProductTable;
    public TableColumn newPartID; public TableColumn newPartName; public TableColumn newPartInv; public TableColumn newPartPrice;
    public Label errorBox;

    private ObservableList<Part> newProduct = FXCollections.observableArrayList();

    /** End of Table and Error */



    /** This function will remove a selected part from the product being created.
     *
     * @param actionEvent  remove part button action
     * */
    public void onRemovePart(ActionEvent actionEvent) {
        Part selectedPart = (Part) newProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            newProduct.remove(selectedPart);
        }

    }
    /** Save Product function saves all the information entered by the user for to create the new product.
     *
     * @param actionEvent  save part button action
     * @throws IOException from FXMLLoader
     *
     * */
    public void onSaveProduct(ActionEvent actionEvent) throws IOException {

            try {
                /** Part Variable Definitions */
                String name = addProductName.getText();
                int inv = parseInt(addProductInv.getText());
                double price = Double.parseDouble(addProductPrice.getText());
                int max = parseInt(addProductMax.getText());
                int min = parseInt(addProductMin.getText());
                /** Part Variable Definitions */

                if (max < min) {
                    errorBox.setText("Error: Check Min and Max values");
                } else if (inv < min || inv > max) {
                    errorBox.setText("Error: Check Min, Max, and Inv values");
                } else {
                    Product temp = new Product(getID(),name,price,inv,min, max);


                    for (Part part : newProduct) {
                        temp.addAssociatedPart(part);
                    }
                    Inventory.addProduct(temp);


                    // Leave to go to Main Page after saving
                    Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root, 1200, 600);
                    stage.setTitle("Main Form");
                    stage.setScene(scene);
                    stage.show();


                }
            }
            catch (Exception e ) {
                errorBox.setText("Error: Please check input values");
            }
    }

    /**Function is generating unique ID's for the new products.
     *
     * @return the generated ID
     *
     *
     * */
    private int getID(){
        int newID = 100;
        for (int i = 0; i < Inventory.getAllProducts().size();i++){
            if (Inventory.getAllProducts().get(i).getId() == newID) {
                newID = newID + 100;
            }
        }
        return newID;
    }


    /**Function adds a selected part to the product.
     *
     * @param actionEvent add button
     * */
    public void onAdd(ActionEvent actionEvent) {
        Part selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            newProduct.add(selectedPart);
        }
    }

    /** Searches through the available parts to add to the product being created.
     *
     * @param actionEvent search field
     *
     * */
    public void onProductSearch(ActionEvent actionEvent) {
        String q = addProductSearch.getText();
        ObservableList<Part> foundParts = Inventory.lookupPart(q);

        if (foundParts.size() == 0) {
            try {
                int partID = Integer.parseInt(q);
                Part p = Inventory.lookupPart(partID);
                if (p != null) {
                    foundParts.add(p);
                }
            } catch (NumberFormatException e) {
                //ignore
            }
        }
        else {
            foundParts = Inventory.lookupPart(q);
        }
        if (foundParts.isEmpty()){
            errorBox.setText("Error: No Part Found!");
        }
        else {
            partsTable.setItems(foundParts);
            errorBox.setText("");
        }

    }

    /** Changes back to the main screen and "Cancel" out of this view.
     *
     *
     * @param actionEvent cancel button action
     * @throws IOException from FXMLLoader
     * */
    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,1200,600);
        stage.setTitle("Main Form");
        stage.setScene(scene);
        stage.show();
    }

    /** Sets the table data for the new product being created.
     *
     *
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     *
     * */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTable.setItems(Inventory.getAllParts());
        //Setting Part DATA
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLvlPart.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCostPart.setCellValueFactory(new PropertyValueFactory<>("price"));


        newProductTable.setItems(newProduct);
        newPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        newPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        newPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        newPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
