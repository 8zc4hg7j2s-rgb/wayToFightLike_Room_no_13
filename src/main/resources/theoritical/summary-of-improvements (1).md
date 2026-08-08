# Summary of Improvements — Java `User` Record

| Issue                            | Fix                                                       |
|----------------------------------|-----------------------------------------------------------|
| Mutable list breaks immutability | `List.copyOf(account)` in compact constructor             |
| No validation                    | Add checks in compact constructor                         |
| No null-safety                   | `Objects.requireNonNull()` / `List.copyOf` throws on null |
| Repetitive construction          | Add overloaded constructor for defaults                   |
