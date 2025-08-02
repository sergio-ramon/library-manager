package dao;

import domain.Author;
import domain.Book;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class BookDAO {
    private Set<Book> bookList = new HashSet<>();

    public String saveBook(Book book) {
        if (!this.bookList.add(book)) {
            return "Livro já cadastrado! Verifique o título e tente novamente";
        }
        return "Livro cadastrado com sucesso!";
    }

    public void editBook(Book book, String title, int gender, int pubYear, Author author) {
        book.setTitle(title);
        book.setGender(gender);
        book.setPublicationYear(pubYear);
        book.setAuthor(author);
    }

    public List<Book> listAvailableBooks() {
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : this.bookList) {
            if (!book.isLoanedBook()) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    public List<Book> listLoanedBooks() {
        List<Book> loanedBooks = new ArrayList<>();
        for (Book book : this.bookList) {
            if (book.isLoanedBook()) {
                loanedBooks.add(book);
            }
        }
        return loanedBooks;
    }

    public Set<Book> listAllBooks() {
        return new TreeSet<>(bookList);
    }

    public void deleteBook(Book book) {
        bookList.removeIf(book1 -> book1.equals(book));
    }

    public void loanAvailableBook(Book book) {
        for (Book b : this.bookList) {
            if (b.equals(book)) {
                b.setLoanedBook(true);
            }
        }
    }

    public void returnBorrowedBook(Book book) {
        for (Book b : this.bookList) {
            if (b.equals(book)) {
                b.setLoanedBook(false);
            }
        }
    }

    public Book findBookById(long id) {
        if (id < 1230000L || id > 1239999L) {
            return null;
        }
        for (Book book : bookList) {
            if (book.getBookId() == id) {
                return book;
            }
        }
        return null;
    }
}
