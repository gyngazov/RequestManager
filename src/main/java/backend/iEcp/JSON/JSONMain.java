package backend.iEcp.JSON;

import backend.window.settings.Options;
import com.google.gson.annotations.Expose;

public class JSONMain {
    @Expose(deserialize = false)
    private final String login = Options.read().getLogin();
    @Expose(deserialize = false)
    private final String pass = Options.read().getPassword();
}
