package nitin.gupta.room.no13.utils;

import java.time.LocalDate;

public record Person(
        int index,
        String userId,
        String firstName,
        String lastName,
        String sex,
        String email,
        String phone,
        LocalDate dateOfBirth,
        String jobTitle
) {}