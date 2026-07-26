package nitin.gupta.room.no13.utils;

import java.util.List;
/**
 * Why this matters:
 *
 * <li>List.copyOf() creates an unmodifiable copy, so the internal list can't be mutated after construction — even if the caller keeps a reference to the original list and modifies it later.</li>
 * <li>Throws NullPointerException automatically if account is null or contains null elements (fail-fast).</li>
 * <li>Without this, two different User objects could share the same backing list, causing subtle bugs.</li>
 */

public record User(
        int id,
        String  name,
        int age,
        double salary,
        List<Integer> account
) {
    // Compact constructor — runs before field assignment
    public User {
        account = List.copyOf(account); // immutable + defensive copy
    }
}

