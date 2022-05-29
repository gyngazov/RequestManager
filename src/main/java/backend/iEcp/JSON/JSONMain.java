package backend.iEcp.JSON;

import backend.window.settings.SoftwareConfiguration;
import com.google.gson.annotations.Expose;

public class JSONMain {
    @Expose(deserialize = false)
    private final String login;
    @Expose(deserialize = false)
    private final String pass;

    public JSONMain() {
        login = SoftwareConfiguration.getInstance().getLogin();
        pass = SoftwareConfiguration.getInstance().getPassword();
    }
}
