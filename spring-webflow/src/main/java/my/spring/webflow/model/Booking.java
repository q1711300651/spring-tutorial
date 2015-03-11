package my.spring.webflow.model;

public class Booking {

    private String clientName;
    private String creditCard;
    private String creditCardName;
    private int creditCardExpiryMonth;
    private int creditCardExpiryYear;

    public String getClientName() {
        return clientName;
    }

    public void setClientName( String clientName ) {
        this.clientName = clientName;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard( String creditCard ) {
        this.creditCard = creditCard;
    }

    public String getCreditCardName() {
        return creditCardName;
    }

    public void setCreditCardName( String creditCardName ) {
        this.creditCardName = creditCardName;
    }

    public int getCreditCardExpiryMonth() {
        return creditCardExpiryMonth;
    }

    public void setCreditCardExpiryMonth( int creditCardExpiryMonth ) {
        this.creditCardExpiryMonth = creditCardExpiryMonth;
    }

    public int getCreditCardExpiryYear() {
        return creditCardExpiryYear;
    }

    public void setCreditCardExpiryYear( int creditCardExpiryYear ) {
        this.creditCardExpiryYear = creditCardExpiryYear;
    }
}
