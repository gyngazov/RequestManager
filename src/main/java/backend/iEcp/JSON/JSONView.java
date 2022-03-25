package backend.iEcp.JSON;

import com.google.gson.annotations.Expose;

public final class JSONView extends JSONMain {
    private final int requestId;

    public JSONView(int requestId) {
        super();
        this.requestId = requestId;
    }

    public JSONView(String requestId) throws NumberFormatException {
        this(Integer.parseInt(requestId));
    }
}
