public class CleanerRecord {

    private final int id;
    private final String name;

    public CleanerRecord(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format(
                "Cleaner Record {\n" +
                "\tID: %d,\n" +
                "\tName: %s\n" +
                "}",
                this.id,
                this.name
        );
    }
}