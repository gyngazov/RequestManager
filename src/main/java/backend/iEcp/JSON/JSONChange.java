package backend.iEcp.JSON;

import backend.window.main.form.FormData;

public final class JSONChange extends JSONMain {
    private final int requestId;
    private final FormData info;

    public JSONChange(int requestId, FormData info) {
        super();

        this.requestId = requestId;
        this.info = info;
    }
}
