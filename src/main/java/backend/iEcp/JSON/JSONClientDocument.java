package backend.iEcp.JSON;

public final class JSONClientDocument extends JSONView {
    private final int type;

    public JSONClientDocument(int requestID, int type) {
        super(requestID);

        this.type = type;
    }
}
