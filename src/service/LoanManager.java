package service;

import dao.BookDAO;
import domain.Reader;
import domain.Book;
import exceptions.InvalidInputTypeException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LoanManager {
    static Scanner input = new Scanner(System.in);
    BookManager bookManager;
    ReaderManager readerManager;

    public LoanManager(BookManager bookManager, ReaderManager readerManager) {
        this.bookManager = bookManager;
        this.readerManager = readerManager;
    }

    public void startBookLoan(BookDAO bookDAO, List<Reader> readers) {
        if (!bookDAO.listAvailableBooks().isEmpty()) {
            if (!readers.isEmpty()) {
                int bookId = getBookInput(new ArrayList<>(bookDAO.listAllBooks()), readers);
                if (validateBookId(bookId) || checkAvailableBooks(bookDAO, bookId)) return;
                Book book = bookDAO.findBookById(bookId);

                int readerNumber = getReaderInput(readers);
                if (validateReader(readers, readerNumber)) return;
                Reader reader = readers.get(readerNumber - 1);

                reader.setBorrowedBooks(book);
                bookDAO.loanAvailableBook(book);
                System.out.println("Empréstimo realizado com sucesso!");
            } else {
                System.out.println("Não há leitores cadastrados!");
            }
        } else {
            System.out.println("Não há livros disponíveis. Considere cadastrar um novo livro!");
        }
    }

    public void startBookReturn(BookDAO bookDAO, List<Reader> readers) {
        if (!bookDAO.listLoanedBooks().isEmpty()) {
            if (!readers.isEmpty()) {
                int readerNumber = getReaderInput(readers);
                if (validateReader(readers, readerNumber) || validateReaderBorrowedBooks(readers, readerNumber)) return;
                Reader reader = readers.get(readerNumber - 1);

                List<Book> books = reader.getBorrowedBooks();
                int bookId = getBookInput(books, readers);
                if (validateBookId(bookId)) return;
                Book book = bookDAO.findBookById(bookId);

                returnBook(reader, book);
                bookDAO.returnBorrowedBook(book);
                System.out.println("Devolução realizada com sucesso!");
            } else {
                System.out.println("Não há leitores cadastrados!");
            }
        } else {
            System.out.println("Não há livros emprestados!");
        }
    }

    private int getBookInput(List<Book> books, List<Reader> readers) {
        bookManager.listBooks(books, readers);
        System.out.print("Digite o ID do livro: ");
        int bookId = -1;
        try {
            return bookId = Validation.validateIntInput(input.next());
        } catch (InvalidInputTypeException e) {
            System.out.println(e.getMessage());;
        } finally {
            input.nextLine(); // Resolve momentaneamente lixo residual
        }
        return bookId;
    }

    private int getReaderInput(List<Reader> readers) {
        readerManager.listReaders(readers);
        System.out.print("Digite o número do leitor: ");
        int readerNumber = -1;
        try {
            readerNumber = Validation.validateIntInput(input.next());
        } catch (InvalidInputTypeException e) {
            System.out.println(e.getMessage());
        } finally {
            input.nextLine(); // Resolve momentaneamente lixo residual
        }
        return readerNumber;
    }

    private boolean validateReader(List<Reader> readers, int readerNumber) {
        if (readerNumber > readers.size() || readerNumber <= 0) {
            System.out.println("Número de leitor inválido!");
            return true;
        }
        return false;
    }

    private boolean validateReaderBorrowedBooks(List<Reader> readers, int readerNumber) {
        List<Book> borrowedBooks = readers.get(readerNumber - 1).getBorrowedBooks();
        if (borrowedBooks == null || borrowedBooks.isEmpty()) {
            System.out.println("Não há livros com o leitor selecionado!");
            return true;
        }
        return false;
    }

    private boolean validateBookId(int bookId) {
        if (bookId < 1230000L || bookId > 1239999L) {
            System.out.println("ID de livro inválido!");
            return true;
        }
        return false;
    }

    private boolean checkAvailableBooks(BookDAO bookDAO, int bookId) {
        if (bookDAO.findBookById(bookId).isLoanedBook()) {
            System.out.println("Livro já emprestado!");
            return true;
        }
        return false;
    }

    private void returnBook(Reader reader, Book book) {
        if (reader.getBorrowedBooks() != null) {
            reader.removeBorrowedBooks(book);
        }
    }
}
