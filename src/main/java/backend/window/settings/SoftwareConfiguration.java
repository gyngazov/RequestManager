package backend.window.settings;

import backend.util.Validatable;

import java.io.*;

public final class SoftwareConfiguration implements Serializable {
    private static final String SAVE_PATH = System.getProperty("java.io.tmpdir");
    private static final String CONFIGURATION_FILE = "RequestManagerConfiguration";

    private static SoftwareConfiguration configuration;

    private String currentDirectory;
    private String login;
    private String password;
    private boolean verifiedOrgInn;

    public static class Builder {
        SoftwareConfiguration configuration;

        public Builder() {
            configuration = SoftwareConfiguration.getInstance();
        }

        public Builder setCurrentDirectory(String currentDirectory) {
            currentDirectory = Validatable.getFormattedCurrentDirectory(currentDirectory);
            if (Validatable.isCorrectCurrentDirectory(currentDirectory)) {
                configuration.currentDirectory = currentDirectory;
                return this;
            } else {
                throw new IllegalArgumentException("Указанный путь не существует.");
            }
        }

        public Builder setLogin(String login) {
            login = Validatable.getFormattedEmailAddress(login);
            if (Validatable.isCorrectEmailAddress(login)) {
                configuration.login = login;
                return this;
            } else {
                throw new IllegalArgumentException("Указан неверный электронный адрес почты.");
            }

        }

        public Builder setPassword(char[] chars) {
            String password = String.valueOf(chars);
            if (Validatable.isCorrectPassword(password)) {
                configuration.password = password;
                return this;
            } else {
                throw new IllegalArgumentException("Пароль не отвечает требованиям безопасности.");
            }
        }

        public Builder setVerifiedOrgInn(boolean verifiedOrgInn) {
            configuration.verifiedOrgInn = verifiedOrgInn;
            return this;
        }

        public SoftwareConfiguration build() {
            return configuration;
        }
    }

    private SoftwareConfiguration() {
        currentDirectory = System.getProperty("user.dir");
        verifiedOrgInn = true;
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
                        new File(SAVE_PATH, CONFIGURATION_FILE), false))) {
            output.writeObject(this);
        }
    }

    public static SoftwareConfiguration getInstance() {
        if (configuration == null) {
            try (ObjectInputStream input = new ObjectInputStream(
                    new FileInputStream(
                            new File(SAVE_PATH, CONFIGURATION_FILE)))) {
                return configuration = (SoftwareConfiguration) input.readObject();
            } catch (IOException | ClassNotFoundException e) {
                return configuration = new SoftwareConfiguration();
            }
        } else {
            return configuration;
        }
    }
}
