package backend.reader;

import org.apache.pdfbox.io.RandomAccessReadBufferedFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;

public class PDFFileReader extends FileReader {
    private final int firstPage = 1;
    private final int lastPage = 7;

    public PDFFileReader(File file) {
        super(file);
    }

    @Override
    public String read() throws IOException {
        try (PDDocument pdDocument = new PDFParser(new RandomAccessReadBufferedFile(file)).parse()) {
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            pdfTextStripper.setStartPage(firstPage);
            pdfTextStripper.setEndPage(lastPage);
            pdfTextStripper.setLineSeparator(System.lineSeparator());
            return pdfTextStripper.getText(pdDocument);
        }
    }

    @Override
    public String toString() {
        return "PDFFileReader{" + "firstPage=" + firstPage +
                ", lastPage=" + lastPage +
                ", file=" + file +
                '}';
    }
}
