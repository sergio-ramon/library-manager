package domain;

public class Author extends Person {
    private static long generalId = 1;
    private final long authorId;

    public Author(String name) {
        super(name);
        this.authorId = generalId++;
    }

    @Override
    public String toString() {
        return "| " + String.format("%02d", this.authorId) + " | " + this.getName();
    }

    public long getAuthorId() {
        return authorId;
    }
}
