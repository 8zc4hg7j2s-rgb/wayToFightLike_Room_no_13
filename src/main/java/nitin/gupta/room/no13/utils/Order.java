package nitin.gupta.room.no13.utils;

import java.time.LocalDate;

public record Order(
        int order_id,
        LocalDate order_date,
        int product_id,
        int quantity,
        String customer_email
) {
}
