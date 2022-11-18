package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
        * Models an inventory of Parts and Products.
        *
        * The class provides persistent data for the application.
        *
        */
public class Inventory {

    /**
     * A list of all parts in inventory.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * A list of all products in inventory.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /**
     * Adds a part to the inventory.
     *
     * @param newPart The part object to add.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    /**
     * Adds a product to the inventory.
     *
     * @param product The product object to add.
     */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /**
     * Searches the list of parts by ID.
     *
     * @param partID The part ID.
     * @return The part object if found, null if not found.
     */
    public static Part lookupPart(int partID) {

        for (int i = 0; i < allParts.size(); i++) {
            Part p = allParts.get(i);

            if (p.getId() == partID){
                return p;
            }
        }

        return null;
    }

    /**
     * Searches the list of products by ID.
     *
     * @param productID The productt ID.
     * @return The product object if found, null if not found.
     */
    public static Product lookupProduct(int productID) {
        for (int i = 0; i < allProducts.size(); i++) {
            Product p = allProducts.get(i);

            if (p.getId() == productID){
                return p;
            }
        }
        return null;
    }


    /**
     * Searches the list of parts by name.
     *
     * @param partName The part name.
     * @return A list of parts found.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> namedPart = FXCollections.observableArrayList();

        for(Part p : allParts){
            if(p.getName().contains(partName)){
                namedPart.add(p);
            }
        }

        return namedPart;
    }


    /**
     * Searches the list of products by name.
     *
     * @param productName The product name.
     * @return A list of products found.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> namedProduct = FXCollections.observableArrayList();

        for(Product p : allProducts) {
            if(p.getName().contains(productName)){
                namedProduct.add(p);
            }
        }
        return namedProduct;
    }

    /**
     * Replaces a part in the list of parts.
     *
     * @param index Index of the part to be replaced.
     * @param selectedPart The part used for replacement.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index,selectedPart);
    }

    /**
     * Replaces a product in the list of products.
     *
     * @param index Index of the product to be replaced.
     * @param selectedProduct The product used for replacement.
     */
    public static void updateProduct(int index, Product selectedProduct)
    {
        allProducts.set(index,selectedProduct);
    }

    /**
     * Removes a part from the list of parts.
     *
     * @param selectedPart The part to be removed.
     * @return A boolean indicating status of part removal.
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)){
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes a product from the list of parts.
     *
     * @param selectedProduct The product to be removed.
     * @return A boolean indicating status of product removal.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)){
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets a list of all parts in inventory.
     *
     * @return A list of part objects.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Gets a list of all products in inventory.
     *
     * @return A list of product objects.
     */

   public static ObservableList<Product> getAllProducts(){
        return allProducts;

   }

}


