package food.rest.code.endpoints;

public class SaveBookingRequest {
    private long bookingId;
    private String ticketId;
    private String foodName;
    private String description;

    public SaveBookingRequest() {
    }

    public SaveBookingRequest(long bookingId, String ticketId, String foodName, String description) {
        this.bookingId = bookingId;
        this.ticketId = ticketId;
        this.foodName = foodName;
        this.description = description;
    }

    public long getBookingId() {
        return bookingId;
    }

    public void setBookingId(long bookingId) {
        this.bookingId = bookingId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SaveBookingRequest{" +
                "bookingId=" + bookingId +
                ", ticketId='" + ticketId + '\'' +
                ", foodName='" + foodName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
