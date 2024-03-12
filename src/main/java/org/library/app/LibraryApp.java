package org.library.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.library.models.Book;
import org.library.models.User;
import org.library.services.AuthService;
import org.library.services.BookService;
import org.library.utils.CSVReader;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class LibraryApp {
    private final AuthService authService = new AuthService();
    private final BookService bookService;
    private User currentUser = null;
    private final Scanner scanner = new Scanner(System.in);

    public LibraryApp() {
        initialise();
        this.bookService = new BookService("src/main/resources/books.json");
    }

    public void run() {
        while (true) {
            System.out.println("Welcome to our library!");
            System.out.println("1. Login\n2. Exit");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    login();
                    if (currentUser != null) {
                        if ("admin".equals(currentUser.getRole())) {
                            adminMenu();
                        } else {
                            userMenu();
                        }
                    }
                    break;
                case "2":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void login() {
        System.out.println();
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        currentUser = authService.authenticate(username, password);
        if (currentUser == null) {
            System.out.println("Login failed. Please try again.");
        } else {
            System.out.println("Login successful.");
        }
    }

    private void userMenu() {
        String option;
        do {
            System.out.println("\nUser Menu:");
            System.out.println("1. View Available Books");
            System.out.println("2. Loan a Book");
            System.out.println("3. Return a Book");
            System.out.println("4. View Loaned Books");
            System.out.println("5. Logout");
            System.out.print("Select an option: ");
            option = scanner.nextLine();
            System.out.println();

            switch (option) {
                case "1":
                    viewAvailableBooks();
                    break;
                case "2":
                    loanBook();
                    break;
                case "3":
                    returnBook();
                    break;
                case "4":
                    viewLoanedBooks();
                    break;
                case "5":
                    logout();
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (!"5".equals(option));
    }

    private void viewAvailableBooks() {
        List<Book> availableBooks = bookService.getAvailableBooks();
        if (availableBooks.isEmpty()) {
            System.out.println("No available books at the moment.");
            return;
        }
        System.out.println("Available Books:");
        for (Book book : availableBooks) {
            System.out.println(book);
        }
    }


    private void adminMenu() {
        System.out.println("Admin Menu:");
        // TODO: Add all admin actions - see reports 1) books that are out on loan 2) how many times the book has been loaned out
    }

    private void loanBook() {
        viewAvailableBooks();
        System.out.println("Enter the number of the book you wish to loan:");
        int bookNumber = Integer.parseInt(scanner.nextLine());
        boolean success = bookService.loanBook(bookNumber, currentUser.getUsername());
        if (success) {
            System.out.println("Book loaned successfully.");
        } else {
            System.out.println("Failed to loan book. It might not be available.");
        }
    }

    private void returnBook() {
        System.out.println("Enter the number of the book you are returning:");
        int bookNumber = Integer.parseInt(scanner.nextLine()); // Simple parsing; consider validating input
        boolean success = bookService.returnBook(bookNumber, currentUser.getUsername());
        if (success) {
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("Failed to return the book. Please check the number and try again.");
        }
    }

    private void viewLoanedBooks() {
        List<Book> loanedBooks = bookService.getLoanedBooks(currentUser.getUsername());
        if (loanedBooks.isEmpty()) {
            System.out.println("You have not loaned any books.");
            return;
        }
        System.out.println("Your Loaned Books:");
        for (Book book : loanedBooks) {
            System.out.println(book);
        }
    }

    private void logout() {
        currentUser = null;
        System.out.println("You have been logged out successfully.");
    }

    private void initialise() {
        List<Book> books = CSVReader.readBooksFromCSV("books.csv");

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.writeValue(new File("books.json"), books);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
