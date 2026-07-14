import java.util.List;

public class StockIssuanceService {
    private final IssuanceRepository repository;

    public StockIssuanceService(IssuanceRepository repository) {
        this.repository = repository;
    }

    public List<MaterialStock> getMaterials() {
        return repository.getMaterials();
    }

    public List<CleanerRecord> getCleaners() {
        return repository.getCleaners();
    }

    public List<StockIssuance> getIssuanceHistory() {
        return repository.getIssuanceHistory();
    }

    public StockIssuance issueMaterial(MaterialStock material,
            CleanerRecord cleaner, int quantity) throws IssuanceException {
        if (material == null) {
            throw new IssuanceException("Please select a material.");
        }
        if (cleaner == null) {
            throw new IssuanceException("Please select a cleaner.");
        }
        if (quantity <= 0) {
            throw new IssuanceException("Quantity must be greater than zero.");
        }

        return repository.issueStock(material.getId(), cleaner.getId(), quantity);
    }
}
