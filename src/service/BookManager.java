package service;

import dao.BookDAO;
import domain.Author;
import domain.Book;
import domain.Reader;
import exceptions.InvalidBookGenderException;
import exceptions.InvalidBookTitleException;
import exceptions.PublicationDateFormatInvalidException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class BookManager {
    static Scanner input = new Scanner(System.in);

    public BookManager() {
    }

    public Book generateNewBook(List<Author> authors)
            throws InvalidBookTitleException, InvalidBookGenderException, PublicationDateFormatInvalidException {
        System.out.print("Digite o título do livro: ");
        String title = input.nextLine();
        Validation.validateArgument(title);
        System.out.println("+---+-------------------+---+--------------------+---+-------------+");
        System.out.println("| 1 | Fantasia          | 2 | Ficção             | 3 | Não-Ficção  |");
        System.out.println("| 4 | Romance           | 5 | Ficção-Científica  | 6 | Mistério    |");
        System.out.println("| 7 | Suspense/Terror   | 8 | Poesia             |   |             |");
        System.out.println("+---+-------------------+---+--------------------+---+-------------+");
        System.out.print("Selecione o gênero do livro: ");
        int gender = HandleInputException.handleIntInputException(input.next());
        input.nextLine(); // Resolve momentaneamente lixo residual
        validateGender(gender);

        System.out.print("Digite o ano de publicação: ");
        int publicationYear = HandleInputException.handleIntInputException(input.next());
        validatePublication(publicationYear);

        Author author = authorSelect(authors);

        return new Book(title, gender, publicationYear, author);
    }

    public void editBook(BookDAO bookDAO, List<Author> authors, List<Reader> readers)
            throws InvalidBookTitleException, InvalidBookGenderException, PublicationDateFormatInvalidException {
        listBooks(new ArrayList<>(bookDAO.listAllBooks()), readers);
        System.out.print("Digite o número do livro: ");
        int id = HandleInputException.handleIntInputException(input.next());
        input.nextLine(); // Resolve momentaneamente lixo residual

        Book book = bookDAO.findBookById(id);

        if (book == null) {
            System.out.println("Número de livro inválido");
            return;
        }

        System.out.print("Digite o título do livro: ");
        String title = input.nextLine();
        Validation.validateArgument(title);

        if (checkBookTitle(bookDAO.listAllBooks(), title)) {
            System.out.println("Título de livro já cadastrado!");
            return;
        }

        System.out.println("+---+-------------------+---+--------------------+---+-------------+");
        System.out.println("| 1 | Fantasia          | 2 | Ficção             | 3 | Não-Ficção  |");
        System.out.println("| 4 | Romance           | 5 | Ficção-Científica  | 6 | Mistério    |");
        System.out.println("| 7 | Suspense/Terror   | 8 | Poesia             |   |             |");
        System.out.println("+---+-------------------+---+--------------------+---+-------------+");
        System.out.print("Selecione o gênero do livro: ");
        int gender = HandleInputException.handleIntInputException(input.next());
        input.nextLine(); // Resolve momentaneamente lixo residual
        validateGender(gender);

        System.out.print("Digite o ano de publicação: ");
        int publicationYear = HandleInputException.handleIntInputException(input.next());
        input.nextLine(); // Resolve momentaneamente lixo residual
        validatePublication(publicationYear);

        Author author = authorSelect(authors);
        bookDAO.editBook(book, title, gender, publicationYear, author);
        System.out.println("Livro atualizado com sucesso!");
    }

    public void listBooksByAuthor(List<Book> books, List<Author> authors, List<Reader> readers) {
        Author author = authorSelect(authors);
        if (author == null) return;
        List<Book> booksByAuthor = findBookByAuthor(books, author);
        if (booksByAuthor != null && !booksByAuthor.isEmpty()) {
            System.out.println("=============== LIVROS DO AUTOR ================");
            for (Book book : booksByAuthor) {
                System.out.println("+------------+---------------------------------+");
                System.out.println(book);
                System.out.println(checkIfIsLoaned(booksByAuthor, readers));
            }
            System.out.println("+------------+---------------------------------+");
        } else {
            System.out.println("Não há livros cadastrados com o autor selecionado...");
        }
    }

    public void listBooks(List<Book> books, List<Reader> readers) {
        if (books != null && !books.isEmpty()) {
            System.out.println("==================== LIVROS ====================");
            for (Book book : books) {
                System.out.println("+------------+---------------------------------+");
                System.out.println(book);
                System.out.println(checkIfIsLoaned(books, readers));
            }
            System.out.println("+------------+---------------------------------+");
        } else {
            System.out.println("Não há livros disponíveis...");
        }
    }

    public void deleteBook(BookDAO bookDAO, List<Reader> readers) {
        if (!bookDAO.listAllBooks().isEmpty()) {
            System.out.println("=============== EXCLUSÃO DE LIVRO ===============");
            listBooks(new ArrayList<>(bookDAO.listAllBooks()), readers);
            System.out.print("Digite o ID do livro: ");
            int id = HandleInputException.handleIntInputException(input.next());
            input.nextLine(); // Resolve momentaneamente lixo residual
            Book book = bookDAO.findBookById(id);

            if (book == null) {
                System.out.println("ID de livro inválido");
                return;
            }

            bookDAO.deleteBook(book);

            System.out.println("Livro excluído com sucesso!");
            System.out.println("=================================================");
        } else {
            System.out.println("Não há livros disponíveis...");
        }
    }

    private Author authorSelect(List<Author> authors) {
        if (!authors.isEmpty()) {
            System.out.println("+---+----------------------------------------");
            for (int i = 0; i < authors.size(); i++) {
                System.out.println("| " + (i + 1) + " | " + authors.get(i).getName());
            }
            System.out.println("+---+----------------------------------------");
            System.out.print("Selecione o autor pelo número: ");

            int authorNumber = HandleInputException.handleIntInputException(input.next());
            input.nextLine(); // Resolve momentaneamente lixo residual

            while (authorNumber <= 0 || authorNumber > authors.size()) {
                System.out.println("Opção inválida, selecione novamente!");
                for (int i = 0; i < authors.size(); i++) {
                    System.out.println((i + 1) + " - " + authors.get(i).getName());
                }
                authorNumber = HandleInputException.handleIntInputException(input.next());
                input.nextLine(); // Resolve momentaneamente lixo residual
            }

            return authors.get(authorNumber - 1);
        } else {
            System.out.println("Não existem autores cadastrados!");
            return null;
        }
    }

    private List<Book> findBookByAuthor(List<Book> books, Author author) {
        if (!books.isEmpty()) {
            List<Book> booksFromAuthor = new ArrayList<>();
            for (Book book : books) {
                if (book.getAuthor().equals(author)) {
                    booksFromAuthor.add(book);
                }
            }
            return booksFromAuthor;
        } else {
            return null;
        }
    }

    private void validateGender(int gender) throws InvalidBookGenderException {
        if (gender <= 0 || gender > 8) {
            throw new InvalidBookGenderException("Verifique o valor do gênero e tente novamente!");
        }
    }

    private void validatePublication(int publicationYear) throws PublicationDateFormatInvalidException {
        if (publicationYear < 1500 || publicationYear > 2025) {
            throw new PublicationDateFormatInvalidException("A data de publicação está inválida!");
        }
    }

    private String checkIfIsLoaned(List<Book> books, List<Reader> readers) {
        for (Book book : books) {
            if (!book.isLoanedBook()) {
                return "| Situaçao   | Disponível";
            } else {
                for (Reader reader : readers) {
                    for (Book borrowedBook : reader.getBorrowedBooks()) {
                        if (borrowedBook.equals(book)) {
                            return "| Emprestado | " + reader.getName();
                        }
                    }
                }
            }
        }
        return null;
    }

    private boolean checkBookTitle(Set<Book> books, String title) {
        for (Book book : books) {
            if (book.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }
}
