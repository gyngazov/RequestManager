package backend.iEcp.JSON;

import com.google.gson.annotations.SerializedName;

public final class JSONAttachFile extends JSONMain {
    private final String fileName;
    private final String file;
    private final int fileType;
    @SerializedName(value = "requestId")
    private final int requestID;


    public JSONAttachFile(String fileName,
                          String file,
                          int fileType,
                          int requestID) {
        super();

        this.fileName = fileName;
        this.file = file;
        this.fileType = fileType;
        this.requestID = requestID;
    }
}
