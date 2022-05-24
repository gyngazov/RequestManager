package backend.window.main.bar.listener;

import backend.reader.Readable;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ImportDocumentIEcp implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
        File currentDirectory = Readable.getSelectedDir();
        if (currentDirectory != null) {

        }
    }
}
