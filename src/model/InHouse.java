package model;

/**
 * Models an in-house part.

 *
 */
public class InHouse extends Part{
    private int machineID;

    /**
     * The getter for the machineId
     *
     * @return machineID of the part
     */

    public int getMachineID() {
        return machineID;
    }

    /**
     * The setter for the machineId
     *
     * @param machineID the machineId of the part
     */

    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }

/**
 * Constructor for a new instance of an InHouse object.
 *
 * @param id the ID for the part
 * @param name the name of the part
 * @param price the price of the part
 * @param stock the inventory level of the part
 * @param min the minimum level for the part
 * @param max the maximum level for the part
 * @param machineID the machine ID for the part
 * */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID) {
        super(id, name, price, stock, min, max);
        this.machineID = machineID;
    }
}
