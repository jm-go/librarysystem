package org.library.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.library.models.Book;
import org.library.models.User;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class BookService {

    private List<Book> books;
    private final AuthService authService;

    public BookService(String filePath) {
        this.books = new ArrayList<>();
        this.authService = new AuthService();
        loadBooksFromJson("books.json");
    }

    /**
     * Loads books from a JSON file into the book list.
     *
     * @param filePath The path to the JSON file.
     */
    private void loadBooksFromJson(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Book>> typeReference = new TypeReference<>() {};
        try {
            File file = new File(filePath);
            if (file.exists()) {
                this.books = objectMapper.readValue(file, typeReference);
                System.out.println("Books loaded successfully.\n");
            }
        } catch (IOException e) {
            System.err.println("Could not load books from file: " + filePath);
            e.printStackTrace();
        }
    }


    /**
     * Returns a list of all books that are currently not loaned out.
     *
     * @return A list of available books.
     */
    public List<Book> getAvailableBooks() {
        return books.stream()
                .filter(book -> !book.getIsLoaned())
                .collect(Collectors.toList());
    }

    /**
     * Loans a book to a user if it's available.
     *
     * Identifies a book by its number. If the book is not currently loaned,
     * it marks the book as loaned to the specified user, increments its loan count,
     * and updates the JSON storage to reflect the change.
     *
     * @param bookNumber The identifier of the book to loan.
     * @param username The username of the user loaning the book.
     * @return true if the book is successfully loaned, false if the book is already loaned or not found.
     */
    public boolean loanBook(int bookNumber, String username) {
        boolean isLoaned = false;
        for (Book book : books) {
            if (book.getNumber() == bookNumber && !book.getIsLoaned()) {
                book.setIsLoaned(true);
                book.setLoanedTo(username);
                book.incrementTimesLoaned();
                isLoaned = true;
                break;
            }
        }
        if (isLoaned) {
            User user = authService.getUserByUsername(username);
            if (user != null) {
                user.addLoanedBookNumber(bookNumber);
                authService.saveUsersToJson();
            }
            saveBooksToJson();
        }
        return isLoaned;
    }

    /**
     * Returns a loaned book and updates its status.
     *
     * Finds a book by its number and the username of the borrower. If found and loaned,
     * marks the book as available again and updates the JSON storage.
     *
     * @param bookNumber The identifier of the book to return.
     * @param username The borrower's username.
     * @return true if the book is successfully returned, false otherwise.
     */
    public boolean returnBook(int bookNumber, String username) {
        boolean isReturned = false;
        for (Book book : books) {
            if (book.getNumber() == bookNumber && book.getIsLoaned() && username.equals(book.getLoanedTo())) {
                book.setIsLoaned(false);
                book.setLoanedTo(null);
                isReturned = true;
                break;
            }
        }
        if (isReturned) {
            saveBooksToJson();
        }
        return isReturned;
    }

    /**
     * Gets a list of books currently loaned by a specific user.
     *
     * @param username The username of the user.
     * @return A list of books loaned by the user.
     */
    public List<Book> getLoanedBooks(String username) {
        return books.stream()
                .filter(book -> username.equals(book.getLoanedTo()))
                .collect(Collectors.toList());
    }

    /**
     * Saves the current state of books (including loan information) to a JSON file.
     */
    private void saveBooksToJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            objectMapper.writeValue(new File("books.json"), books);
            System.out.println("SYSTEM MESSAGE: Books saved successfully to " + "books.json");
        } catch (IOException e) {
            System.err.println("Error saving books to file: " + "books.json");
            e.printStackTrace();
        }
    }

    /**
     * Reports books currently out on loan.
     *
     * @return A list of books that are currently loaned out.
     */
    public List<Book> getLoanedBooksReport() {
        return books.stream().filter(Book::getIsLoaned).collect(Collectors.toList());
    }

    /**
     * Prints a report showing the loan count for each book.
     */
    public void printLoanCountReport() {
        books.forEach(book -> System.out.println(book.getNumber() + ". " + book.getTitle() + " has been loaned out " + book.getTimesLoaned() + " times."));
    }

}
