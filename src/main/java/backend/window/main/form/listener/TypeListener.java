package backend.window.main.form.listener;

import backend.window.main.form.constant.TypeEnum;
import frontend.controlElement.ComboBox;
import frontend.window.main.MainForm;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public record TypeListener(MainForm mainForm) implements ItemListener {

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            final Object source = e.getSource();
            final ComboBox<?> typeConstant;
            if (source instanceof JComboBox) {
                typeConstant = (ComboBox<?>) source;
                Object value = typeConstant.getSelectedItem();
                if (value instanceof TypeEnum) {
                    switch ((TypeEnum) value) {
                        case RF_PASSPORT -> {
                            mainForm.getCitizenshipTextField().setEditable(false);
                            mainForm.getCitizenshipTextField().setText(TypeEnum.CITIZENSHIP_RF);
                            mainForm.getSeriesTextField().setEnabled(true);
                            mainForm.getNumberTextField().updateBackground();
                            mainForm.getIssueIdTextField().setEnabled(true);
                        }
                        case FID_DOC -> {
                            mainForm.getCitizenshipTextField().setEditable(true);
                            mainForm.getCitizenshipTextField().setText(null);
                            mainForm.getSeriesTextField().setEnabled(false);
                            mainForm.getNumberTextField().updateBackground();
                            mainForm.getIssueIdTextField().setEnabled(false);
                        }
                    }
                }
            }
        }
    }
}
