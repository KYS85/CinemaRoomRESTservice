package cinema;

import java.util.UUID;

class Ticket {

    private UUID token;
    private Seat ticket;

    public Ticket() {
    }

    public Ticket( Seat ticket ) {
        this.token = UUID.randomUUID();
        this.ticket = ticket;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken( UUID token ) {
        this.token = token;
    }

    public Seat getTicket() {
        return ticket;
    }

    public void setTicket( Seat ticket ) {
        this.ticket = ticket;
    }
}

class ReturnedTicket {

    private Seat returned_ticket;

    public ReturnedTicket( Seat returned_ticket ) {
        this.returned_ticket = returned_ticket;
    }

    public Seat getReturned_ticket() {
        return returned_ticket;
    }

    public void setReturned_ticket( Seat returned_ticket ) {
        this.returned_ticket = returned_ticket;
    }
}