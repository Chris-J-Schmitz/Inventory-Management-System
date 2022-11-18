/**
 *
 *  Author Chris Schmitz
 *
 *  Inventory Management Application
 *
 *  Allows you to Add / Delete / Modify Parts (Both In house and Outsourced)
 *  Allows you to Add / Delete / Modify Products ( Products are composed of the available parts)
 *
 *
 *
 *
 *
 */
package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Product;
import model.OutSourced;

import java.io.IOException;


/** This Class is the main class of the program.*/
public class Main extends Application {

    /**
     * The start method creates the FXML stage and loads the initial scene.
     *
     * @param mainStage the main stage that loads.
     *
     * @throws IOException from FXMLLoader
     *
     */
    @Override
    public void start(Stage mainStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        mainStage.setTitle("Main Form");
        mainStage.setScene(new Scene(root, 1200, 600));
        mainStage.show();
    }

    /**
     * The main method is the starting point of the application.
     *
     * The main method creates sample data and launches the application.
     *
     * @param args initial arguments of project
     */
    public static void main(String[] args){
        // Starting Parts Data
        Inventory.addPart(new InHouse(1, "CPU", 125.00, 2, 1, 100, 123));
        Inventory.addPart(new InHouse(2, "Motherboard", 110.00, 2, 1, 100, 223));
        Inventory.addPart(new OutSourced(3, "RAM", 84.00, 1, 1, 100, "G-Skill"));
        Inventory.addPart(new OutSourced(4, "GPU", 624.00, 5, 1, 100, "Nvidia"));


        // Starting Product Data
        Inventory.addProduct(new Product(100, "Budget PC", 500.00,15,1,50));
        Inventory.addProduct(new Product(200, "High-End PC", 2750.00,20,1,25));


        launch(args);
    }
}
