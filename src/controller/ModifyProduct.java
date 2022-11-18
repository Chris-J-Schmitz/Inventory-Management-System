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

    /**Creates logic for the "Modify Product" page*/
public class ModifyProduct implements Initializable {
    /** Text Fields */
    public TextField ProductID; public TextField ProductName; public TextField ProductInv;
    public TextField ProductPrice; public TextField ProductMax; public TextField ProductMin;
    public TextField modifyProductSearch;
    /** End of Text Fields*/

    /** Buttons */
    public Button removePartButton; public Button modifyProductSave; public Button modifyProductCancel;
    public Button Add;
    public Label errorBox;

    /** End of Buttons*/

    /** Tables*/
    public TableView partsTable;
    public TableColumn partID; public TableColumn partName; public TableColumn partInv; public TableColumn partPrice;
    public TableView newProductTable;
    public TableColumn newPartID; public TableColumn newPartName; public TableColumn newPartInv; public TableColumn newPartPrice;
    /** */

    public Product theProduct = null;
    private int index;

    private ObservableList<Part> newProduct = FXCollections.observableArrayList();

    /** Searches the available Parts.
     *
     * @param actionEvent  search field.
     *
     * */
    public void onSearch(ActionEvent actionEvent) {
        String q = modifyProductSearch.getText();
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

    /** Adds the selected part to the product.
     *
     * @param actionEvent the add button action
     *
     * */
    public void onAdd(ActionEvent actionEvent) {
        Part selectedPart = (Part) partsTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            newProduct.add(selectedPart);
        }
    }

    /** Removes selected part from the product.
     *
     * @param actionEvent remove part button action.
     *
     * */
    public void onRemovePart(ActionEvent actionEvent) {
        Part selectedPart = (Part) newProductTable.getSelectionModel().getSelectedItem();

        if (selectedPart != null) {
            newProduct.remove(selectedPart);
        }
    }

    /**Set info from selected product on main screen.
     *
     * @param selectedProduct the product selected from the main screen.
     * */
    public void setProductInfo(Product selectedProduct) {
        //Set ID
        this.theProduct = selectedProduct;
        ProductID.setText(Integer.toString(theProduct.getId()));

        index = Inventory.getAllProducts().indexOf(selectedProduct);
        //Set the rest of the info
        ProductName.setText(selectedProduct.getName());
        ProductPrice.setText(Double.toString(selectedProduct.getPrice()));
        ProductInv.setText(Integer.toString(selectedProduct.getStock()));
        ProductMax.setText(Integer.toString(selectedProduct.getMax()));
        ProductMin.setText(Integer.toString(selectedProduct.getMin()));

        //Set table info
        newProduct.addAll(selectedProduct.getAllAssociatedParts());


    }

    /**Save the input from the user to create the new Product.
     *
     * @param actionEvent save button action
     * @throws IOException from FXMLLoader
     *
     * */

    public void onSaveProduct(ActionEvent actionEvent) throws IOException{
        // Get Inv Max and Min
        int inv = parseInt(ProductInv.getText());
        int max = parseInt(ProductMax.getText());
        int min = parseInt(ProductMin.getText());

        //Check values
        if (max < min) {
            errorBox.setText("Error: Check Min and Max values");
        } else if (inv < min || inv > max) {

            errorBox.setText("Error: Check Min, Max, and Inv values");
        } else {
            try {
                //Gather ID Name and Price
                int ID = Integer.parseInt(ProductID.getText());
                String name = ProductName.getText();
                double price = Double.parseDouble(ProductPrice.getText());

                //Create a Temp Product
                Product temp = new Product(ID, name, price,inv, min, max);

                //Gather Parts for Product
                temp.getAllAssociatedParts().clear();
                for (Part part : newProduct) {
                    temp.addAssociatedPart(part);
                }
                Inventory.updateProduct(index,temp);



                // Leave to go to Main Page after saving
                Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1200, 600);
                stage.setTitle("Main Form");
                stage.setScene(scene);
                stage.show();
            }
            catch (Exception e ) {
                errorBox.setText("Error : Please check values ");
            }
        }

    }

    /** Change back to the main screen and "Cancel" out of this view.
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


    /**
     * Initializes controller and sets in-house radio button to true.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTable.setItems(Inventory.getAllParts());
        //Setting Part DATA
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        newProductTable.setItems(newProduct);
        newPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        newPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        newPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        newPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));


    }


}
