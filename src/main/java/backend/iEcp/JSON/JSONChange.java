package backend.iEcp.JSON;

import backend.window.main.form.FormData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class JSONChange extends JSONMain {
    @Expose
    private final FormData info;
    @Expose
    @SerializedName(value = "requestId")
    private final int requestID;

    public JSONChange(FormData info, int requestID) {
        super();

        this.info = info;
        this.requestID = requestID;
    }
}
