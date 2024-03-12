package org.library.app;

import org.library.models.Book;
import org.library.utils.CSVReader;
import org.library.utils.JSONWriter;
import java.util.List;

public class LibraryApp {
    public void initialise() {
        CSVReader csvReader = new CSVReader();
        List<Book> books = csvReader.readBooksFromCSV("books.csv");

        JSONWriter jsonWriter = new JSONWriter();
        jsonWriter.writeBooksToJsonFile(books, "books.json");

        System.out.println("Library system initialised. Books data written to books.json");
    }
}
