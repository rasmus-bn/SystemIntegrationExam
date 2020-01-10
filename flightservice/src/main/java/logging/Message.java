package logging;

public class Message{
    private String severity;
    private String description;
    private String message;

    public Message(String severity, String description, String message) {
        this.severity = severity;
        this.description = description;
        this.message = message;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}