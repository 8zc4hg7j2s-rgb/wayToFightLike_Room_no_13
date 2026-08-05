# Java Evolution: Java 8 → Java 25
### A Detailed Feature Guide & Comparison

> Legend: 🟢 = LTS (Long-Term Support) release · 🔵 = Non-LTS (feature) release

---

## Table of Contents
1. [Quick Comparison Table](#quick-comparison-table)
2. [Java 8 (LTS, 2014)](#java-8-lts---march-2014)
3. [Java 9 (2017)](#java-9---september-2017)
4. [Java 10 (2018)](#java-10---march-2018)
5. [Java 11 (LTS, 2018)](#java-11-lts---september-2018)
6. [Java 12 (2019)](#java-12---march-2019)
7. [Java 13 (2019)](#java-13---september-2019)
8. [Java 14 (2020)](#java-14---march-2020)
9. [Java 15 (2020)](#java-15---september-2020)
10. [Java 16 (2021)](#java-16---march-2021)
11. [Java 17 (LTS, 2021)](#java-17-lts---september-2021)
12. [Java 18 (2022)](#java-18---march-2022)
13. [Java 19 (2022)](#java-19---september-2022)
14. [Java 20 (2023)](#java-20---march-2023)
15. [Java 21 (LTS, 2023)](#java-21-lts---september-2023)
16. [Java 22 (2024)](#java-22---march-2024)
17. [Java 23 (2024)](#java-23---september-2024)
18. [Java 24 (2025)](#java-24---march-2025)
19. [Java 25 (LTS, 2025)](#java-25-lts---september-2025)
20. [Feature Evolution Timelines](#feature-evolution-timelines)
21. [Which LTS Should You Use?](#which-lts-should-you-use)

---

## Quick Comparison Table

| Version | Type | Release Date | Headline Features |
|---|---|---|---|
| **8** 🟢 | LTS | Mar 2014 | Lambdas, Streams, `java.time`, Optional, Default methods |
| **9** 🔵 | Feature | Sep 2017 | Module System (Project Jigsaw), JShell, `var` (private) |
| **10** 🔵 | Feature | Mar 2018 | Local-variable type inference (`var`), App CDS |
| **11** 🟢 | LTS | Sep 2018 | `var` in lambdas, new HTTP Client, single-file execution, removed JavaFX/EE |
| **12** 🔵 | Feature | Mar 2019 | Switch expressions (preview), Shenandoah GC |
| **13** 🔵 | Feature | Sep 2019 | Text blocks (preview), switch yield |
| **14** 🔵 | Feature | Mar 2020 | Records (preview), Pattern matching `instanceof` (preview), Switch expressions (final), helpful NPEs |
| **15** 🔵 | Feature | Sep 2020 | Text blocks (final), Sealed classes (preview), Hidden classes |
| **16** 🔵 | Feature | Mar 2021 | Records (final), Pattern matching `instanceof` (final), Vector API (incubator) |
| **17** 🟢 | LTS | Sep 2021 | Sealed classes (final), pattern matching, strong encapsulation of JDK internals |
| **18** 🔵 | Feature | Mar 2022 | UTF-8 by default, simple web server, `@snippet` in Javadoc |
| **19** 🔵 | Feature | Sep 2022 | Virtual threads (preview), structured concurrency (incubator), pattern matching for switch (preview) |
| **20** 🔵 | Feature | Mar 2023 | Scoped values (incubator), record patterns (2nd preview), virtual threads (2nd preview) |
| **21** 🟢 | LTS | Sep 2023 | **Virtual threads (final)**, record patterns (final), pattern matching switch (final), sequenced collections, generational ZGC |
| **22** 🔵 | Feature | Mar 2024 | Unnamed variables/patterns, foreign function & memory API (final), stream gatherers (preview) |
| **23** 🔵 | Feature | Sep 2024 | Primitive types in patterns (preview), Markdown Javadoc, stream gatherers (2nd preview) |
| **24** 🔵 | Feature | Mar 2025 | Stream gatherers (final), class-file API (final), quantum-resistant crypto (ML-KEM/ML-DSA), compact object headers (experimental) |
| **25** 🟢 | LTS | Sep 2025 | Module import declarations, flexible constructor bodies, compact source files (final), scoped values (final), PEM API (preview), AOT profiling |

---

## Java 8 (LTS) — March 2014
The most transformative release since Java 5; introduced functional programming to Java.

- **Lambda Expressions (JEP 126):** Enables treating functionality as a method argument, or code as data.
  ```java
  list.forEach(item -> System.out.println(item));
  ```
- **Stream API (`java.util.stream`):** Functional-style operations on collections — `map`, `filter`, `reduce`, `collect`.
  ```java
  List<String> names = people.stream()
      .filter(p -> p.getAge() > 18)
      .map(Person::getName)
      .collect(Collectors.toList());
  ```
- **Functional Interfaces:** `Function`, `Predicate`, `Supplier`, `Consumer`, `BiFunction`, etc., in `java.util.function`.
- **Default & Static Methods in Interfaces:** Allows interfaces to evolve without breaking implementers.
- **New Date/Time API (`java.time` — JSR-310):** Immutable, thread-safe replacement for `Date`/`Calendar` (`LocalDate`, `LocalTime`, `LocalDateTime`, `ZonedDateTime`, `Duration`, `Period`).
- **Optional<T>:** A container object to avoid `NullPointerException`.
- **Method References:** `ClassName::methodName` shorthand for lambdas.
- **Nashorn JavaScript Engine:** Replaced Rhino (later removed in Java 15).
- **Metaspace:** Replaced PermGen for class metadata storage.
- **Parallel Array Sorting**, **Base64 API**, **Type Annotations (JSR 308)**.

---

## Java 9 — September 2017
- **Java Platform Module System / Project Jigsaw (JEP 261):** Introduces `module-info.java`; JDK itself is split into modules.
- **JShell (JEP 222):** Interactive REPL for quick Java experimentation.
- **Private Interface Methods:** Interfaces can have private helper methods.
- **Improved `try-with-resources`:** Effectively-final variables can be used directly.
- **Collection Factory Methods:** `List.of()`, `Set.of()`, `Map.of()` for immutable collections.
- **Stream API Enhancements:** `takeWhile`, `dropWhile`, `iterate` (with predicate), `ofNullable`.
- **Multi-Release JAR Files.**
- **Process API Updates:** Better control/info about OS processes.
- **`var` for local vars — proposed here, delivered in Java 10.**

**Compared to Java 8:** Java 9's biggest shift was architectural (modularity) rather than syntactic — it changed how the JDK is built and packaged more than how everyday code is written.

---

## Java 10 — March 2018
- **Local-Variable Type Inference (JEP 286):** The `var` keyword.
  ```java
  var list = new ArrayList<String>(); // inferred as ArrayList<String>
  ```
- **Application Class-Data Sharing (AppCDS):** Reduces startup time and footprint.
- **Garbage-Collector Interface** and **Parallel Full GC for G1.**
- **Thread-Local Handshakes:** Perform callbacks on threads without global VM safepoints.
- Root Certificates, Time-Based Release Versioning (adopted the `$YEAR.$MONTH` release cadence going forward).

---

## Java 11 (LTS) — September 2018
First LTS after the new 6-month release cadence began; a major line in the sand for enterprises moving off Java 8.

- **`var` in Lambda Parameters (JEP 323).**
- **New HTTP Client (JEP 321):** Standardized, supports HTTP/2 and WebSocket (was incubator in Java 9).
  ```java
  HttpClient client = HttpClient.newHttpClient();
  HttpRequest request = HttpRequest.newBuilder(URI.create("https://example.com")).build();
  ```
- **Single-File Source-Code Launch:** Run `.java` files directly — `java HelloWorld.java` (no compile step).
- **Removed:** Java EE and CORBA modules (JAX-WS, JAXB, etc.), **JavaFX** decoupled from JDK, Nashorn deprecated.
- **Flight Recorder (JFR)** open-sourced.
- **Epsilon GC:** A "no-op" garbage collector for performance testing.
- **String Methods:** `isBlank()`, `strip()`, `lines()`, `repeat()`.

**Compared to Java 8:** Java 11 is leaner (many bundled libraries removed), adds a modern HTTP client, and formalizes `var`. Most companies treat the Java 8 → 11 migration as the real "modernization" jump.

---

## Java 12 — March 2019
- **Switch Expressions (JEP 325, Preview):** `switch` as an expression using `->` syntax.
  ```java
  int numLetters = switch (day) {
      case MONDAY, FRIDAY, SUNDAY -> 6;
      case TUESDAY -> 7;
      default -> 0;
  };
  ```
- **Shenandoah GC (Experimental):** Low-pause-time garbage collector (Red Hat).
- **JVM Constants API**, **Microbenchmark Suite** added to JDK sources.
- **`Collectors.teeing()`** in Streams.

---

## Java 13 — September 2019
- **Text Blocks (JEP 355, Preview):** Multi-line string literals.
  ```java
  String json = """
      {
          "name": "John"
      }
      """;
  ```
- **Switch Expressions (2nd Preview):** Added `yield` keyword for returning values.
- **Dynamic CDS Archives (JEP 350).**
- **Reimplemented Socket API (JEP 353).**

---

## Java 14 — March 2020
- **Records (JEP 359, Preview):** Compact syntax for immutable data carrier classes.
  ```java
  record Point(int x, int y) { }
  ```
- **Pattern Matching for `instanceof` (JEP 305, Preview):** Removes explicit casting.
  ```java
  if (obj instanceof String s) {
      System.out.println(s.length());
  }
  ```
- **Switch Expressions (JEP 361, Final/Standard).**
- **Helpful NullPointerExceptions (JEP 358):** Pinpoints exactly which variable was `null`.
- **Removed:** CMS Garbage Collector; **Deprecated:** ParallelScavenge + SerialOld GC combo, Solaris/SPARC ports.
- **Packaging Tool (`jpackage`, Incubator).**

---

## Java 15 — September 2020
- **Text Blocks (JEP 378, Final/Standard).**
- **Sealed Classes (JEP 360, Preview):** Restrict which classes can extend/implement a class or interface.
  ```java
  sealed interface Shape permits Circle, Square, Triangle { }
  ```
- **Hidden Classes (JEP 371):** For frameworks generating classes at runtime.
- **Records & Pattern Matching for `instanceof`:** 2nd preview.
- **Removed:** Nashorn JavaScript Engine.
- **Deprecated:** RMI Activation, Biased Locking.
- **EdDSA (Edwards-Curve Digital Signature Algorithm)** added.

---

## Java 16 — March 2021
- **Records (JEP 395, Final/Standard).**
- **Pattern Matching for `instanceof` (JEP 394, Final/Standard).**
- **Sealed Classes:** 2nd preview.
- **Vector API (JEP 338, Incubator):** Express vector computations that compile to optimal hardware instructions.
- **Strongly Encapsulate JDK Internals by Default (JEP 396):** Foreshadowed Java 17's stricter enforcement.
- **Foreign Linker API & Foreign-Memory Access API (Incubator).**
- **Unix-Domain Socket Channels.**

---

## Java 17 (LTS) — September 2021
The second major enterprise LTS milestone after Java 11 — became the default target for most modern frameworks (Spring Boot 3+, Quarkus, etc.).

- **Sealed Classes (JEP 409, Final/Standard).**
- **Pattern Matching for `switch` (JEP 406, Preview) — first appearance.**
- **Strong Encapsulation of JDK Internals (JEP 403):** `--illegal-access` option removed; internal APIs no longer accessible by default (breaks some old reflection-based libraries).
- **Enhanced Pseudo-Random Number Generators (JEP 356).**
- **New macOS Rendering Pipeline (Metal API), macOS/AArch64 Port (Apple Silicon support).**
- **Deprecated:** Applet API, Security Manager (for future removal).
- **Removed:** RMI Activation mechanism.
- **Context-Specific Deserialization Filters (JEP 415).**

**Compared to Java 11:** Java 17 brings sealed classes, richer pattern matching, and much stricter JDK encapsulation — plus critically, it's the minimum baseline for most modern frameworks going forward (e.g., Spring Framework 6 / Spring Boot 3 require Java 17+).

---

## Java 18 — March 2022
- **UTF-8 by Default (JEP 400):** `UTF-8` becomes the default charset across all platforms.
- **Simple Web Server (JEP 408):** `jwebserver` command-line tool for basic static file serving.
- **Code Snippets in Java API Documentation (JEP 413):** `@snippet` tag in Javadoc.
- **Reimplement Core Reflection with Method Handles (JEP 416).**
- **Vector API:** 2nd incubator.
- **Deprecated:** Finalization mechanism (`Object.finalize()`) for removal.

---

## Java 19 — September 2022
Project Loom features begin arriving.

- **Virtual Threads (JEP 425, Preview):** Lightweight threads managed by the JVM, not the OS — enables millions of concurrent threads.
  ```java
  try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      executor.submit(() -> System.out.println("Running in a virtual thread"));
  }
  ```
- **Structured Concurrency (JEP 428, Incubator):** Treats groups of related tasks as a single unit of work.
- **Pattern Matching for `switch` (JEP 427):** 2nd preview, adds record deconstruction patterns.
- **Record Patterns (JEP 405, Preview) — first appearance.**
- **Foreign Function & Memory API (JEP 424, Preview):** Successor to JNI for calling native code.
- **Vector API:** 4th incubator.

---

## Java 20 — March 2023
- **Scoped Values (JEP 429, Incubator):** Share immutable data within/across threads — a lighter alternative to `ThreadLocal`, designed for virtual threads.
- **Record Patterns (JEP 432):** 2nd preview.
- **Pattern Matching for `switch` (JEP 433):** 3rd preview.
- **Virtual Threads (JEP 436):** 2nd preview.
- **Structured Concurrency (JEP 437):** 2nd incubator.
- **Foreign Function & Memory API (JEP 434):** 2nd preview.

---

## Java 21 (LTS) — September 2023
**The most significant LTS since Java 17** — finalizes virtual threads, cementing a new concurrency model for Java.

- **Virtual Threads (JEP 444, Final/Standard):** ⭐ The headline feature — dramatically simplifies writing high-throughput concurrent applications without reactive/async complexity.
- **Record Patterns (JEP 440, Final/Standard):** Deconstruct record values in patterns.
  ```java
  if (obj instanceof Point(int x, int y)) {
      System.out.println(x + y);
  }
  ```
- **Pattern Matching for `switch` (JEP 441, Final/Standard).**
  ```java
  String result = switch (shape) {
      case Circle c -> "Circle r=" + c.radius();
      case Square s  -> "Square side=" + s.side();
      default -> "Unknown";
  };
  ```
- **Sequenced Collections (JEP 431):** New `SequencedCollection`, `SequencedSet`, `SequencedMap` interfaces with `getFirst()`, `getLast()`, `reversed()`.
- **Generational ZGC (JEP 439):** Improves GC pause times by generational collection.
- **Virtual Thread-friendly:** `Structured Concurrency` (2nd preview), `Scoped Values` (preview).
- **String Templates (JEP 430, Preview) — first appearance** (later dropped/redesigned, never finalized).
- **Key Encapsulation Mechanism API (JEP 452).**
- **Deprecated for removal:** the 32-bit x86 port, Windows 32-bit x86 port.

**Compared to Java 17:** Java 21 is where "modern Java concurrency" really begins — virtual threads plus pattern matching/record deconstruction change how idiomatic Java code looks and scales.

---

## Java 22 — March 2024
- **Unnamed Variables & Patterns (JEP 456):** Use `_` for variables/patterns you don't need.
  ```java
  if (obj instanceof Point(int x, _)) { ... }
  ```
- **Statements before `super(...)` (JEP 447, Preview):** Relaxes constructor rules — precursor to Java 25's flexible constructor bodies.
- **Foreign Function & Memory API (JEP 454, Final/Standard):** Officially replaces JNI for native interop.
- **Stream Gatherers (JEP 461, Preview) — first appearance:** Custom intermediate stream operations.
- **Class-File API (JEP 457, Preview).**
- **Structured Concurrency & Scoped Values:** further previews.
- **Launch Multi-File Source Programs (JEP 458).**
- **Region Pinning for G1 GC (JEP 423).**

---

## Java 23 — September 2024
- **Primitive Types in Patterns, `instanceof`, and `switch` (JEP 455, Preview) — first appearance.**
- **Markdown Documentation Comments (JEP 467):** Write Javadoc using Markdown syntax instead of HTML/JavaDoc tags.
- **Stream Gatherers (JEP 473):** 2nd preview.
- **Structured Concurrency (JEP 480):** 3rd preview.
- **Class-File API (JEP 466):** 2nd preview.
- **Flexible Constructor Bodies (JEP 482):** 2nd preview.
- **Vector API:** 8th incubator.
- **Deprecate the Memory-Access Methods in `sun.misc.Unsafe` for Removal (JEP 471).**
- **ZGC becomes the default generational mode.**

---

## Java 24 — March 2025
- **Stream Gatherers (JEP 485, Final/Standard).**
- **Class-File API (JEP 484, Final/Standard).**
- **Quantum-Resistant Cryptography:**
  - **Module-Lattice-Based Key Encapsulation Mechanism, ML-KEM (JEP 496, Final).**
  - **Module-Lattice-Based Digital Signature Algorithm, ML-DSA (JEP 497, Final).**
- **Compact Object Headers (JEP 450, Experimental) — first appearance:** Reduces per-object memory overhead.
- **Ahead-of-Time Class Loading & Linking (JEP 483):** Improves startup via AOT cache.
- **Generational Shenandoah (JEP 404).**
- **Scoped Values (JEP 487):** 4th preview.
- **Structured Concurrency (JEP 499):** 4th preview.
- **Simple Source Files and Instance Main Methods (JEP 495):** 4th preview (evolved into JEP 512 in Java 25).
- **Removed:** the 32-bit x86 port (finalized removal path started in 21).
- **Permanently Disable the Security Manager (JEP 486).**

---

## Java 25 (LTS) — September 2025
Latest LTS (5-year premier support). 18 JEPs total: 11 delivering new/finalized features, plus previews/incubators/experimental features.

- **Module Import Declarations (JEP 511, Final/Standard):** Import all packages exported by a module in one line.
  ```java
  import module java.base;
  ```
- **Compact Source Files and Instance Main Methods (JEP 512, Final/Standard):** Simplifies the classic "Hello World" — no class/static boilerplate needed for simple programs.
  ```java
  void main() {
      System.out.println("Hello, World!");
  }
  ```
- **Flexible Constructor Bodies (JEP 513, Final/Standard):** Code can run before `super()`/`this()` calls in constructors, as long as it doesn't reference the instance being constructed.
- **Scoped Values (JEP 506, Final/Standard):** Finalizes the `ThreadLocal` alternative introduced in Java 20's incubator.
- **Compact Object Headers (JEP 519):** Promoted from experimental (Java 24) to a **product feature**, shrinking heap footprint.
- **PEM Encodings of Cryptographic Objects (JEP 470, Preview):** Standard API to encode/decode PEM-format keys and certificates.
- **Key Derivation Function API (JEP 510, Final/Standard):** Standardized cryptographic KDF support.
- **Stable Values (JEP 502, Preview):** Deferred immutable value holders for better startup performance.
- **Structured Concurrency (JEP 505):** 5th preview.
- **Primitive Types in Patterns, `instanceof`, and `switch` (JEP 507):** 3rd preview.
- **Vector API (JEP 508):** 10th incubator round.
- **Ahead-of-Time Command-Line Ergonomics (JEP 514):** Simplifies commands for creating AOT caches (Project Leyden).
- **Ahead-of-Time Method Profiling (JEP 515):** Reuses method-execution profiles from a previous run at JVM startup, improving warm-up time.
- **JFR Cooperative Sampling (JEP 518)** and **JFR Method Timing & Tracing (JEP 520):** Deeper runtime observability.
- **JFR CPU-Time Profiling (JEP 509, Experimental).**
- **Generational Shenandoah (JEP 521, Final/Standard).**
- **Removed:** the 32-bit x86 port entirely (JEP 503); the experimental Graal JIT compiler.

**Compared to Java 21:** Java 25 focuses less on flashy new syntax and more on startup performance (AOT profiling/caching, Project Leyden groundwork), memory efficiency (compact object headers as a default-eligible feature), and finishing what Loom/Amber started (scoped values finalized, more pattern-matching maturity). It's the natural upgrade target for teams currently on Java 17 or 21.

---

## Feature Evolution Timelines

### Pattern Matching Journey
| Version | Milestone |
|---|---|
| 14 | `instanceof` pattern matching — Preview |
| 16 | `instanceof` pattern matching — **Final** |
| 17 | `switch` pattern matching — Preview |
| 19 | Record patterns — Preview; `switch` patterns — 2nd preview |
| 20 | Record patterns — 2nd preview; `switch` patterns — 3rd preview |
| 21 | Record patterns — **Final**; `switch` patterns — **Final** |
| 23 | Primitive types in patterns — Preview |
| 25 | Primitive types in patterns — 3rd preview (not yet final) |

### Concurrency (Project Loom) Journey
| Version | Milestone |
|---|---|
| 19 | Virtual threads — Preview; Structured concurrency — Incubator |
| 20 | Virtual threads — 2nd preview; Scoped values — Incubator |
| 21 | **Virtual threads — Final** |
| 22–24 | Structured concurrency & scoped values — ongoing previews |
| 25 | **Scoped values — Final**; Structured concurrency — 5th preview |

### Records & Data Modeling Journey
| Version | Milestone |
|---|---|
| 14 | Records — Preview |
| 15–16 | Records — 2nd preview → **Final** |
| 15 | Sealed classes — Preview |
| 17 | Sealed classes — **Final** |
| 19–21 | Record patterns — Preview → **Final** |

### Startup/Performance (Project Leyden) Journey
| Version | Milestone |
|---|---|
| 24 | Compact object headers — Experimental; AOT class loading/linking |
| 25 | Compact object headers — Product feature; AOT method profiling; AOT command-line ergonomics |

---

## Which LTS Should You Use?

| If you're currently on... | Consider moving to... | Why |
|---|---|---|
| Java 8 | Java 17 or 21 | Java 8 is out of free public updates; most frameworks (Spring Boot 3+) require 17+ |
| Java 11 | Java 21 | Get virtual threads, pattern matching, sequenced collections in one jump |
| Java 17 | Java 21 or 25 | Virtual threads (21) are a major concurrency simplification; 25 adds startup/memory wins |
| Java 21 | Java 25 | Latest LTS, 5 years of premier support, finalized scoped values, AOT tooling maturing |

**General rule:** For new projects, always start on the **latest LTS** (currently **Java 25**, supported until ~2030). For existing systems, LTS-to-LTS migration (8→11→17→21→25) is safer than jumping through non-LTS releases, though non-LTS versions are fully production-supported for their 6-month window if you're on a rapid upgrade cadence.

---

*Document compiled with details from Oracle JEP release notes and OpenJDK documentation, covering Java 8 (2014) through Java 25 (2025).*
