package brandonlagasse.c482;

public class OutSourced extends Part{
    private String companyName;

    /**
     * This is the constructor for OutSourced parts
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param companyName
     */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Getter for CompanyName
     * @return companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Setter for CompanyName
     * @param companyName new companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
