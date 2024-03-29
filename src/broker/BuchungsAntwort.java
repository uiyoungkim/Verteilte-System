package broker;

public class BuchungsAntwort {
    private String id;
    private boolean isSuccess;
    private String message;
    public BuchungsAntwort(String id, boolean isSuccess, String message) {
        this.id = id;
        this.isSuccess = isSuccess;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
