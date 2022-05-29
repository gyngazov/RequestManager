package backend.iEcp.JSON;

import backend.window.main.form.FormData;
import com.google.gson.annotations.Expose;

public final class JSONCreate extends JSONMain {
    @Expose
    private final FormData info;

    public JSONCreate(FormData info) {
        this.info = info;
    }
}
