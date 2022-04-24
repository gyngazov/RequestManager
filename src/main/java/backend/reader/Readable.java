package backend.reader;

import backend.window.settings.SoftwareConfiguration;
import frontend.window.main.MainWindow;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.IOException;

public interface Readable {

    String read() throws IOException;

    private static @NotNull String getFileExtension(File file) {
        if (file != null && file.exists() && file.isFile()) {
            String filename = file.getName();
            if (filename.lastIndexOf(".") != -1 && filename.lastIndexOf(".") != 0) {
                return filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();
            } else {
                throw new IllegalArgumentException("Не удается определить расширение файла.");
            }
        } else {
            throw new IllegalArgumentException("Не удается найти указанный файл.");
        }
    }

    static @NotNull Readable of(File filename) {
        switch (getFileExtension(filename)) {
            case "PDF" -> {
                return new PDFFileReader(filename);
            }
            case "TXT" -> {
                return new TextFileReader(filename);
            }
            default -> throw new UnsupportedOperationException("Расширение этого файла является недопустимым.");
        }
    }

    static @Nullable File getSelectedDir() {
        JFileChooser fileChooser = new JFileChooser(SoftwareConfiguration.getInstance().getCurrentDirectory());
        fileChooser.setApproveButtonToolTipText("Выбрать папку с файлами");
        fileChooser.setDialogTitle("Обзор папок");
        fileChooser.setFileHidingEnabled(true);
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        if (fileChooser.showDialog(null, "Выбрать") == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }

        return null;
    }

    static @Nullable File getSelectedFile(FileFilter filter) {
        JFileChooser fileChooser = new JFileChooser(SoftwareConfiguration.getInstance().getCurrentDirectory());
        fileChooser.setFileFilter(filter);
        fileChooser.setFileHidingEnabled(true);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);
        if (fileChooser.showOpenDialog(MainWindow.getInstance()) == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }

        return null;
    }

    static @Nullable File getSelectedFile() {
        return getSelectedFile(null);
    }
}
