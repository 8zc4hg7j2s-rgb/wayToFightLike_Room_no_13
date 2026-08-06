md_content = """# SOLID Design Principles in Java

The **SOLID** principles are five design guidelines for writing clean, maintainable, understandable, and scalable
object-oriented software. Introduced by Robert C. Martin (Uncle Bob), these principles help developers reduce tight
coupling, avoid code smell, and design robust architectures.

---

## Overview

| Letter | Principle                                 | Core Idea                                                                         |
|:------:|:------------------------------------------|:----------------------------------------------------------------------------------|
| **S**  | **Single Responsibility Principle (SRP)** | A class should have one, and only one, reason to change.                          |
| **O**  | **Open/Closed Principle (OCP)**           | Software entities should be open for extension, but closed for modification.      |
| **L**  | **Liskov Substitution Principle (LSP)**   | Subtypes must be substitutable for their base types without altering correctness. |
| **I**  | **Interface Segregation Principle (ISP)** | Clients should not be forced to depend on interfaces they do not use.             |
| **D**  | **Dependency Inversion Principle (DIP)**  | High-level modules should depend on abstractions, not concrete implementations.   |

---

## 1. Single Responsibility Principle (SRP)

> **Definition:** A class should have only one reason to change, meaning it should have only one job or responsibility.

### ❌ Violation Example

In this example, the `Invoice` class handles invoice calculation, printing, and database persistence. If the saving
logic or printing format changes, this class must be modified.

```java
// BAD: Invoice class handles multiple responsibilities
public class Invoice {
    private double amount;

    public Invoice(double amount) {
        this.amount = amount;
    }

    public double calculateTotal() {
        return amount * 1.18; // Includes tax
    }

    public void printInvoice() {
        System.out.println("Invoice Amount: " + calculateTotal());
    }

    public void saveToDatabase() {
        System.out.println("Saving invoice to DB...");
    }
}
```

### ✅ Good Example

Separate the responsibilities into distinct classes: `Invoice` for business calculation, `InvoicePrinter` for rendering
output, and
`InvoiceRepository` for persistence.

``` java
// GOOD: Each class has a single responsibility

public class Invoice {
    private double amount;

    public Invoice(double amount) {
        this.amount = amount;
    }

    public double calculateTotal() {
        return amount * 1.18;
    }
}

public class InvoicePrinter {
    public void print(Invoice invoice) {
        System.out.println("Invoice Amount: " + invoice.calculateTotal());
    }
}

public class InvoiceRepository {
    public void save(Invoice invoice) {
        System.out.println("Saving invoice to DB...");
    }
}
```

------------------------------------------------------------------------

## 2. Open/Closed Principle (OCP)

> **Definition:** Software entities (classes, modules, functions, etc.)
> should be **open for extension**, but **closed for modification**.

### ❌ Violation Example

Adding a new payment method (e.g., PayPal) requires modifying the existing `PaymentProcessor` class, increasing the risk
of introducing bugs.

``` java
// BAD: Class must be modified whenever a new payment mode is added
public class PaymentProcessor {
    public void processPayment(String type, double amount) {
        if ("CREDIT_CARD".equalsIgnoreCase(type)) {
            System.out.println("Processing credit card payment of $" + amount);
        } else if ("DEBIT_CARD".equalsIgnoreCase(type)) {
            System.out.println("Processing debit card payment of $" + amount);
        }
        // Adding PayPal requires modifying this method!
    }
}
```

### ✅ Good Example

Define a `PaymentMethod` interface. Adding new payment methods requires creating new implementation classes without
touching existing code.

``` java
// GOOD: Open for extension via abstraction, closed for modification

public interface PaymentMethod {
    void pay(double amount);
}

public class CreditCardPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Processing credit card payment of $" + amount);
    }
}

public class PayPalPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Processing PayPal payment of $" + amount);
    }
}

public class PaymentService {
    public void executePayment(PaymentMethod paymentMethod, double amount) {
        paymentMethod.pay(amount);
    }
}
```

------------------------------------------------------------------------

## 3. Liskov Substitution Principle (LSP)

> **Definition:** Derived or child classes must be substitutable for
> their base or parent classes without breaking the application's
> correctness.

### ❌ Violation Example

`Square` inherits from `Rectangle`, but setting `width` on `Square`
forces `height` to change as well. Substituting a `Square` where a
`Rectangle` is expected causes unexpected behavior.

``` java
// BAD: Square breaks the expectations set by Rectangle
public class Rectangle {
    protected int width;
    protected int height;

    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }
    public int getArea() { return width * height; }
}

public class Square extends Rectangle {
    @Override
    public void setWidth(int width) {
        this.width = width;
        this.height = width; // Unexpected side effect for Rectangle caller
    }

    @Override
    public void setHeight(int height) {
        this.width = height;
        this.height = height;
    }
}
```

### ✅ Good Example

Segregate `Square` and `Rectangle` into independent classes implementing a common `Shape` interface.

``` java
// GOOD: Derived classes adhere to the contract without side effects

public interface Shape {
    int getArea();
}

public class Rectangle implements Shape {
    private int width;
    private int height;

    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getArea() {
        return width * height;
    }
}

public class Square implements Shape {
    private int side;

    public Square(int side) {
        this.side = side;
    }

    @Override
    public int getArea() {
        return side * side;
    }
}
```

------------------------------------------------------------------------

## 4. Interface Segregation Principle (ISP)

> **Definition:** Clients should not be forced to depend upon interfaces
> that they do not use. Prefer smaller, focused interfaces over large
> monolithic ones.

### ❌ Violation Example

`RobotWorker` is forced to implement `eat()`, which it doesn't need, throwing an `UnsupportedOperationException`.

``` java
// BAD: Fat interface forcing unnecessary methods
public interface Worker {
    void work();
    void eat();
}

public class RobotWorker implements Worker {
    @Override
    public void work() {
        System.out.println("Robot working...");
    }

    @Override
    public void eat() {
        throw new UnsupportedOperationException("Robots do not eat");
    }
}
```

### ✅ Good Example

Break down the large interface into dedicated interfaces (`Workable`,
`Eatable`).

``` java
// GOOD: Fine-grained, cohesive interfaces

public interface Workable {
    void work();
}

public interface Eatable {
    void eat();
}

public class HumanWorker implements Workable, Eatable {
    @Override
    public void work() {
        System.out.println("Human working...");
    }

    @Override
    public void eat() {
        System.out.println("Human eating lunch...");
    }
}

public class RobotWorker implements Workable {
    @Override
    public void work() {
        System.out.println("Robot working...");
    }
}
```

------------------------------------------------------------------------

## 5. Dependency Inversion Principle (DIP)

> **Definition:** High-level modules should not depend on low-level
> modules. Both should depend on abstractions (e.g., interfaces).
> Abstractions should not depend on details; details should depend on
> abstractions.

### ❌ Violation Example

`Car` directly instantiates `PetrolEngine` (tight coupling). Replacing
`PetrolEngine` with `ElectricEngine` requires modifying `Car`.

``` java
// BAD: High-level class tightly coupled to a concrete low-level implementation
public class PetrolEngine {
    public void start() {
        System.out.println("Petrol engine started.");
    }
}

public class Car {
    private PetrolEngine engine; // Direct dependence on concrete class

    public Car() {
        this.engine = new PetrolEngine();
    }

    public void startCar() {
        engine.start();
    }
}
```

### ✅ Good Example

`Car` depends on an `Engine` interface. Specific engine implementations (Petrol, Electric) are injected into `Car` via
constructor injection.

``` java
// GOOD: High-level and low-level modules depend on abstraction

public interface Engine {
    void start();
}

public class PetrolEngine implements Engine {
    @Override
    public void start() {
        System.out.println("Petrol engine started.");
    }
}

public class ElectricEngine implements Engine {
    @Override
    public void start() {
        System.out.println("Electric engine started silently.");
    }
}

public class Car {
    private final Engine engine; // Dependent on interface

    // Dependency Injection
    public Car(Engine engine) {
        this.engine = engine;
    }

    public void startCar() {
        engine.start();
    }
}
```

------------------------------------------------------------------------

## Summary & Key Takeaways

1. **SRP**: Keeps classes focused, lightweight, and easy to test.
2. **OCP**: Promotes extensibility using polymorphism without risking breaking existing code.
3. **LSP**: Ensures subtype behavior stays compatible with interface expectations.
4. **ISP**: Prevents bloated interfaces and dummy/throwing method implementations.
5. **DIP**: Reduces coupling by relying on abstractions, enabling flexibility and easier unit testing (mocking). """

file_path = "SOLID_Design_Principles_Java.md" with open (file_path, "w", encoding="utf-8") as f: f.write (md_content)

print (f"File created successfully: {file_path}")

    ```text?code_stdout&code_event_index=1
    File created successfully: SOLID_Design_Principles_Java.md

Your Markdown file is ready

### Summary of Included Topics in `SOLID_Design_Principles_Java.md`:

1. **Single Responsibility Principle (SRP):** Explanation + Bad vs. Good Java examples separating data/logic from
   persistence and rendering.
2. **Open/Closed Principle (OCP):** Explanation + Bad vs. Good Java examples demonstrating interface extension
   vs. conditional logic.
3. **Liskov Substitution Principle (LSP):** Explanation + The classic
   `Rectangle`/`Square` hierarchy bug vs. a clean interface-driven
   `Shape` model.
4. **Interface Segregation Principle (ISP):** Explanation + Breaking monolithic interfaces into modular (`Workable`,
   `Eatable`)
   interfaces.
5. **Dependency Inversion Principle (DIP):** Explanation + Decoupling
   `Car` from concrete engines using constructor dependency injection.
6. **Summary Table & Best Practices:** Key takeaways for applying SOLID principles in clean architecture.
