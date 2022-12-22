package cinema;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller implements ErrorController {

    private final int TOTAL_ROWS = 9, TOTAL_COLUMNS = 9;
    Room cinemaRoom = new Room( TOTAL_ROWS, TOTAL_COLUMNS );

    @GetMapping("/seats")
    public Room getAvailableSeats() {
        return cinemaRoom;
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSeat( @RequestBody Seat seat ) {
        return cinemaRoom.purchase( seat );
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnSeat( @RequestBody Ticket ticket ) {
        return cinemaRoom.refund( ticket );
    }
    
    // to pass tests reset Mapping
    @GetMapping("/stats"). // in tests used PostMapping
    public ResponseEntity<?> returnStats( @RequestParam ( required = false) String password) {
        return cinemaRoom.stats( password );
    }

}
