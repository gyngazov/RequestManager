package backend.window.main.bar.listener;

import backend.window.settings.Options;
import frontend.window.optionDialog.MessageDialog;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.Objects;

public final class PackingFiles implements ActionListener {

    private @Nullable File getCurrentDirectory() {
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

    private @Nullable File[] getListFiles(@NotNull File currentDirectory, String codename) {
        return currentDirectory.listFiles(((dir, name) -> name.matches("[" + codename + "]_?\\d*\\.[a-zA-z]{3,}")));
    }

    private boolean checkFiles(@NotNull File currentDirectory) throws Exception {
        File[] listFiles = currentDirectory.listFiles();
        if (listFiles == null || listFiles.length == 0 || Arrays.stream(listFiles).mapToLong(File::length).sum() == 0) {
            throw new Exception("Операция не завершена, т.к. папка \""
                    + currentDirectory.getName()
                    + "\" пуста.");
        }

        File[] listOfScannedPassports = getListFiles(currentDirectory, "pP");
        if (listOfScannedPassports == null || listOfScannedPassports.length != 1) {
            throw new Exception("Проверьте наличие электронного образа паспорта.<br>Убедитесь, что в папке \""
                    + currentDirectory.getName()
                    + "\" расположен один образ паспорта и повторите попытку.");
        }

        File[] listOfScannedSNILS = getListFiles(currentDirectory, "sS");
        if (listOfScannedSNILS == null || listOfScannedSNILS.length != 1) {
            throw new Exception("Проверьте наличие электронного образа СНИЛС.<br>Убедитесь, что в папке \""
                    + currentDirectory.getName()
                    + "\" расположен один образ документа и повторите попытку.");
        }

        File[] listOfScannedProcurations = getListFiles(currentDirectory, "dD");
        if (listOfScannedProcurations != null && listOfScannedProcurations.length > 1) {
            throw new Exception("Обнаружено наличие нескольких электронных образов доверенностей.<br>Удалите ненужные файлы и повторите попытку.");
        }

        File[] listOfScannedApplications = getListFiles(currentDirectory, "zZ");
        if (listOfScannedApplications == null || listOfScannedApplications.length == 0) {
            throw new Exception("Проверьте наличие электронного образа заявления.<br>Убедитесь, что в папке \""
                    + currentDirectory.getName()
                    + "\" расположено не менее одного образа документа и повторите попытку.");
        }

        File[] photoList = getListFiles(currentDirectory, "fF");
        if (photoList == null || photoList.length == 0) {
            throw new Exception("Проверьте наличие фотографии заявителя.<br>Убедитесь, что в папке \""
                    + currentDirectory.getName()
                    + "\" расположено не менее одной фотографии и повторите попытку.");
        }

        if (listOfScannedApplications.length != photoList.length) {
            throw new Exception("Общее количество электронных образов заявлений не совпадает с общим количеством фотографий.");
        }

        for (File scannedApplication : listOfScannedApplications) {
            String scannedApplicationName = scannedApplication.getName().replaceAll("[^\\d]+", "");
            boolean throwException = false;
            for (File photo : photoList) {
                String photoName = photo.getName().replaceAll("[^\\d]+", "");
                if (Objects.equals(scannedApplicationName, photoName)) {
                    throwException = false;
                    break;
                } else {
                    throwException = true;
                }
            }
            if (throwException) {
                throw new Exception("Электронному образу заявления \"" + scannedApplication.getName() + "\" не найдена фотография.");
            }
        }

        return true;
    }

    private void pack(File currentDirectory) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final File currentDirectory = getCurrentDirectory();
        if (currentDirectory == null) return;

        try {
            if (checkFiles(currentDirectory)) {
                pack(currentDirectory);
            }
        } catch (Exception exception) {
            new MessageDialog.Error("<html><body>" + exception.getMessage() + "</body></html>");
        }
    }
}
