package org.library.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;
import org.library.models.Book;

public class JSONWriter {

    /**
     * Writes a list of books to a JSON file.
     *
     * Uses Jackson to convert the list of {@link Book} objects to a formatted JSON string
     * and writes it to the specified file. The file will be created or overwritten.
     *
     * @param books List of books to serialise.
     * @param targetFileName File path for the output JSON.
     */
    public void writeBooksToJsonFile(List<Book> books, String targetFileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(targetFileName), books);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
