package backend.iEcp.JSON;

import backend.window.settings.SoftwareConfiguration;
import com.google.gson.annotations.Expose;

public class JSONMain {
    @Expose(deserialize = false)
    private final String login = SoftwareConfiguration.getInstance().getLogin();
    @Expose(deserialize = false)
    private final String pass = SoftwareConfiguration.getInstance().getPassword();
}
