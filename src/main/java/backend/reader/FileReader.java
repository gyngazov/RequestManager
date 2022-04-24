package backend.reader;

import java.io.File;

public abstract class FileReader implements Readable {
    protected final File file;

    public FileReader(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
