import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class InMemoryIssuanceRepository implements IssuanceRepository {
    private final Map<Integer, MaterialStock> materials = new LinkedHashMap<>();
    private final Map<Integer, CleanerRecord> cleaners = new LinkedHashMap<>();
    private final List<StockIssuance> issuanceHistory = new ArrayList<>();
    private int nextIssuanceId = 1;

    public InMemoryIssuanceRepository() {
        materials.put(1, new MaterialStock(1, "Floor Cleaner", 25));
        materials.put(2, new MaterialStock(2, "Disinfectant", 18));
        materials.put(3, new MaterialStock(3, "Refuse Bags", 40));

        cleaners.put(1, new CleanerRecord(1, "Thandi Mokoena"));
        cleaners.put(2, new CleanerRecord(2, "Sipho Dlamini"));
        cleaners.put(3, new CleanerRecord(3, "Lerato Molefe"));
    }

    public InMemoryIssuanceRepository(List<MaterialStock> materialList,
            List<CleanerRecord> cleanerList) {
        for (MaterialStock material : materialList) {
            materials.put(material.getId(), material);
        }
        for (CleanerRecord cleaner : cleanerList) {
            cleaners.put(cleaner.getId(), cleaner);
        }
    }

    @Override
    public synchronized List<MaterialStock> getMaterials() {
        return new ArrayList<>(materials.values());
    }

    @Override
    public synchronized List<CleanerRecord> getCleaners() {
        return new ArrayList<>(cleaners.values());
    }

    @Override
    public synchronized List<StockIssuance> getIssuanceHistory() {
        return new ArrayList<>(issuanceHistory);
    }

    @Override
    public synchronized StockIssuance issueStock(int materialId, int cleanerId,
            int quantity) throws IssuanceException {
        MaterialStock material = materials.get(materialId);
        CleanerRecord cleaner = cleaners.get(cleanerId);

        if (quantity <= 0) {
            throw new IssuanceException("Quantity must be greater than zero.");
        }
        if (material == null) {
            throw new IssuanceException("The selected material no longer exists.");
        }
        if (cleaner == null) {
            throw new IssuanceException("The selected cleaner no longer exists.");
        }
        if (quantity > material.getAvailableQuantity()) {
            throw new IssuanceException("Only " + material.getAvailableQuantity()
                    + " units of " + material.getName() + " are available.");
        }

        StockIssuance issuance = new StockIssuance(
                nextIssuanceId++,
                material.getId(),
                material.getName(),
                cleaner.getId(),
                cleaner.getName(),
                quantity,
                LocalDateTime.now()
        );

        material.deduct(quantity);
        issuanceHistory.add(issuance);
        return issuance;
    }
}
