package domain;

import java.util.ArrayList;
import java.util.List;

public class Reader extends Person {
    private static long generalId = 1;
    private final long readerId;
    private List<Book> borrowedBooks = new ArrayList<>();

    public Reader(String name, String cpf, String phoneNumber) {
        super(name, cpf, phoneNumber);
        this.readerId = generalId++;
    }

    @Override
    public String toString() {
        return "| " + String.format("%02d", this.readerId) + " | " + this.getName() +
                " | CPF: " + this.getCpf() +
                " | Tel: " + this.getPhoneNumber();
    }

    public void setBorrowedBooks(Book book) {
        this.borrowedBooks.add(book);
    }

    public void removeBorrowedBooks(Book book) {
        if (!borrowedBooks.isEmpty()) {
            for (int i = 0; i < borrowedBooks.size(); i++) {
                if (borrowedBooks.get(i).equals(book)) {
                    borrowedBooks.remove(borrowedBooks.get(i));
                }
            }
        }
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public long getReaderId() {
        return readerId;
    }
}
