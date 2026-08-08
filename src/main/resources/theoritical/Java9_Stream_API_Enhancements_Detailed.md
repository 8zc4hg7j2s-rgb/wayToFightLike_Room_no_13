# Stream API Enhancements (Java 9)

## Overview

Java 9 introduced several useful additions to the Stream API that make stream pipelines easier to express and avoid
common boilerplate.

---

## 1. `takeWhile()`

### Description

`takeWhile(Predicate)` returns elements from the beginning of the stream **until the predicate becomes false**. Once a
false result is encountered, processing stops.

It works best on **ordered streams**.

### Example

```java
List<Integer> numbers = List.of(1, 2, 3, 4, 0, 5, 6);

List<Integer> result = numbers.stream()
        .takeWhile(n -> n > 0)
        .toList();

System.out.

println(result);
```

**Output**

```text
[1, 2, 3, 4]
```

### Before Java 9

```java
List<Integer> result = new ArrayList<>();
for(
int n :numbers){
        if(n <=0)break;
        result.

add(n);
}
```

### Use Cases

- Read data until a delimiter
- Stop processing at the first invalid record
- Prefix extraction

---

## 2. `dropWhile()`

### Description

`dropWhile(Predicate)` skips elements while the predicate is true, then returns the remaining elements.

### Example

```java
List<Integer> numbers = List.of(1, 2, 3, 4, 0, 5, 6);

List<Integer> result = numbers.stream()
        .dropWhile(n -> n > 0)
        .toList();
```

**Output**

```text
[0, 5, 6]
```

### Use Cases

- Skip headers
- Ignore leading whitespace or comments
- Resume processing after a marker

---

## 3. `Stream.iterate(seed, predicate, next)`

### Description

Java 8:

```java
Stream.iterate(1,n ->n +1)
```

creates an **infinite stream**.

Java 9 added an overload with a predicate to create a **finite stream**.

### Syntax

```java
Stream.iterate(seed, predicate, nextFunction)
```

### Example

```java
Stream<Integer> stream =
        Stream.iterate(1, n -> n <= 10, n -> n + 1);

stream.

forEach(System.out::println);
```

**Output**

```text
1
2
3
4
5
6
7
8
9
10
```

### Benefits

- Avoids `limit()`
- More readable
- Natural termination condition

---

## 4. `Stream.ofNullable()`

### Description

Creates a stream containing one element if the value is non-null; otherwise it creates an empty stream.

### Example (Non-null)

```java
Stream.ofNullable("Java")
      .

forEach(System.out::println);
```

Output

```text
Java
```

### Example (Null)

```java
Stream.ofNullable(null)
      .

forEach(System.out::println);
```

Output

```text
(no output)
```

### Before Java 9

```java
if(value !=null){
        Stream.

of(value);
}else{
        Stream.

empty();
}
```

### Use Cases

- Null-safe stream creation
- Optional pipelines
- Cleaner APIs

---

# Comparison

| Feature                          | Purpose                        | Stops Early | Java Version |
|----------------------------------|--------------------------------|-------------|--------------|
| `takeWhile()`                    | Take leading matching elements | Yes         | Java 9       |
| `dropWhile()`                    | Skip leading matching elements | Yes         | Java 9       |
| `iterate(seed, predicate, next)` | Finite stream generation       | Yes         | Java 9       |
| `ofNullable()`                   | Null-safe stream creation      | N/A         | Java 9       |

# Summary

These Java 9 Stream enhancements reduce boilerplate and make pipelines more expressive:

- `takeWhile()` extracts a matching prefix.
- `dropWhile()` skips a matching prefix.
- `iterate(seed, predicate, next)` creates finite streams naturally.
- `ofNullable()` eliminates explicit null checks when creating streams.
