package nitin.gupta.room.no13.utils;

import java.time.LocalDate;

public record Product(
        int prodId,
        String prodName,
        LocalDate prodCreatedDate,
        double prodRate
) {}
