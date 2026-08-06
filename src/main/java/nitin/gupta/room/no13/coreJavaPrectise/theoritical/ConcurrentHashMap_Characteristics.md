# Java `ConcurrentHashMap` Characteristics

## Overview

`ConcurrentHashMap` is a thread-safe implementation of the `Map` interface designed for high concurrency and scalable
performance. It allows multiple threads to read and update the map concurrently with minimal contention.

---

## Key Characteristics

### Thread-safe

- Safe for concurrent access by multiple threads.
- No need for external synchronization for most operations.

### High concurrency

- Reads are generally non-blocking.
- Updates use fine-grained synchronization and CAS (Compare-And-Swap) operations.

### No `null` keys or values

- `null` keys are **not allowed**.
- `null` values are **not allowed**.
- Attempting to insert `null` throws `NullPointerException`.

### Weakly consistent iterators

- Iterators do **not** throw `ConcurrentModificationException`.
- They reflect the state of the map at some point during or after iteration begins.
- They may or may not include modifications made during iteration.

### Atomic compound operations

Provides atomic methods such as:

- `putIfAbsent`
- `remove(key, value)`
- `replace(key, oldValue, newValue)`
- `compute`
- `computeIfAbsent`
- `computeIfPresent`
- `merge`

### Better scalability than `Hashtable`

- `Hashtable` synchronizes every method.
- `ConcurrentHashMap` allows much higher throughput under contention.

---

## Internal Behavior (Java 8+)

- Uses an array of bins (buckets).
- Bins may contain linked lists or balanced trees (red-black trees) when collisions become high.
- Resizing is performed cooperatively by multiple threads.

---

## Performance Characteristics

| Operation | Average Complexity |
|-----------|--------------------|
| `get`     | O(1)               |
| `put`     | O(1)               |
| `remove`  | O(1)               |
| iteration | O(n)               |

Under heavy hash collisions, complexity may degrade, but tree bins help maintain near O (log n) performance for affected
buckets.

---

## Memory Visibility

Actions in one thread **happen-before** subsequent successful retrievals of the same key in another thread.

Example:

```java
map.put("id",100);
```

A later successful `map.get("id")` in another thread is guaranteed to see the updated value.

---

## Common Use Cases

- Shared caches
- Frequency counters
- Session stores
- In-memory indexes
- Producer-consumer coordination maps
- Memoization

---

## Example

```java
import java.util.concurrent.ConcurrentHashMap;

ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

map.

put("A",1);

map.

compute("A",(k, v) ->v ==null?1:v +1);

        System.out.

println(map.get("A")); // 2
```

---

## Iteration Example

```java
map.forEach((k, v) ->{
        System.out.

println(k +" = "+v);
});
```

Safe to use while other threads modify the map.

---

## `ConcurrentHashMap` vs `HashMap`

| Feature              | `HashMap` | `ConcurrentHashMap` |
|----------------------|-----------|---------------------|
| Thread-safe          | No        | Yes                 |
| Allows `null` key    | Yes       | No                  |
| Allows `null` values | Yes       | No                  |
| Iterator behavior    | Fail-fast | Weakly consistent   |
| Read concurrency     | Unsafe    | High                |
| Write concurrency    | Unsafe    | High                |

---

## Important Notes

- `size()` is accurate but may be expensive under heavy concurrent updates.
- Use `mappingCount()` for large concurrent maps when an estimated long count is preferable.
- Avoid using `synchronized(map)` unless you need to coordinate multiple operations atomically.

---

## Best Practice

Use `ConcurrentHashMap` when:

- many threads read and update the map concurrently,
- high throughput is required,
- and you do not need global locking for compound actions.

For simple single-threaded use, prefer `HashMap` because it has lower overhead.
