package backend.window.main.bar.listener;

import backend.window.settings.Options;
import frontend.window.main.filter.Table;
import frontend.window.optionDialog.MessageDialog;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public record ExportAttachedFileIEcp(Table table) implements ActionListener {

    private @Nullable File getWorkingDirectory() {
        JFileChooser fileChooser = new JFileChooser(Options.read().getCurrentDirectory());
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

    private @Nullable File[] getListFiles(@NotNull File workingDirectory, String regex) {
        return workingDirectory.listFiles(((dir, name) -> name.matches(regex)));
    }

    private boolean checkZip_SigFiles(File workingDirectory, File[] listZipFiles) {
        if (listZipFiles == null || listZipFiles.length == 0) {
            return false;
        } else {
            File[] listSigFiles = getListFiles(workingDirectory, "documents_?\\d*\\.zip\\.sig");
            if (listSigFiles == null || listSigFiles.length == 0 || listSigFiles.length != listZipFiles.length) {
                return false;
            } else {
                for (File zipFile : listZipFiles) {
                    File sigFile = new File(zipFile.getAbsolutePath() + ".sig");
                    if (!sigFile.exists() || sigFile.length() > 5120) {
                        return false;
                    }
                }
                return true;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final File workingDirectory = getWorkingDirectory();
        if (workingDirectory == null) return;

        File[] listZipFiles = getListFiles(workingDirectory, "documents_?\\d*\\.zip");
        try {
            if (checkZip_SigFiles(workingDirectory, listZipFiles)) {
                // TODO
            } else {
                new MessageDialog.Error("<html><body>Ошибка при отправке документов.<br>Перед отправкой документов необходимо их подписать открепленной ЭП и повторить попытку.</body></html>");
            }
        } catch (Exception exception) {
            new MessageDialog.Error("<html><body>Ошибка при отправке документов.<br>"
                    + exception.getMessage()
                    + "</body></html>");
        }
    }
}
