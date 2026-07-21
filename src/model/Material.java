package model;

public class Material {

    private int materialId;
    private String materialName;
    private String description;
    private int quantity;
    private int reorderLevel;
    private String unit;
    private int supplierId;

    public Material() {
    }

    public Material(int materialId, String materialName, String description,
                    int quantity, int reorderLevel,
                    String unit, int supplierId) {

        this.materialId = materialId;
        this.materialName = materialName;
        this.description = description;
        this.quantity = quantity;
        this.reorderLevel = reorderLevel;
        this.unit = unit;
        this.supplierId = supplierId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getReorderLevel() {
        return reorderLevel;
    }

    public void setReorderLevel(int reorderLevel) {
        this.reorderLevel = reorderLevel;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }
}