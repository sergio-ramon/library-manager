package service;

import dao.AuthorDAO;
import dao.BookDAO;
import dao.ReaderDAO;
import exceptions.InvalidBookGenderException;
import exceptions.InvalidBookTitleException;
import exceptions.InvalidCpfFormatException;
import exceptions.InvalidInputTypeException;
import exceptions.InvalidArgumentException;
import exceptions.InvalidPhoneFormatException;
import exceptions.PublicationDateFormatInvalidException;

import java.util.ArrayList;
import java.util.Scanner;

public class MenuManager {
    Scanner input = new Scanner(System.in);
    private final AuthorDAO authorDAO;
    private final BookDAO bookDAO;
    private final ReaderDAO readerDAO;
    private final AuthorManager authorManager = new AuthorManager();
    private final BookManager bookManager = new BookManager();
    private final ReaderManager readerManager = new ReaderManager();
    private final LoanManager loanManager = new LoanManager(bookManager, readerManager);

    public MenuManager(AuthorDAO authorDAO, BookDAO bookDAO, ReaderDAO readerDAO) {
        this.authorDAO = authorDAO;
        this.bookDAO = bookDAO;
        this.readerDAO = readerDAO;
    }

    public void mainMenu() throws InvalidInputTypeException {
        boolean mainsMenuSystem = true;
        do {
            MenuMessageManager.showMainMenuMessage();
            int selectedOption = HandleInputException.handleIntInputException(input.next());
            input.nextLine(); // Resolve momentaneamente lixo residual

            switch (selectedOption) {
                case 0 -> {
                    System.out.println("Até breve!");
                    mainsMenuSystem = false;
                }
                case 1 -> registerMenu();
                case 2 -> listMenu();
                case 3 -> editionMenu();
                case 4 -> loanMenu();
                case 5 -> deleteMenu();
                default -> System.out.println("Opção inválida, tente novamente!");
            }
        } while (mainsMenuSystem);
    }

    private void registerMenu() {
        boolean registerMenuSystem = true;

        do {
            MenuMessageManager.showRegisterMenuMessage();
            int selectedOption = HandleInputException.handleIntInputException(input.next());
            input.nextLine(); // Resolve momentaneamente lixo residual

            switch (selectedOption) {
                case 0 -> registerMenuSystem = false;
                case 1 -> {
                    try {
                        System.out.println("============== CADASTRO DE AUTOR ==============");
                        System.out.println(authorDAO.saveAuthor(authorManager.createAuthor()));
                        System.out.println("===============================================");
                    } catch (InvalidArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    registerMenuSystem = false;
                }
                case 2 -> {
                    verifyAuthors();
                    registerMenuSystem = false;
                }
                case 3 -> {
                    try {
                        System.out.println("============== CADASTRO DE LEITOR ==============");
                        System.out.println(readerDAO.saveReader(readerManager.createReader(readerDAO)));
                        System.out.println("================================================");
                    } catch (InvalidArgumentException | InvalidCpfFormatException | InvalidPhoneFormatException e) {
                        System.out.println(e.getMessage());
                    }
                    registerMenuSystem = false;
                }
                default -> System.out.println("Opção inválida, tente novamente!");
            }
        } while (registerMenuSystem);
    }

    private void editionMenu() {
        boolean editionMenuSystem = true;

        do {
            MenuMessageManager.showUpdateMessage();
            int selectedOption = HandleInputException.handleIntInputException(input.next());
            input.nextLine(); // Resolve momentaneamente lixo residual

            switch (selectedOption) {
                case 0 -> editionMenuSystem = false;
                case 1 -> {
                    if (!authorDAO.listAuthors().isEmpty()) {
                        try {
                            authorManager.editAuthor(authorDAO);
                        } catch (InvalidArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Não existem autores para edição...");
                        System.out.println("Considere cadastrar um novo autor antes!");
                    }
                    editionMenuSystem = false;
                }
                case 2 -> {
                    if (!readerDAO.listReaders().isEmpty()) {
                        try {
                            readerManager.editReader(readerDAO);
                        } catch (InvalidArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Não existem leitor para edição...");
                        System.out.println("Considere cadastrar um novo leitor antes!");
                    }
                    editionMenuSystem = false;
                }
                case 3 -> {
                    if (!bookDAO.listAvailableBooks().isEmpty()) {
                        try {
                            System.out.println("=============== EDIÇÃO DE LIVRO ===============");
                            bookManager.editBook(bookDAO, authorDAO.listAuthors(), readerDAO.listReaders());
                            System.out.println("===============================================");
                        } catch (InvalidBookTitleException | InvalidBookGenderException |
                                 PublicationDateFormatInvalidException e) {
                            System.out.println(e.getMessage());
                        }
                    } else {
                        System.out.println("Não existem livros para edição...");
                        System.out.println("Considere cadastrar um novo livro antes!");
                    }
                    editionMenuSystem = false;
                }
                default -> System.out.println("Opção inválida, tente novamente!");
            }
        } while (editionMenuSystem);
    }

    private void listMenu() {
        boolean listMenuSystem = true;

        do {
            MenuMessageManager.showListMenuMessage();
            int selectedOption = HandleInputException.handleIntInputException(input.next());
            input.nextLine(); // Resolve momentaneamente lixo residual

            switch (selectedOption) {
                case 0 -> listMenuSystem = false;
                case 1 -> authorManager.listAuthors(authorDAO.listAuthors());
                case 2 -> readerManager.listReaders(readerDAO.listReaders());
                case 3 -> bookManager.listBooks(new ArrayList<>(bookDAO.listAllBooks()), readerDAO.listReaders());
                case 4 -> bookManager.listBooksByAuthor(new ArrayList<>(bookDAO.listAllBooks()), authorDAO.listAuthors(), readerDAO.listReaders());
                default -> System.out.println("Opção inválida, tente novamente!");
            }
        } while (listMenuSystem);
    }

    private void loanMenu() {
        boolean loanMenuSystem = true;

        do {
            MenuMessageManager.showLoanMenuMessage();
            int selectedOption = HandleInputException.handleIntInputException(input.next());
            input.nextLine(); // Resolve momentaneamente lixo residual

            switch (selectedOption) {
                case 0 -> loanMenuSystem = false;
                case 1 -> {
                    loanManager.startBookLoan(bookDAO, readerDAO.listReaders());
                    loanMenuSystem = false;
                }
                case 2 -> {
                    loanManager.startBookReturn(bookDAO, readerDAO.listReaders());
                    loanMenuSystem = false;
                }
                default -> System.out.println("Opção inválida, tente novamente!");
            }
        } while (loanMenuSystem);
    }

    private void deleteMenu() {
        boolean deleteMenuSystem = true;

        do {
            MenuMessageManager.showDeleteMessage();
            int selectedOption = HandleInputException.handleIntInputException(input.next());
            input.nextLine(); // Resolve momentaneamente lixo residual

            switch (selectedOption) {
                case 0 -> deleteMenuSystem = false;
                case 1 -> {
                    authorManager.deleteAuthor(authorDAO, bookDAO);
                    deleteMenuSystem = false;
                }
                case 2 -> {
                    readerManager.deleteReader(readerDAO);
                    deleteMenuSystem = false;
                }
                case 3 -> {
                    bookManager.deleteBook(bookDAO, readerDAO.listReaders());
                    deleteMenuSystem = false;
                }
                default -> System.out.println("Opção inválida, tente novamente!");
            }
        } while (deleteMenuSystem);
    }

    private void verifyAuthors() {
        if (authorDAO.listAuthors().isEmpty()) {
            System.out.println("Não existem autoress para associação...");
            System.out.println("Considere cadastrar um novo autor antes!");
        } else {
            try {
                System.out.println("============== CADASTRO DE LIVRO ==============");
                System.out.println(bookDAO.saveBook(bookManager.generateNewBook(authorDAO.listAuthors())));
                System.out.println("===============================================");
            } catch (InvalidBookTitleException | PublicationDateFormatInvalidException | InvalidBookGenderException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
