package service;

import dao.AuthorDAO;
import dao.BookDAO;
import domain.Author;
import domain.Book;
import exceptions.InvalidArgumentException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AuthorManager {
    static Scanner input = new Scanner(System.in);

    public Author createAuthor() throws InvalidArgumentException {

        System.out.print("Digite o nome do autor: ");
        String name = input.nextLine();
        Validation.validateArgument(name);

        return new Author(name);
    }

    public void editAuthor(AuthorDAO authorDAO) throws InvalidArgumentException {
        listAuthors(authorDAO.listAuthors());
        System.out.print("Digite o número do autor: ");
        int id = HandleInputException.handleIntInputException(input.next());
        input.nextLine(); // Resolve momentaneamente lixo residual
        Author author = authorDAO.findAuthorById(id);

        if (author == null) {
            System.out.println("Número de autor inválido!");
            return;
        }

        System.out.println("============== EDIÇÃO DE AUTOR ==============");
        System.out.print("Digite o novo nome do autor: ");
        String name = input.nextLine();
        Validation.validateArgument(name);
        if (!checkAuthorName(authorDAO.listAuthors(), name)) {
            System.out.println("Nome de autor já cadastrado! Tente novamente!");
            return;
        }

        System.out.println("Autor editado com sucesso!");
        System.out.println("===============================================");
        author.setName(name);
    }

    public void listAuthors(List<Author> authors) {
        if (authors != null && !authors.isEmpty()) {
            System.out.println("============ AUTORES CADASTRADOS ============");
            System.out.println("+---+----------------------------------------");
            for (Author author : authors) {
                System.out.println(author);
            }
            System.out.println("+---+----------------------------------------");
        } else {
            System.out.println("Não existem autores cadastrados!");
        }
    }

    public void deleteAuthor(AuthorDAO authorDAO, BookDAO bookDAO) {
        if (!authorDAO.listAuthors().isEmpty()) {
            listAuthors(authorDAO.listAuthors());
            System.out.print("Digite o número do autor: ");
            int id = HandleInputException.handleIntInputException(input.next());
            input.nextLine(); // Resolve momentaneamente lixo residual
            Author author = authorDAO.findAuthorById(id);

            if (author == null) {
                System.out.println("Número de autor inválido!");
                return;
            }

            List<Book> booksToDelete = new ArrayList<>();

            if (!bookDAO.listAllBooks().isEmpty()) {
                for (Book book : bookDAO.listAllBooks()) {
                    if (book.getAuthor().equals(author)) {
                        booksToDelete.add(book);
                    }
                }
            }

            for (Book book : booksToDelete) {
                bookDAO.deleteBook(book);
                bookDAO.deleteBook(book);
            }

            authorDAO.deleteAuthors(author);
            System.out.println("Autor excluído com sucesso!");
            System.out.println("===============================================");
        } else {
            System.out.println("Não existem autores cadastrados...");
        }
    }

    public boolean checkAuthorName(List<Author> authors, String name) {
        for (Author a : authors) {
            if (a.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
