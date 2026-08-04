package nitin.gupta.room.no13.utils;

import java.time.LocalDate;

public record Product(
        int product_id,
        String product_name,
        String category,
        double price,
        int stock_qty
) {}
