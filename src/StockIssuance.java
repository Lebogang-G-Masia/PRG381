import java.time.LocalDateTime;

public class StockIssuance {
    private final int id;
    private final int materialId;
    private final String materialName;
    private final int cleanerId;
    private final String cleanerName;
    private final int quantity;
    private final LocalDateTime issuedAt;

    public StockIssuance(int id, int materialId, String materialName,
            int cleanerId, String cleanerName, int quantity,
            LocalDateTime issuedAt) {
        this.id = id;
        this.materialId = materialId;
        this.materialName = materialName;
        this.cleanerId = cleanerId;
        this.cleanerName = cleanerName;
        this.quantity = quantity;
        this.issuedAt = issuedAt;
    }

    public int getId() {
        return id;
    }

    public int getMaterialId() {
        return materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public int getCleanerId() {
        return cleanerId;
    }

    public String getCleanerName() {
        return cleanerName;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }
}
