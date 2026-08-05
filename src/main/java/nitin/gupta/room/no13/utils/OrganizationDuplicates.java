package nitin.gupta.room.no13.utils;

import java.time.LocalDateTime;

public record OrganizationDuplicates(
        int index,
        String organizationId,
        String name,
        String Website,
        String country,
        String description,
        String founded,
        String industry,
        String  numberOfEmployees,
        LocalDateTime UpdatedAt) {
}
