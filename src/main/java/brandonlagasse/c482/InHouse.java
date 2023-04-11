package brandonlagasse.c482;

public class InHouse extends Part{


    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Getter for machineId
     * @return machine
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Setter for MachineId
     * @param machineId sets machineId
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
