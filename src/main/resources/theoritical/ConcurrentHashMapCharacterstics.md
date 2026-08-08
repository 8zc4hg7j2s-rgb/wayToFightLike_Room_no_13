# Java `ConcurrentHashMap`: Characteristics & Internal Mechanics

`java.util.concurrent.ConcurrentHashMap` is a thread-safe, high-performance implementation of the `ConcurrentMap`
interface introduced in Java 5. It provides full concurrency for reads and high concurrency for writes without locking
the entire table.

---

## 1. Key Characteristics

| Feature                  | `HashMap`     | `Hashtable` / `Collections.synchronizedMap` | `ConcurrentHashMap`          |
|:-------------------------|:--------------|:--------------------------------------------|:-----------------------------|
| **Thread Safety**        | ❌ No         | Yes                                         | Yes                          |
| **Locking Strategy**     | None          | Full Map Lock (`synchronized`)              | Bucket-level / Lock-striping |
| **Read Concurrency**     | High (Unsafe) | Low (Blocks on writes)                      | High (Lock-free)             |
| **Write Concurrency**    | High (Unsafe) | Low (Single writer at a time)               | High (Concurrent writers)    |
| **`null` Keys / Values** | Allowed       | ❌ Forbidden                                | ❌ **Forbidden**             |
| **Iterators**            | Fail-Fast     | Fail-Fast                                   | **Weakly Consistent**        |

---

## 2. Core Operational Rules

### No `null` Keys or Values

`ConcurrentHashMap` **disallows** `null` for both keys and values.

* **Reasoning:** In a concurrent context, if `map.get(key)` returns `null`, it creates ambiguity between whether the key
  is missing or the mapped value is explicitly `null`. In non-concurrent maps, `containsKey(key)` resolves this, but in
  concurrent environments, the state could change between the two calls.
* **Result:** Passing `null` as a key or value throws a `NullPointerException`.

### Weakly Consistent Iterators

* Iterators returned by `iterator()`, `keySet()`, `entrySet()`, and `values()` are **weakly consistent**.
* They do **not** throw `ConcurrentModificationException`.
* They reflect the state of the map at or since the time of iterator creation and may or may not reflect subsequent
  updates/deletions.

---

## 3. Internal Mechanics Evolution

### Java 7: Segmented Locking (Lock Striping)

* The map was internally partitioned into a array of **Segments** (default: 16).
* Each `Segment` extended `ReentrantLock` and acted as its own independent hash map.
* **Concurrency Level:** Up to $N$ concurrent writes where $N$ is the number of segments (typically 16).

### Java 8+: Bucket-Level CAS & Synchronized Nodes

Java 8 completely redesigned `ConcurrentHashMap` for higher concurrency and lower memory usage:

* **No Segments:** Abandoned the segment-array structure in favor of a single `Node<K,V>[]` array.
* **Lock-Free Reads:** Reads (`get()`) use volatile memory reads and require **no locks**.
* **CAS (Compare-And-Swap) for Insertions:** Inserting into an empty bucket uses atomic CAS operations without
  acquisition of traditional locks.
* **Bucket-Level Synchronized Blocks:** When a bucket already contains elements, synchronization locks **only the head
  node** of that specific bucket chain/tree.
* **Treeification:** When a bucket chain exceeds a threshold (`TREEIFY_THRESHOLD = 8`), the linked list converts into a
  Red-Black Tree (`TreeBin`), reducing lookups from $O (N)$ to $O (\log N)$.

---

## 4. Atomic Operations & Useful APIs

Standard `put()` or `get()` operations do not prevent race conditions when compound check-then-act logic is performed
outside the map. `ConcurrentHashMap` provides built-in atomic methods to handle multi-step actions safely:

```java
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();

// 1. Compute if Absent (Atomic initialization/fetch)
map.computeIfAbsent("key", k -> 42);

// 2. Put if Absent
map.putIfAbsent("key", 100);

// 3. Atomic Merge (e.g., frequency counting)
map.merge("counter", 1, Integer::sum);

// 4. Atomic Replace / Remove
map.replace("key", 42, 50); // Replaces only if current value is 42
map.remove("key", 50);       // Removes only if current value is 50