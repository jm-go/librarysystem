package org.library.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.library.models.Book;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    /**
     * Reads books from a CSV file and converts them into a list of {@link Book} objects.
     *
     * This method opens a CSV file, parses it to extract book information,
     * and returns a list of {@link Book} objects created from that data.
     * The CSV file has headers corresponding to the book attributes: Number, Title, Author, Genre, SubGenre, Publisher.
     *
     * @param resourceFileName The name of the CSV file to read from, located in the resources directory.
     * @return A list of {@link Book} objects populated with data read from the CSV file.
     */
    public static List<Book> readBooksFromCSV(String resourceFileName) {
        List<Book> books = new ArrayList<>();
        try (InputStream inputStream = CSVReader.class.getClassLoader().getResourceAsStream(resourceFileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            for (CSVRecord csvRecord : csvParser) {
                // Accessing values by the names assigned to each column
                int number = Integer.parseInt(csvRecord.get("Number"));
                String title = csvRecord.get("Title");
                String author = csvRecord.get("Author");
                String genre = csvRecord.get("Genre");
                String subGenre = csvRecord.get("SubGenre");
                String publisher = csvRecord.get("Publisher");

                Book book = new Book(number, title, author, genre, subGenre, publisher);
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
}