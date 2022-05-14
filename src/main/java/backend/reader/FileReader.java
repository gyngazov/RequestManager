package backend.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class FileReader implements Readable {
    protected final File file;

    public FileReader(File file) {
        this.file = file;
    }

    public final byte[] readAllBytes() throws IOException {
        try (InputStream reader = new FileInputStream(file)) {
            return reader.readAllBytes();
        }
    }
}
