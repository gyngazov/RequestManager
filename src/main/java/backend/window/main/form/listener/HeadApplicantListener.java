package backend.window.main.form.listener;

import frontend.controlElement.TextField;
import frontend.window.main.MainForm;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public final class HeadApplicantListener implements ItemListener {

    record Listener(TextField applicant, TextField organization) implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            applicant.setText(organization.getText());
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            applicant.setText(organization.getText());
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
        }
    }

    private final TextField headLastNameTextField;
    private final TextField headFirstNameTextField;
    private final TextField headMiddleNameTextField;
    private final TextField headPersonINNTextField;
    private final TextField headTitleTextField;

    private final TextField lastNameTextField;
    private final TextField firstNameTextField;
    private final TextField middleNameTextField;
    private final TextField personINNTextField;
    private final TextField titleTextField;

    private final Listener headLastNameListener;
    private final Listener headFirstNameListener;
    private final Listener headMiddleNameListener;
    private final Listener headPersonInnListener;
    private final Listener headTitleNameListener;

    public HeadApplicantListener(MainForm mainForm) {
        headLastNameTextField = mainForm.getHeadLastNameTextField();
        headFirstNameTextField = mainForm.getHeadFirstNameTextField();
        headMiddleNameTextField = mainForm.getHeadMiddleNameTextField();
        headPersonINNTextField = mainForm.getHeadPersonINNTextField();
        headTitleTextField = mainForm.getHeadTitleTextField();

        lastNameTextField = mainForm.getLastNameTextField();
        firstNameTextField = mainForm.getFirstNameTextField();
        middleNameTextField = mainForm.getMiddleNameTextField();
        personINNTextField = mainForm.getPersonINNTextField();
        titleTextField = mainForm.getTitleTextField();


        headLastNameListener = new Listener(lastNameTextField, headLastNameTextField);
        headFirstNameListener = new Listener(firstNameTextField, headFirstNameTextField);
        headMiddleNameListener = new Listener(middleNameTextField, headMiddleNameTextField);
        headPersonInnListener = new Listener(personINNTextField, headPersonINNTextField);
        headTitleNameListener = new Listener(titleTextField, headTitleTextField);
    }

    private void setEditable(boolean b) {
        lastNameTextField.setEditable(b);
        firstNameTextField.setEditable(b);
        middleNameTextField.setEditable(b);
        personINNTextField.setEditable(b);
        titleTextField.setEditable(b);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            setEditable(false);

            lastNameTextField.setText(headLastNameTextField.getText());
            firstNameTextField.setText(headFirstNameTextField.getText());
            middleNameTextField.setText(headMiddleNameTextField.getText());
            personINNTextField.setText(headPersonINNTextField.getText());
            titleTextField.setText(headTitleTextField.getText());

            headLastNameTextField.getDocument().addDocumentListener(headLastNameListener);
            headFirstNameTextField.getDocument().addDocumentListener(headFirstNameListener);
            headMiddleNameTextField.getDocument().addDocumentListener(headMiddleNameListener);
            headPersonINNTextField.getDocument().addDocumentListener(headPersonInnListener);
            headTitleTextField.getDocument().addDocumentListener(headTitleNameListener);
        } else {
            setEditable(true);

            headLastNameTextField.getDocument().removeDocumentListener(headLastNameListener);
            headFirstNameTextField.getDocument().removeDocumentListener(headFirstNameListener);
            headMiddleNameTextField.getDocument().removeDocumentListener(headMiddleNameListener);
            headPersonINNTextField.getDocument().removeDocumentListener(headPersonInnListener);
            headTitleTextField.getDocument().removeDocumentListener(headTitleNameListener);
        }
    }
}