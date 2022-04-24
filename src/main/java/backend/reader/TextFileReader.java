package backend.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

public class TextFileReader extends FileReader {

    public TextFileReader(File file) {
        super(file);
    }

    @Override
    public String read() throws IOException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append(System.lineSeparator());
            }
        }
        return builder.toString();
    }
}
