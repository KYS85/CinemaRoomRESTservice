package cinema;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller implements ErrorController {

    private final int TOTAL_ROWS = 9, TOTAL_COLUMNS = 9;
    Room cinemaRoom = new Room( TOTAL_ROWS, TOTAL_COLUMNS );

    @GetMapping("/seats")  // this can be inserted in browser http://localhost:28852/seats
    public Room getAvailableSeats() {
        return cinemaRoom;
    }

    /* body for POST method in Postman
    {
    "row": 2,
    "column": 1
    }
     */
    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSeat( @RequestBody Seat seat ) {
        return cinemaRoom.purchase( seat );
    }
    /* body for POST method in Postman
    { 
    "token": "0e9138a7-0b69-44b1-9bfd-ecf5b31645e9"
    }
     */
    @PostMapping("/return")
    public ResponseEntity<?> returnSeat( @RequestBody Ticket ticket ) {
        return cinemaRoom.refund( ticket );
    }


    //http://localhost:28852/stats?password=super_secret  - request with secret code
    // in Hyperskill tests used PostMapping
    @GetMapping("/stats")  // this is to use in browser

    public ResponseEntity<?> returnStats( @RequestParam ( required = false) String password) {
        return cinemaRoom.stats( password );
    }

}
