package org.library.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.library.models.Book;

import java.io.InputStream;
import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class BookService {
    private List<Book> books;

    public BookService(String filePath) {
        this.books = new ArrayList<>();
        loadBooksFromJson("books.json");
    }

    private void loadBooksFromJson(String resourcePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Book>> typeReference = new TypeReference<List<Book>>() {};
        InputStream inputStream = BookService.class.getClassLoader().getResourceAsStream(resourcePath);

        try {
            this.books = objectMapper.readValue(inputStream, typeReference);
            System.out.println("Books loaded successfully.\n");
        } catch (Exception e) {
            System.err.println("Could not load books from file: " + resourcePath);
            e.printStackTrace();
        }
    }


    // Get a list of all available books (not currently loaned out)
    public List<Book> getAvailableBooks() {
        return books.stream()
                .filter(book -> !book.getIsLoaned())
                .collect(Collectors.toList());
    }

    // Loan out a book to a user if it's available
    public boolean loanBook(int bookNumber, String username) {
        for (Book book : books) {
            if (book.getNumber() == bookNumber && !book.getIsLoaned()) {
                book.setIsLoaned(true);
                book.setLoanedTo(username);
                book.incrementTimesLoaned();
                return true;
            }
        }
        return false;
    }

    // Return a book that was previously loaned
    public boolean returnBook(int bookNumber, String username) {
        for (Book book : books) {
            if (book.getNumber() == bookNumber && book.getIsLoaned() && username.equals(book.getLoanedTo())) {
                book.setIsLoaned(false);
                book.setLoanedTo(null);
                // TODO: Save changes to the persistent storage
                return true;
            }
        }
        return false;
    }

    // Get a list of books currently loaned by a specific user
    public List<Book> getLoanedBooks(String username) {
        return books.stream()
                .filter(book -> username.equals(book.getLoanedTo()))
                .collect(Collectors.toList());
    }

    // Save the current state of books (including loan information) to a JSON file
    private void saveBooksToJson(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            objectMapper.writeValue(new File(filePath), books);
            System.out.println("Books saved successfully to " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving books to file: " + filePath);
            e.printStackTrace();
        }
    }

    // Report of books currently out on loan
    public List<Book> getLoanedBooksReport() {
        return books.stream().filter(Book::getIsLoaned).collect(Collectors.toList());
    }

    public void printLoanCountReport() {
        books.forEach(book -> System.out.println(book.getNumber() + ". " + book.getTitle() + " has been loaned out " + book.getTimesLoaned() + " times."));
    }

}
