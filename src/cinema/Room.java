package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Map;

/**
 * All logic in this class
 */

public class Room {

    private int total_rows;
    private int total_columns;
    private ArrayList<Seat> available_seats = new ArrayList<>();
    @JsonIgnore
    private ArrayList<Ticket> purchasedTickets = new ArrayList<>();
    @JsonIgnore
    private final int PRICE_SWITCH = 4, PRICE_HIGH = 10, PRICE_LOW = 8;

    @JsonIgnore
    private int current_income;


    public Room( int total_rows, int total_columns ) {
        this.total_rows = total_rows;
        this.total_columns = total_columns;
        initAllSeats( total_rows, total_columns );
    }

    private void initAllSeats( int total_rows, int total_columns ) {
        for ( int i = 1; i <= total_rows; i++ ) {
            for ( int j = 1; j <= total_columns; j++ ) {
                available_seats.add( new Seat( i, j, i <= PRICE_SWITCH ? PRICE_HIGH : PRICE_LOW ) );
            }
        }
    }

    public ResponseEntity<?> purchase( Seat seat ) {

        int column = seat.getColumn();
        int row = seat.getRow();

        // validate row & column input
        if ( column > 9 || row > 9 || row < 1 || column < 1 ) {
            return new ResponseEntity<>( Map.of( "error", "The number of a row or a column is out of bounds!" )
                    , HttpStatus.BAD_REQUEST );
        }

        // check seat available + generate Ticket + purchase(remove from availableSeats array)
        for ( int i = 0; i < this.getAvailable_seats().size(); i++ ) {

            seat = this.getAvailable_seats().get( i );

            if ( seat.getRow() == row && seat.getColumn() == column ) {
                Ticket ticket = new Ticket( seat );
                plusCurrent_income(ticket.getTicket().getPrice());
                this.getPurchasedTickets().add( ticket );
                this.getAvailable_seats().remove( i );
                //return new ResponseEntity<>( ticket, HttpStatus.OK );
                return ResponseEntity.status(200)
                        .body( ticket);
            }
        }
      //
        // if not found!
        return new ResponseEntity<>( Map.of( "error", "The ticket has been already purchased!" )
                , HttpStatus.BAD_REQUEST );
    }

    public ResponseEntity<?> refund( Ticket ticket ) {

        // search for + restore available seat + remove sold ticket + minusIncome
        for ( int i = 0; i < purchasedTickets.size(); i++ ) {

            if ( purchasedTickets.get( i ).getToken().equals( ticket.getToken() ) ) {

                available_seats.add( purchasedTickets.get( i ).getTicket() );
                ReturnedTicket returned_ticket = new ReturnedTicket( purchasedTickets.get( i ).getTicket() );

                minusCurrent_income(purchasedTickets.get( i ).getTicket().getPrice());
                purchasedTickets.remove( purchasedTickets.get( i ) );


                return new ResponseEntity<>( returned_ticket, HttpStatus.OK );
            }
        }

        // if not found
        return new ResponseEntity<>( Map.of( "error", "Wrong token!" )
                , HttpStatus.BAD_REQUEST );

    }

    public ResponseEntity<?> stats( String password ) {

        if (password == null) {
            return ResponseEntity.status(401)
                    .body( Map.of("error", "The password is wrong!"));
        }

        Password passwordObj = new Password(); // create password object storing pwd value
        passwordObj.setPassword(password);
        if (passwordObj.getSuper_secret().equals(passwordObj.getPassword())) {

            Statistic statistic = new Statistic(getCurrent_income(), getAvailable_seats().size(), getPurchasedTickets().size());

            return ResponseEntity.status(200)
                    .body(statistic);  // body from class turned into json
        }

        // if wrong password
        return ResponseEntity.status(401)
                .body( Map.of("error", "The password is wrong!"));
    }


    @JsonIgnore
    public ArrayList<Ticket> getPurchasedTickets() {
        return purchasedTickets;
    }

    @JsonIgnore
    public void setPurchasedTickets( ArrayList<Ticket> purchasedTickets ) {
        this.purchasedTickets = purchasedTickets;
    }

    @JsonIgnore
    public int getPRICE_SWITCH() {
        return PRICE_SWITCH;
    }

    @JsonIgnore
    public int getHIGHER_PRICE() {
        return PRICE_HIGH;
    }

    @JsonIgnore
    public int getPRICE_LOW() {
        return PRICE_LOW;
    }

    public int getTotal_rows() {
        return total_rows;
    }

    public void setTotal_rows( int total_rows ) {
        this.total_rows = total_rows;
    }

    public int getTotal_columns() {
        return total_columns;
    }

    public void setTotal_columns( int total_columns ) {
        this.total_columns = total_columns;
    }

    public ArrayList<Seat> getAvailable_seats() {
        return available_seats;
    }

    public void setAvailable_seats( ArrayList<Seat> available_seats ) {
        this.available_seats = available_seats;
    }

    @JsonIgnore
    public int getCurrent_income() {
        return this.current_income;
    }

    @JsonIgnore
    public void plusCurrent_income(int income) {
        this.current_income += income;
    }

    @JsonIgnore
    public void minusCurrent_income(int income) {
        this.current_income -= income;
    }
}