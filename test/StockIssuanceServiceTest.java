import java.awt.GraphicsEnvironment;
import java.util.List;
import javax.swing.SwingUtilities;

public class StockIssuanceServiceTest {
    public static void main(String[] args) throws Exception {
        issuesMaterialAndDeductsStock();
        preventsIssuingMoreThanAvailable();
        rejectsInvalidQuantity();
        rejectsUnknownCleaner();
        buildsIssuanceScreen();
        System.out.println("All stock issuance tests passed.");
    }

    private static void issuesMaterialAndDeductsStock() throws Exception {
        MaterialStock material = new MaterialStock(1, "Disinfectant", 10);
        CleanerRecord cleaner = new CleanerRecord(1, "Test Cleaner");
        StockIssuanceService service = createService(material, cleaner);

        StockIssuance issuance = service.issueMaterial(material, cleaner, 4);

        check(material.getAvailableQuantity() == 6,
                "Available stock should be reduced from 10 to 6.");
        check(issuance.getQuantity() == 4,
                "The issuance should store the issued quantity.");
        check(service.getIssuanceHistory().size() == 1,
                "A successful issuance should be added to history.");
    }

    private static void preventsIssuingMoreThanAvailable() throws Exception {
        MaterialStock material = new MaterialStock(1, "Floor Cleaner", 3);
        CleanerRecord cleaner = new CleanerRecord(1, "Test Cleaner");
        StockIssuanceService service = createService(material, cleaner);

        expectFailure(() -> service.issueMaterial(material, cleaner, 4),
                "Only 3 units");
        check(material.getAvailableQuantity() == 3,
                "Rejected issuance must not change the available stock.");
        check(service.getIssuanceHistory().isEmpty(),
                "Rejected issuance must not be added to history.");
    }

    private static void rejectsInvalidQuantity() throws Exception {
        MaterialStock material = new MaterialStock(1, "Refuse Bags", 20);
        CleanerRecord cleaner = new CleanerRecord(1, "Test Cleaner");
        StockIssuanceService service = createService(material, cleaner);

        expectFailure(() -> service.issueMaterial(material, cleaner, 0),
                "greater than zero");
    }

    private static void rejectsUnknownCleaner() throws Exception {
        MaterialStock material = new MaterialStock(1, "Refuse Bags", 20);
        CleanerRecord storedCleaner = new CleanerRecord(1, "Stored Cleaner");
        CleanerRecord unknownCleaner = new CleanerRecord(99, "Unknown Cleaner");
        StockIssuanceService service = createService(material, storedCleaner);

        expectFailure(() -> service.issueMaterial(material, unknownCleaner, 1),
                "no longer exists");
    }

    private static StockIssuanceService createService(MaterialStock material,
            CleanerRecord cleaner) {
        IssuanceRepository repository = new InMemoryIssuanceRepository(
                List.of(material), List.of(cleaner));
        return new StockIssuanceService(repository);
    }

    private static void buildsIssuanceScreen() throws Exception {
        if (GraphicsEnvironment.isHeadless()) {
            return;
        }

        SwingUtilities.invokeAndWait(() -> {
            StockIssuanceService service = new StockIssuanceService(
                    new InMemoryIssuanceRepository());
            StockIssuanceFrame frame = new StockIssuanceFrame(service);
            frame.dispose();
        });
    }

    private static void expectFailure(TestAction action, String expectedMessage)
            throws Exception {
        try {
            action.run();
            throw new AssertionError("Expected the issuance to be rejected.");
        } catch (IssuanceException exception) {
            check(exception.getMessage().contains(expectedMessage),
                    "Unexpected validation message: " + exception.getMessage());
        }
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    @FunctionalInterface
    private interface TestAction {
        void run() throws Exception;
    }
}
