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
    return String.format("Cleaner Record {\n" +
                    "\t ID: %d,\n" +
                    "\t Name: %s\n" +
                    "}", this.id, this.name);
    }
}
