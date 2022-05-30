package backend.iEcp.JSON;

import com.google.gson.annotations.Expose;

public final class JSONResult {
    @Expose(serialize = false)
    private String name;
    @Expose(serialize = false)
    private String contentBase64;
    @Expose(serialize = false)
    private String mimeType;
    @Expose(serialize = false)
    private int type;

    public String getName() {
        return name;
    }

    public String getContentBase64() {
        return contentBase64;
    }

    public String getMimeType() {
        return mimeType;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return "JSONResult{" +
                "name='" + name + '\'' +
                ", contentBase64='" + contentBase64 + '\'' +
                ", mimeType='" + mimeType + '\'' +
                ", type=" + type +
                '}';
    }
}
