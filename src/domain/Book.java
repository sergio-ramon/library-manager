package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Book implements Comparable<Book> {
    private static long generalId = 1230000L;

    private final long bookId;
    private String title;
    private BookGenders gender;
    private int publicationYear;
    private Author author;
    private boolean loanedBook = false;

    public Book(String title, int gender, int publicationYear, Author author) {
        generalId += 10;

        char[] codeToCharArray = String.valueOf(generalId).toCharArray();
        this.bookId = generalId + sumListNumbers(convertArrayToInt(codeToCharArray));

        this.title = title;
        this.gender = BookGenders.bookGendersByNumber(gender);
        this.publicationYear = publicationYear;
        this.author = author;
    }

    private static List<Integer> convertArrayToInt(char[] codeToCharArray) {
        List<Integer> codeToIntArray = new ArrayList<>();

        int count = 7;
        for (char c : codeToCharArray) {
            int e = Integer.parseInt(String.valueOf(c));

            codeToIntArray.add(e * count--);
        }

        return codeToIntArray;
    }

    private static long sumListNumbers(List<Integer> list) {
        int sum = 0;

        for (int n : list) {
            sum += n;
        }

        sum = sum % 10;
        return sum == 0 ? 0 : (10 - sum);
    }

    @Override
    public int compareTo(Book book) {
        return Long.compare(bookId, book.getBookId());
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Book book = (Book) object;
        return Objects.equals(title, book.title);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(title);
    }

    @Override
    public String toString() {
        return "| " + String.format("%05d", this.bookId) + "    | " + this.title +
        "\n| Gênero     | " + this.gender.getNameValue() +
        "\n| Autor      | " + this.author.getName() +
        "\n| Publicação | " + this.publicationYear;
    }

    public long getBookId() {
        return bookId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGender(int gender) {
        this.gender = BookGenders.bookGendersByNumber(gender);
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public boolean isLoanedBook() {
        return loanedBook;
    }

    public void setLoanedBook(boolean loanedBook) {
        this.loanedBook = loanedBook;
    }

    public String getTitle() {
        return title;
    }
}
