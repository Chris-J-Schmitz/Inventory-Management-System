package model;

/**
 * Models an outsourced part.
 *
 *
 */
public class OutSourced extends Part{

    private String companyName;

    /**
     * The getter for the companyName
     *
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * The setter for the companyName
     *
     * @param companyName the name of the company
     */

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Constructor for a new instance of an Outsourced part.
     *
     * @param id the ID for the part
     * @param name the name of the part
     * @param price the price of the part
     * @param stock the inventory level of the part
     * @param min the minimum level for the part
     * @param max the maximum level for the part
     * @param companyName the company name for the part
     */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
}
