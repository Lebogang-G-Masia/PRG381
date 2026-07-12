public class MaterialStock {
    private final int id;
    private final String name;
    private int availableQuantity;

    public MaterialStock(int id, String name, int availableQuantity) {
        this.id = id;
        this.name = name;
        this.availableQuantity = availableQuantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void deduct(int quantity) {
        availableQuantity -= quantity;
    }

    @Override
    public String toString() {
        return name;
    }
}
