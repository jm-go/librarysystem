package org.library.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.util.List;
import org.library.models.Book;

public class JSONWriter {
    public void writeBooksToJsonFile(List<Book> books, String targetFileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(targetFileName), books);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
