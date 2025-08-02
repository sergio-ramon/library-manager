import dao.AuthorDAO;
import dao.BookDAO;
import dao.ReaderDAO;
import service.MenuManager;

import java.util.Scanner;

public class LibraryTest {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        AuthorDAO authorDAO = new AuthorDAO();
        BookDAO bookDAO = new BookDAO();
        ReaderDAO readerDAO = new ReaderDAO();

        MenuManager menu = new MenuManager(authorDAO, bookDAO, readerDAO);

        menu.mainMenu();

        input.close();
    }
}
