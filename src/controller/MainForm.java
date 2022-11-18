package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;



import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This is the main page of the program and sets out logic that controls the data on the main page*/
public class MainForm implements Initializable {
    /** Button Definitions on Main Page. **/
    public Button addPart; public Button modifyPart; public Button deletePart;
    public Button productAdd; public Button productModify; public Button productDelete;
    public Button mainExit;
    /** End of Button Definitions **/


    /** Parts Table definitions **/
    public TableView partsTable; public TableColumn partID; public TableColumn partName;
    public TableColumn inventoryLvlPart; public TableColumn priceCostPart; public TextField partSearch;
    /** End of Parts Table Definitions **/

    /** Product Table Definitions **/
    public TableView productTable; public TableColumn productID; public TableColumn productName;
    public TableColumn inventoryLvlProduct; public TableColumn priceCostProduct;
    public TextField productSearch;
    public Label mainError;


    /** End of Product Table Definitions **/


    /** Sets the part and product tables.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        partsTable.setItems(Inventory.getAllParts());
        productTable.setItems(Inventory.getAllProducts());

        //Setting Part DATA
        partID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLvlPart.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCostPart.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Setting Product DATA
        productID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLvlProduct.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCostProduct.setCellValueFactory(new PropertyValueFactory<>("price"));

    }


    /**
     * Switches to add part screen.
     *
     * @param actionEvent Add button action.
     * @throws IOException from FXMLLoader
     *
     */
    public void onAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Add Part.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,700,600);
        stage.setTitle("Add Part");
        stage.setScene(scene);
        stage.show();


    }

    /**
     * Loads to the modify part screen.
     *
     * @param actionEvent modify button action.
     * @throws IOException from FXMLLoader
     *
     */
    public void onModifyPart(ActionEvent actionEvent) throws IOException {

            try {
                Part toModify =  (Part) partsTable.getSelectionModel().getSelectedItem();
                if (toModify == null) {
                    mainError.setText("Error: Please Select a Part to Modify!");

                }else {
                    mainError.setText("");

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Modify Part.fxml"));
                    Parent root = loader.load();

                    ModifyPart mp = loader.getController();
                    mp.setPartInfo(toModify);

                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    stage.setTitle("Modify Part");
                    stage.setScene(new Scene(root, 700, 600));
                    stage.show();

                }


            } catch (Exception e ) {
                System.out.println("Error");
            }

    }


    /**
     * Deletes selected part.
     *
     * Prompts error message if no part is selected.
     *
     * @param actionEvent delete button action.
     *
     */
    public void onDeletePart(ActionEvent actionEvent) {

        if (partsTable.getSelectionModel().isEmpty()){
            mainError.setText("Error: Please select a Part to delete!");

        }else {
            mainError.setText("");

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Part");
            alert.setHeaderText("Are you sure you want to delete this part?");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();


            if (result.get() == ButtonType.OK){
                // ... user chose OK
                int partToDelete = partsTable.getSelectionModel().getSelectedIndex();
                partsTable.getItems().remove(partToDelete);

            } else {
                // ... user chose CANCEL or closed the dialog
            }
        }
    }



    /**
     * Loads the add product screen.
     *
     * @param actionEvent Add button action.
     * @throws IOException from FXMLLoader
     */
    public void onProductAdd(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/Add Product.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,1000,700);
        stage.setTitle("Add Product");
        stage.setScene(scene);
        stage.show();


    }
    /**
     * Loads the modify product screen.
     *
     * Prompts error message if no product is selected.
     * @param actionEvent modify button action.
     * @throws IOException from FXMLLoader
     */
    public void onProductModify(ActionEvent actionEvent) throws IOException{


        try {
            Product toModify =  (Product) productTable.getSelectionModel().getSelectedItem();
            if (toModify == null) {
                mainError.setText("Error: Please Select a Product to Modify!");

            }else {
                mainError.setText("");

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Modify Product.fxml"));
                Parent root = loader.load();

                ModifyProduct  mp = loader.getController();
                mp.setProductInfo(toModify);

                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setTitle("Modify Product");
                stage.setScene(new Scene(root, 1000, 700));
                stage.show();

            }


        } catch (Exception e ) {
            System.out.println("Error");
        }
    }


    /**
     * Deletes a selected product.
     *
     * Prompts error if no product is selected.
     * @param actionEvent button action
     *
     *  */
    public void onProductDelete(ActionEvent actionEvent) {

        if (productTable.getSelectionModel().isEmpty()){
            mainError.setText("Error: Please select a Product to delete!");

        }else {
            mainError.setText("");

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Product");
            alert.setHeaderText("Are you sure you want to delete this product?");
            alert.setContentText("Are you ok with this?");

            Optional<ButtonType> result = alert.showAndWait();


            if (result.get() == ButtonType.OK){
                // ... user chose OK
                Product selected = (Product) productTable.getSelectionModel().getSelectedItem();

                ObservableList<Part> associatedParts = selected.getAllAssociatedParts();
                if(associatedParts.size() != 0) {
                    Alert nextAlert = new Alert(Alert.AlertType.ERROR);
                    nextAlert.setTitle("Error ");
                    nextAlert.setHeaderText("The product you are trying to delete has parts associated with it!");
                    nextAlert.setContentText("Please modify the product first and remove all associated parts before deleting!");

                    nextAlert.showAndWait();
                } else {

                    int productToDelete = productTable.getSelectionModel().getSelectedIndex();
                    productTable.getItems().remove(productToDelete);
                }


            } else {
                // ... user chose CANCEL or closed the dialog
            }

        }
    }





    /**
     * Searches through the part table to find parts.
     *
     * @param actionEvent search action
     * */
    public void partSearch(ActionEvent actionEvent) {
        String q = partSearch.getText();
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
            mainError.setText("Error: No Part Found!");
        }else {
            mainError.setText("");
            partsTable.setItems(foundParts);
        }

    }

    /**
     * Searches through the product table to find products.
     *
     * @param actionEvent search action
     * */
    public void productSearch(ActionEvent actionEvent) {
        String q = productSearch.getText();
        ObservableList<Product> foundProducts = Inventory.lookupProduct(q);

        if (foundProducts.size() == 0) {
            try {
                int productID = Integer.parseInt(q);
                Product p = Inventory.lookupProduct(productID);
                if (p != null) {
                    foundProducts.add(p);
                }
            } catch (NumberFormatException e) {
                //ignore
            }
        }
        else {
            foundProducts = Inventory.lookupProduct(q);
        }

        if (foundProducts.isEmpty()){
            mainError.setText("Error: No Part Found!");
        }else {
            mainError.setText("");
            productTable.setItems(foundProducts);
        }

    }

    /**
     * Exits the program.
     *
     * @param actionEvent Exit button action.
     */
    public void onMainExit(ActionEvent actionEvent) {

        System.exit(0);

    }


}


