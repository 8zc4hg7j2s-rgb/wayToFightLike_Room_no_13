# Java Feature Details

## Stream API Enhancements (Java 9)

### `takeWhile()`

Returns elements from the start of the stream while the predicate is true.

```java
List.of(1,2,3,0,4).

stream().

takeWhile(n ->n >0).

toList(); // [1,2,3]
```

### `dropWhile()`

Skips leading elements while the predicate is true, then returns the rest.

```java
List.of(1,2,3,0,4).

stream().

dropWhile(n ->n >0).

toList(); // [0,4]
```

### `iterate(seed, predicate, next)`

Finite stream generation.

```java
Stream.iterate(1,n ->n <=5,n ->n +1);
```

### `Stream.ofNullable()`

Creates an empty stream if the value is `null`.

```java
Stream.ofNullable(user).

forEach(System.out::println);
```

---

## Records (Preview → Final)

Introduced as preview in Java 14 and finalized in Java 16.

Records are immutable data carriers that automatically generate constructors, getters, `equals()`, `hashCode()`, and
`toString()`.

```java
record Employee(String name, int age) {
}
```

Benefits:

- Less boilerplate
- Immutable by default
- Better readability

---

## Pattern Matching for `instanceof`

Preview in Java 14, finalized in Java 16.

Before:

```java
if(obj instanceof String){
String s = (String) obj;
}
```

After:

```java
if(obj instanceof
String s){
        System.out.

println(s.length());
        }
```

Benefits:

- Eliminates explicit casts
- Safer and cleaner code

---

## Switch Expressions

Previewed in Java 12/13 and finalized in Java 14.

```java
String type = switch (day) {
    case SATURDAY, SUNDAY -> "Weekend";
    default -> "Weekday";
};
```

Benefits:

- Returns values
- No accidental fall-through
- More concise syntax

---

## Text Blocks

Preview in Java 13/14, finalized in Java 15.

```java
String json = 