package backend.iEcp.JSON;

import backend.window.settings.Options;
import com.google.gson.annotations.Expose;

public class JSONMain {
    private final String login = Options.read().getLogin();
    private final String pass = Options.read().getPassword();
}
