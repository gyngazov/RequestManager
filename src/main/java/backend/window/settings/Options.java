package backend.window.settings;

import backend.util.Validation;

import java.io.*;

public final class Options implements Serializable {
    private static final String FILENAME = "RequestManagerSettings";

    private String currentDirectory;
    private String login;
    private String password;
    private boolean verifiedOrgInn;

    public Options() {
        currentDirectory = System.getProperty("user.dir");
        login = "";
        password = "";
        verifiedOrgInn = true;
    }

    public void setCurrentDirectory(String currentDirectory) {
        currentDirectory = Validation.getFormattedCurrentDirectory(currentDirectory);
        if (Validation.isCorrectCurrentDirectory(currentDirectory)) {
            this.currentDirectory = currentDirectory;
        }
    }

    public void setLogin(String login) {
        login = Validation.getFormattedEmailAddress(login);
        if (Validation.isCorrectEmailAddress(login)) {
            this.login = login;
        }
    }

    public void setPassword(String password) {
        if (Validation.isCorrectPassword(password)) {
            this.password = password;

        }
    }

    public void setVerifiedOrgInn(boolean verifiedOrgInn) {
        this.verifiedOrgInn = verifiedOrgInn;
    }

    public String getCurrentDirectory() {
        return currentDirectory;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean isVerifiedOrgInn() {
        return verifiedOrgInn;
    }

    public void save() throws IOException {
        try (ObjectOutputStream output = new ObjectOutputStream(
                new FileOutputStream(
                        new File(System.getProperty("java.io.tmpdir"), FILENAME), false))) {
            output.writeObject(this);
        }
    }

    public static Options read() {
        try (ObjectInputStream input = new ObjectInputStream(
                new FileInputStream(
                        new File(System.getProperty("java.io.tmpdir"), FILENAME)))) {
            return (Options) input.readObject();
        } catch (Exception e) {
            return new Options();
        }
    }
}
