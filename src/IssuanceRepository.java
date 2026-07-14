import java.util.List;

public interface IssuanceRepository {
    List<MaterialStock> getMaterials();

    List<CleanerRecord> getCleaners();

    List<StockIssuance> getIssuanceHistory();

    StockIssuance issueStock(int materialId, int cleanerId, int quantity)
            throws IssuanceException;
}
