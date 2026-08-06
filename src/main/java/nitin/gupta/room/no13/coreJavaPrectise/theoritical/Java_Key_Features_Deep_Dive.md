# Java Key Features — Deep Dive

### Detailed Descriptions, Code, and Before/After Comparisons

This document covers the following features in detail, in the order they were introduced:

1. Stream API Enhancements — `takeWhile`, `dropWhile`, `iterate`, `ofNullable`
2. Records (Preview)
3. Pattern Matching for `instanceof` (Preview)
4. Switch Expressions (Final)
5. Text Blocks (Final)
6. Sealed Classes
7. Vector API
8. Virtual Threads (Preview)
9. Structured Concurrency (Incubator)
10. Virtual Threads (Final)
11. Stream Gatherers (Final)
12. Strongly Encapsulate JDK Internals by Default
13. Structured Concurrency (later previews)
14. Class-File API (Final)

---

## 1. Stream API Enhancements — `takeWhile`, `dropWhile`, `iterate`, `ofNullable`

**Introduced:** Java 9 (JEP 269 related work, delivered as Stream API additions)

Java 8's Stream API was powerful but missing a few common operations that developers had to fake with workarounds. Java
9 filled these gaps.

### `takeWhile(Predicate)`

Takes elements from the stream **as long as** the predicate is true, then stops at the first element that fails — even
if later elements would also pass. This is different from `filter()`, which checks *every* element.

```java
List<Integer> numbers = List.of(1, 2, 3, 4, 5, 1, 2);

List<Integer> result = numbers.stream()
    .takeWhile(n -> n < 4)
    .collect(Collectors.toList());
// Result: [1, 2, 3]  -- stops at 4, ignores the trailing 1, 2 even though they're < 4
```

**Before Java 9**, you'd need a manual loop or a stateful hack with `AtomicBoolean` to achieve early termination based
on order.

### `dropWhile(Predicate)`

The mirror image of `takeWhile` — **skips** elements while the predicate is true, then takes everything remaining once
the predicate first fails.

```java
List<Integer> result = numbers.stream()
    .dropWhile(n -> n < 4)
    .collect(Collectors.toList());
// Result: [4, 5, 1, 2]  -- drops leading 1,2,3, keeps the rest as-is
```

### `Stream.iterate(seed, predicate, next)` — 3-argument overload

Java 8's `iterate(seed, UnaryOperator)` produced an **infinite** stream that required an external `limit()` to stop.
Java 9 added a version with a built-in **has-next** condition, similar to a classic `for` loop.

```java
// Java 8 style — needs limit() or it never terminates
Stream.iterate(1, n -> n * 2).limit(5).forEach(System.out::println);

// Java 9 style — self-terminating, reads like: for (int i=1; i<50; i *= 2)
Stream.iterate(1, n -> n < 50, n -> n * 2)
      .forEach(System.out::println);
// Output: 1 2 4 8 16 32
```

### `Stream.ofNullable(T)`

Creates a stream of **zero or one** element depending on whether the argument is `null`. Useful for cleanly folding a
possibly-null value into a stream pipeline without an explicit `if`.

```java
String value = getValueOrNull();

// Before: manual null check needed
Stream<String> s = (value == null) ? Stream.empty() : Stream.of(value);

// After: one line
Stream<String> s = Stream.ofNullable(value);

// Practical use: flatMap over a collection of possibly-null values
List<String> results = rawList.stream()
    .flatMap(v -> Stream.ofNullable(v))
    .collect(Collectors.toList());
```

**Why it matters:** These four additions removed common boilerplate/workarounds and made Streams more expressive for
real-world data-cleaning and pagination-style logic, without changing the core Stream API contract from Java 8.

---

## 2. Records (Preview)

**Introduced:** Java 14 (JEP 359, Preview) → finalized in Java 16

Records are a special kind of class designed to be transparent, immutable data carriers — eliminating the boilerplate of
writing constructors, getters, `equals()`, `hashCode()`, and `toString()` by hand.

### Before Records (traditional POJO)

```java
public final class Point {
    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() { return x; }
    public int getY() { return y; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point p = (Point) o;
        return x == p.x && y == p.y;
    }
    @Override
    public int hashCode() { return Objects.hash(x, y); }
    @Override
    public String toString() { return "Point[x=" + x + ", y=" + y + "]"; }
}
```

~25 lines for a class that just holds two numbers.

### After Records

```java
record Point(int x, int y) { }
```

**One line.** The compiler automatically generates:

- A canonical constructor `Point(int x, int y)`
- Accessor methods `x()` and `y()` (note: **not** `getX()` — records use the field name directly)
- `equals()`, `hashCode()`, and `toString()` based on all fields
- The class is implicitly `final` and immutable (all fields are `private final`)

### Customizing a record

You can add validation via a **compact constructor**:

```java
record Point(int x, int y) {
    Point {  // compact constructor — no parameter list repeated
        if (x < 0 || y < 0) {
            throw new IllegalArgumentException("Coordinates must be non-negative");
        }
    }
}
```

You can also add extra methods, static fields, and static factory methods:

```java
record Range(int start, int end) {
    static Range of(int start, int end) { return new Range(start, end); }
    int length() { return end - start; }
}
```

**Why it matters:** Records dramatically reduce boilerplate for DTOs, value objects, and API responses — a pattern
previously handled by libraries like Lombok. They also pair naturally with pattern matching (see Record Patterns in Java
21).

---

## 3. Pattern Matching for `instanceof` (Preview)

**Introduced:** Java 14 (JEP 305, Preview) → finalized in Java 16

Eliminates the redundant cast that traditionally followed an `instanceof` check.

### Before

```java
if (obj instanceof String) {
    String s = (String) obj;   // manual, redundant cast
    System.out.println(s.length());
}
```

### After

```java
if (obj instanceof String s) {   // 's' is automatically bound and cast
    System.out.println(s.length());
}
```

The pattern variable `s` is only in scope where the compiler can prove `obj` is definitely a `String` — including in
flow-sensitive contexts:

```java
if (!(obj instanceof String s)) {
    return;
}
// 's' is still usable here, because if we reach this line, obj WAS a String
System.out.println(s.length());
```

You can also combine the pattern with further boolean logic:

```java
if (obj instanceof String s && s.length() > 5) {
    System.out.println("Long string: " + s);
}
```

**Why it matters:** This is the foundation of Java's broader "pattern matching" initiative (Project Amber), later
extended to `switch` and to deconstructing records (Java 21's record patterns).

---

## 4. Switch Expressions (Final)

**Introduced:** Java 12 (JEP 325, Preview) → finalized in Java 14 (JEP 361)

Turns `switch` from a purely imperative *statement* into something that can also be used as an *expression* that
produces a value — with a safer, more concise syntax.

### Before (traditional switch statement)

```java
int numLetters;
switch (day) {
    case MONDAY:
    case FRIDAY:
    case SUNDAY:
        numLetters = 6;
        break;
    case TUESDAY:
        numLetters = 7;
        break;
    default:
        numLetters = 0;
}
```

Problems: verbose, `break` is easy to forget (fall-through bugs), and it can't be assigned directly to a variable.

### After (switch expression, arrow syntax)

```java
int numLetters = switch (day) {
    case MONDAY, FRIDAY, SUNDAY -> 6;
    case TUESDAY                -> 7;
    default                     -> 0;
};
```

- No fall-through — each arm is self-contained.
- Multiple labels can share one arm (comma-separated).
- Compiler enforces **exhaustiveness** — if you switch over an `enum` and cover all cases, you don't even need a
  `default`.

### Using `yield` for multi-statement arms

```java
int result = switch (day) {
    case MONDAY, FRIDAY, SUNDAY -> 6;
    case TUESDAY -> {
        int base = 7;
        yield base + 1;   // yield returns a value from a block arm
    }
    default -> 0;
};
```

**Why it matters:** Removes an entire class of fall-through bugs, makes intent clearer, and enables `switch` to be used
as pattern-matching expression later on (Java 21).

---

## 5. Text Blocks (Final)

**Introduced:** Java 13 (JEP 355, Preview) → finalized in Java 15 (JEP 378)

Multi-line string literals that avoid the mess of `\n` and `+` concatenation, especially for embedded JSON, HTML, SQL,
etc.

### Before

```java
String json = "{\n" +
              "  \"name\": \"John\",\n" +
              "  \"age\": 30\n" +
              "}\n";
```

### After

```java
String json = """
    {
      "name": "John",
      "age": 30
    }
    """;
```

- Delimited by triple double-quotes (`"""`).
- The compiler strips "incidental" leading whitespace based on the least-indented line, so you can format the block
  naturally within your code's indentation.
- Trailing whitespace on each line is stripped by default (use `\s` to preserve an intentional trailing space).
- Supports the usual escape sequences and new ones like `\` (line continuation, suppresses the newline) and `\s`
  (explicit space).

```java
String html = """
    <html>
        <body>
            <p>Hello, World!</p>
        </body>
    </html>
    """;
```

**Why it matters:** Massively improves readability for embedded structured text (SQL queries, JSON payloads, HTML
fragments) — a very common pain point in real-world code.

---

## 6. Sealed Classes

**Introduced:** Java 15 (JEP 360, Preview) → finalized in Java 17 (JEP 409)

Sealed classes/interfaces let you **restrict** which other classes or interfaces may extend or implement them — giving
you the safety of a "closed" type hierarchy, something Java previously couldn't express (only `final`, which disallowed
*any* subclassing, or open inheritance, which allowed *unlimited* subclassing).

### Declaring a sealed hierarchy

```java
public sealed interface Shape
    permits Circle, Square, Triangle { }

public final class Circle implements Shape {
    public final double radius;
    public Circle(double radius) { this.radius = radius; }
}

public final class Square implements Shape {
    public final double side;
    public Square(double side) { this.side = side; }
}

public non-sealed class Triangle implements Shape {
    // 'non-sealed' re-opens this branch for further unrestricted extension
}
```

Every permitted subclass must specify exactly one of:

- `final` — cannot be extended further
- `sealed` — can be extended, but only by classes it explicitly permits
- `non-sealed` — reopens unrestricted extension from this point on

### Why this matters for `switch`

Sealed classes enable the compiler to verify **exhaustiveness** in pattern-matching switches — because the compiler
knows the *complete* list of possible subtypes at compile time:

```java
double area = switch (shape) {
    case Circle c   -> Math.PI * c.radius * c.radius;
    case Square s   -> s.side * s.side;
    case Triangle t -> 0.5 * t.base * t.height;
    // no 'default' needed -- compiler knows these are the ONLY possibilities
};
```

**Why it matters:** Sealed classes bring the safety of algebraic data types (common in functional languages like
Scala/Kotlin) to Java, enabling much safer domain modeling and exhaustive pattern matching without runtime surprises
from unknown subclasses.

---

## 7. Vector API

**Introduced:** Java 16 (JEP 338, Incubator) — still incubating as of Java 25 (10th round, JEP 508)

The Vector API allows developers to express vector (SIMD — Single Instruction, Multiple Data) computations that reliably
compile to optimal hardware instructions on supported CPUs, at runtime, across platforms.

### The problem it solves

Traditionally, achieving SIMD-level performance in Java required either:

- Hoping the JIT compiler auto-vectorizes your loop (unreliable, opaque), or
- Writing native code via JNI and losing Java's "write once, run anywhere" portability.

### Example

```java
import jdk.incubator.vector.*;

static final VectorSpecies<Float> SPECIES = FloatVector.SPECIES_PREFERRED;

void vectorAdd(float[] a, float[] b, float[] result) {
    int i = 0;
    int upperBound = SPECIES.loopBound(a.length);
    for (; i < upperBound; i += SPECIES.length()) {
        var va = FloatVector.fromArray(SPECIES, a, i);
        var vb = FloatVector.fromArray(SPECIES, b, i);
        var vc = va.add(vb);
        vc.intoArray(result, i);
    }
    // handle the remaining tail elements normally
    for (; i < a.length; i++) {
        result[i] = a[i] + b[i];
    }
}
```

Instead of adding one `float` at a time, this processes a whole "lane" of floats (e.g., 4, 8, or 16 at once, depending
on CPU support) in a single instruction.

**Why it matters:** Critical for performance-sensitive domains — machine learning inference, image/audio processing,
scientific computing, financial simulations — where Java previously lagged behind C/C++/Fortran. It remains an incubator
API because the JEP authors are still refining it against evolving hardware (AVX-512, ARM SVE, etc.).

---

## 8. Virtual Threads (Preview)

**Introduced:** Java 19 (JEP 425, Preview) → finalized in Java 21 (JEP 444)

Part of **Project Loom**. Virtual threads are lightweight threads implemented by the JDK (not the OS), designed to
dramatically increase the throughput of concurrent applications written in the familiar thread-per-request style.

### The problem: platform threads are expensive

Traditional Java threads (`Thread`) map 1:1 to OS threads, which are expensive to create (megabytes of stack memory
each) and expensive to schedule. This is why high-throughput servers moved to reactive/async frameworks (e.g., Netty,
WebFlux) — to avoid needing one OS thread per concurrent request. But reactive code is notoriously harder to write,
read, and debug.

### The solution: virtual threads

Virtual threads are cheap (a few hundred bytes, not megabytes), and the JVM can run **millions** of them, multiplexed
onto a small pool of OS "carrier" threads. Crucially, they use the exact same `Thread` API you already know.

```java
// Before Loom: thread-per-request with platform threads doesn't scale past
// a few thousand concurrent connections

// With virtual threads:
try(ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()){
        for(
int i = 0;
i< 100_000;i++){
        executor.

submit(() ->{
String result = callSlowNetworkService(); // blocking call is FINE
            System.out.

println(result);
        });
                }
                } // executor.close() waits for all tasks to finish
```

The key insight: when a virtual thread performs a **blocking** operation (I/O, `Thread.sleep()`, etc.), the JVM
automatically "unmounts" it from its carrier OS thread, freeing that OS thread to run other virtual threads. When the
blocking operation completes, the virtual thread is "remounted" to continue. All of this happens transparently — you
write plain old blocking, sequential code.

```java
// Creating a single virtual thread directly
Thread vt = Thread.ofVirtual().start(() -> {
    System.out.println("Running in: " + Thread.currentThread());
});
```

**Why it matters:** Lets developers write simple, blocking, imperative-style code (easy to read, debug, and profile)
while achieving throughput previously reachable only with complex reactive/async programming models.

---

## 9. Structured Concurrency (Incubator)

**Introduced:** Java 19 (JEP 428, Incubator) — continued through many preview rounds; still previewing as of Java 25
(JEP 505, 5th preview)

Also part of Project Loom. Structured concurrency treats a group of related subtasks running in different threads as a
**single unit of work** — with a clear parent/child lifecycle, unified error handling, and automatic cancellation
propagation.

### The problem it solves

With traditional `ExecutorService`/`Future`-based concurrency, if one subtask fails, other sibling subtasks keep running
unless you manually track and cancel them — a common source of resource leaks and orphaned threads.

### Example

```java
try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
    Future<String> user  = scope.fork(() -> fetchUser());
    Future<String> order = scope.fork(() -> fetchOrder());

    scope.join();           // wait for both subtasks
    scope.throwIfFailed();  // propagate any exception

    // Both succeeded — safe to use results
    process(user.resultNow(), order.resultNow());
}
// If EITHER subtask fails, the other is automatically cancelled,
// and the scope's owner thread gets the exception -- no orphaned threads.
```

**Why it matters:** Fork-join style concurrent code gets the same safety guarantees as single-threaded code: if a scope
(like a try block) exits, all the work it spawned is guaranteed to be finished — either successfully, cancelled, or
failed — with no leaks and a clear call stack for debugging (unlike scattered reactive callback chains).

---

## 10. Virtual Threads (Final)

**Introduced:** Java 21 (JEP 444, Final/Standard)

After two rounds of preview (Java 19, Java 20), virtual threads became a permanent, stable feature in Java 21 with no
more `--enable-preview` flag required.

### What changed since preview

- API stabilized: `Thread.ofVirtual()`, `Thread.ofPlatform()`, `Executors.newVirtualThreadPerTaskExecutor()`.
- Full integration with `synchronized` blocks improved over subsequent releases (pinning issues reduced).
- Tooling support matured: JFR events, thread dumps (`jcmd Thread.dump_to_file`), and debuggers all understand virtual
  threads distinctly from platform threads.
- Frameworks rapidly adopted it: Spring Boot 3.2+ added a one-line config flag (`spring.threads.virtual.enabled=true`)
  to run the entire MVC stack on virtual threads.

```java
// Production-ready usage as of Java 21+
@RestController
class OrderController {
    @GetMapping("/orders/{id}")
    Order getOrder(@PathVariable String id) {
        // If Spring is configured for virtual threads, this blocking call
        // no longer ties up a scarce platform thread from the web server's pool.
        return orderRepository.findById(id).block();
    }
}
```

**Why it matters:** This is widely considered **the** landmark feature of Java 21 — it fundamentally changes the default
advice for building scalable Java server applications, closing much of the throughput gap with async frameworks while
keeping code simple.

---

## 11. Stream Gatherers (Final)

**Introduced:** Java 22 (JEP 461, Preview) → 2nd preview in Java 23 (JEP 473) → finalized in Java 24 (JEP 485)

Stream Gatherers let you define **custom intermediate operations** for streams — something previously impossible without
dropping into the low-level `Spliterator`/`Collector` internals. Standard streams only ship with a fixed set of
intermediate ops (`map`, `filter`, `sorted`, etc.); Gatherers let you write your own.

### Example: a custom "sliding window" gatherer

```java
List<List<Integer>> windows = Stream.of(1, 2, 3, 4, 5)
    .gather(Gatherers.windowSliding(2))
    .toList();
// Result: [[1, 2], [2, 3], [3, 4], [4, 5]]
```

### Example: `Gatherers.fold` (stateful, ordered reduction, unlike `reduce`)

```java
Optional<String> concatenated = Stream.of("a", "b", "c")
    .gather(Gatherers.fold(() -> "", (acc, elem) -> acc + elem))
    .findFirst();
// Result: Optional["abc"]
```

### Writing a fully custom gatherer

```java
// A gatherer that only emits every 2nd element
Gatherer<Integer, ?, Integer> everyOther = Gatherer.ofSequential(
    () -> new int[]{0},                          // initializer (state)
    (state, element, downstream) -> {             // integrator
        state[0]++;
        if (state[0] % 2 == 0) downstream.push(element);
        return true;
    }
);

List<Integer> result = Stream.of(1, 2, 3, 4, 5, 6)
    .gather(everyOther)
    .toList();
// Result: [2, 4, 6]
```

**Why it matters:** Before Gatherers, doing anything stateful or order-sensitive mid-stream (windowing, deduplication
with custom rules, running totals) required breaking out of the Stream API entirely. Gatherers close this long-standing
gap and make Streams composable for a much wider range of real-world data-processing patterns.

---

## 12. Strongly Encapsulate JDK Internals by Default

**Introduced:** Java 16 (JEP 396, opt-in warning) → enforced by default in Java 17 (JEP 403)

Since Java 9's module system, internal JDK packages (like `sun.misc`, `com.sun.*`) were technically encapsulated — but
for backward compatibility, the JVM still allowed illegal reflective access by default, just with a warning
(`WARNING: An illegal reflective access operation has occurred`).

### Before Java 17

```bash
# This worked (with a warning) if code used reflection on internal JDK classes
java -jar legacy-library-using-sun-misc-unsafe.jar
```

### From Java 17 onward

```bash
# This now FAILS by default with an InaccessibleObjectException
java -jar legacy-library-using-sun-misc-unsafe.jar

# You must explicitly opt back in per-module if truly necessary:
java --add-opens java.base/sun.misc=ALL-UNNAMED -jar legacy-library.jar
```

The old escape-hatch flag `--illegal-access=permit` (which quietly allowed everything) was **removed entirely** in Java
17 — there's no longer a "just make the warnings go away" global switch.

**Why it matters:** This was one of the most impactful *breaking* changes in the Java 8→17 migration path — many older
libraries (some ORMs, some testing/mocking frameworks, performance libraries relying on `sun.misc.Unsafe`) needed
updates before they could run cleanly on Java 17+. It's a deliberate trade-off: short-term migration pain for long-term
JDK maintainability, since the JDK team can no longer be blocked from refactoring internals by third-party code secretly
depending on them.

---

## 13. Structured Concurrency (Continued Evolution)

**Preview history:** Java 19 (JEP 428) → Java 20 (JEP 437, 2nd) → Java 21 (JEP 453, 3rd) → Java 22 (JEP 462, 4th) → Java
23 (JEP 480, 5th... renumbered) → Java 24 (JEP 499) → Java 25 (JEP 505)

Structured Concurrency has had an unusually long preview cycle (still not final as of Java 25) because the API design
has been actively refined based on developer feedback across many releases.

### Notable refinements across versions

- **Java 21:** Introduced `StructuredTaskScope.ShutdownOnSuccess` (race the first successful result) alongside
  `ShutdownOnFailure`.
- **Java 22–23:** API simplified — custom scope subclassing patterns were streamlined.
- **Java 24–25:** Continued alignment with the finalized Scoped Values API (Java 25) since the two features are designed
  to work hand-in-hand — scoped values are typically used to pass context (like a request ID or auth token) down into
  structured concurrency subtasks safely.

```java
// A Java 25-era example combining Scoped Values + Structured Concurrency
final static ScopedValue<String> REQUEST_ID = ScopedValue.newInstance();

void handleRequest(String id) throws Exception {
    ScopedValue.where(REQUEST_ID, id).run(() -> {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            var userTask  = scope.fork(() -> fetchUser(REQUEST_ID.get()));
            var orderTask = scope.fork(() -> fetchOrder(REQUEST_ID.get()));
            scope.join().throwIfFailed();
            combine(userTask.resultNow(), orderTask.resultNow());
        }
    });
}
```

**Why it matters:** The extended preview period reflects how seriously the OpenJDK team treats concurrency-API design —
getting it wrong would lock in bad patterns for a decade. Expect finalization in an upcoming release once the design
fully stabilizes.

---

## 14. Class-File API (Final)

**Introduced:** Java 22 (JEP 457, Preview) → 2nd preview in Java 23 (JEP 466) → finalized in Java 24 (JEP 484)

A standard API for parsing, generating, and transforming Java class files (`.class`), replacing the JDK's historical
practice of bundling a *shaded/repackaged* copy of the third-party **ASM** bytecode library internally.

### The problem it solves

For years, tools that manipulate bytecode (frameworks doing runtime code generation, static analysis tools, bytecode
instrumentation for APM/profiling tools) relied on external libraries like ASM. But the JDK itself *also* needed such a
library internally (for things like generating lambda classes at runtime) and bundled a private, renamed copy of ASM —
which had to be updated with every new class-file version, creating an awkward maintenance burden and version-skew risk
for anyone using an external ASM version that didn't yet understand the newest bytecode features.

### Example: reading a class file

```java
import java.lang.classfile.*;
import java.nio.file.*;

ClassModel classModel = ClassFile.of().parse(Path.of("MyClass.class"));

for (MethodModel method : classModel.methods()) {
    System.out.println("Method: " + method.methodName().stringValue());
}
```

### Example: transforming a class file

```java
byte[] transformedBytes = ClassFile.of().transformClass(classModel,
    ClassTransform.transformingMethods(
        methodTransform -> true, // apply to all methods
        MethodTransform.dropping(codeElement -> false) // example filter
    )
);
```

**Why it matters:** Gives the wider Java ecosystem (frameworks, APM tools, bytecode-manipulation libraries) an official,
always-up-to-date, JDK-maintained way to work with class files — instead of every tool vendor independently tracking
bytecode format changes via ASM updates. It also lets the JDK team retire their internal forked ASM copy, simplifying
JDK maintenance.

---

## Summary Table

| #  | Feature                                                | Preview Version | Final Version              |
|----|--------------------------------------------------------|-----------------|----------------------------|
| 1  | Stream: `takeWhile`/`dropWhile`/`iterate`/`ofNullable` | —               | Java 9                     |
| 2  | Records                                                | Java 14         | Java 16                    |
| 3  | Pattern Matching for `instanceof`                      | Java 14         | Java 16                    |
| 4  | Switch Expressions                                     | Java 12         | Java 14                    |
| 5  | Text Blocks                                            | Java 13         | Java 15                    |
| 6  | Sealed Classes                                         | Java 15         | Java 17                    |
| 7  | Vector API                                             | Java 16         | Still incubating (Java 25) |
| 8  | Virtual Threads                                        | Java 19         | Java 21                    |
| 9  | Structured Concurrency                                 | Java 19         | Still previewing (Java 25) |
| 10 | Stream Gatherers                                       | Java 22         | Java 24                    |
| 11 | Strongly Encapsulate JDK Internals                     | Java 16 (warn)  | Java 17 (enforced)         |
| 12 | Class-File API                                         | Java 22         | Java 24                    |

---

*Document details compiled from OpenJDK JEP specifications and official Oracle release notes.*
