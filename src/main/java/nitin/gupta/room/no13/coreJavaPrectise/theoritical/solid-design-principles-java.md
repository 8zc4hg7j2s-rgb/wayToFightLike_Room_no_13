# SOLID Design Principles (with Java Examples)

SOLID is an acronym for five object-oriented design principles that help developers write maintainable, extensible, and
testable code. Introduced by Robert C. Martin ("Uncle Bob"), these principles are widely used as a foundation for good
software architecture.

---

## S — Single Responsibility Principle (SRP)

**A class should have only one reason to change.**

Each class should be responsible for a single part of the functionality provided by the software, and that
responsibility should be entirely encapsulated by the class.

### ❌ Violation

```java
class Invoice {
    private String id;
    private double amount;

    public double calculateTotal() {
        return amount * 1.18; // adds tax
    }

    // Violates SRP: Invoice class also handles persistence
    public void saveToDatabase() {
        System.out.println("Saving invoice " + id + " to database...");
    }

    // Violates SRP: Invoice class also handles printing
    public void printInvoice() {
        System.out.println("Invoice ID: " + id + ", Total: " + calculateTotal());
    }
}
```

### ✅ Correct

```java
class Invoice {
    private String id;
    private double amount;

    public double calculateTotal() {
        return amount * 1.18;
    }

    public String getId() {
        return id;
    }
}

class InvoiceRepository {
    public void save(Invoice invoice) {
        System.out.println("Saving invoice " + invoice.getId() + " to database...");
    }
}

class InvoicePrinter {
    public void print(Invoice invoice) {
        System.out.println("Invoice ID: " + invoice.getId() +
                ", Total: " + invoice.calculateTotal());
    }
}
```

Now each class has exactly one reason to change: `Invoice` changes if billing logic changes, `InvoiceRepository` changes
if storage changes, and `InvoicePrinter` changes if the output format changes.

---

## O — Open/Closed Principle (OCP)

**Software entities should be open for extension but closed for modification.**

You should be able to add new functionality without changing existing, tested code.

### ❌ Violation

```java
class DiscountCalculator {
    public double calculate(String customerType, double amount) {
        if (customerType.equals("REGULAR")) {
            return amount * 0.95;
        } else if (customerType.equals("PREMIUM")) {
            return amount * 0.90;
        }
        // Every new customer type requires modifying this method
        return amount;
    }
}
```

### ✅ Correct

```java
interface DiscountStrategy {
    double applyDiscount(double amount);
}

class RegularCustomerDiscount implements DiscountStrategy {
    public double applyDiscount(double amount) {
        return amount * 0.95;
    }
}

class PremiumCustomerDiscount implements DiscountStrategy {
    public double applyDiscount(double amount) {
        return amount * 0.90;
    }
}

class VipCustomerDiscount implements DiscountStrategy {
    public double applyDiscount(double amount) {
        return amount * 0.80;
    }
}

class DiscountCalculator {
    public double calculate(DiscountStrategy strategy, double amount) {
        return strategy.applyDiscount(amount);
    }
}
```

Adding a new customer type (e.g., `VipCustomerDiscount`) means creating a new class — no existing code is touched.

---

## L — Liskov Substitution Principle (LSP)

**Objects of a superclass should be replaceable with objects of a subclass without breaking the application.**

Subtypes must honor the contract established by their base type.

### ❌ Violation

```java
class Bird {
    public void fly() {
        System.out.println("Flying...");
    }
}

class Ostrich extends Bird {
    // Ostriches can't fly — this breaks the contract of Bird
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Ostriches can't fly!");
    }
}
```

Any code that calls `bird.fly()` expecting it to work will break when given an `Ostrich`.

### ✅ Correct

```java
interface Bird {
    void eat();
}

interface FlyingBird extends Bird {
    void fly();
}

class Sparrow implements FlyingBird {
    public void eat() {
        System.out.println("Sparrow eating...");
    }

    public void fly() {
        System.out.println("Sparrow flying...");
    }
}

class Ostrich implements Bird {
    public void eat() {
        System.out.println("Ostrich eating...");
    }
    // No fly() method — no broken contract
}
```

Now `Ostrich` only implements behavior it can actually support, and any `FlyingBird` reference is guaranteed to fly
correctly.

---

## I — Interface Segregation Principle (ISP)

**Clients should not be forced to depend on interfaces they do not use.**

Prefer several small, specific interfaces over one large, general-purpose interface.

### ❌ Violation

```java
interface Worker {
    void work();

    void eat();
}

class HumanWorker implements Worker {
    public void work() {
        System.out.println("Human working...");
    }

    public void eat() {
        System.out.println("Human eating...");
    }
}

// Forced to implement eat(), which makes no sense for a robot
class RobotWorker implements Worker {
    public void work() {
        System.out.println("Robot working...");
    }

    public void eat() {
        throw new UnsupportedOperationException("Robots don't eat!");
    }
}
```

### ✅ Correct

```java
interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

class HumanWorker implements Workable, Eatable {
    public void work() {
        System.out.println("Human working...");
    }

    public void eat() {
        System.out.println("Human eating...");
    }
}

class RobotWorker implements Workable {
    public void work() {
        System.out.println("Robot working...");
    }
    // No eat() method needed
}
```

---

## D — Dependency Inversion Principle (DIP)

**High-level modules should not depend on low-level modules; both should depend on abstractions.**

### ❌ Violation

```java
class MySQLDatabase {
    public void save(String data) {
        System.out.println("Saving to MySQL: " + data);
    }
}

class UserService {
    // Directly depends on a concrete, low-level class
    private MySQLDatabase database = new MySQLDatabase();

    public void saveUser(String user) {
        database.save(user);
    }
}
```

If you want to switch to PostgreSQL or MongoDB, you must modify `UserService`.

### ✅ Correct

```java
interface Database {
    void save(String data);
}

class MySQLDatabase implements Database {
    public void save(String data) {
        System.out.println("Saving to MySQL: " + data);
    }
}

class MongoDatabase implements Database {
    public void save(String data) {
        System.out.println("Saving to MongoDB: " + data);
    }
}

class UserService {
    private final Database database;

    // Dependency is injected via constructor — depends on abstraction
    public UserService(Database database) {
        this.database = database;
    }

    public void saveUser(String user) {
        database.save(user);
    }
}

// Usage
UserService service = new UserService(new MongoDatabase());
service.

saveUser("Alice");
```

`UserService` now depends only on the `Database` interface. Swapping databases requires no changes to `UserService`
itself.

---

## Summary Table

| Principle                 | Core Idea                                     | Key Benefit                 |
|---------------------------|-----------------------------------------------|-----------------------------|
| **S**ingle Responsibility | One class, one reason to change               | Easier maintenance          |
| **O**pen/Closed           | Open for extension, closed for modification   | Safer feature additions     |
| **L**iskov Substitution   | Subtypes must be substitutable for base types | Reliable polymorphism       |
| **I**nterface Segregation | Many small interfaces over one large one      | No unused dependencies      |
| **D**ependency Inversion  | Depend on abstractions, not concretions       | Loose coupling, testability |

---

## Why SOLID Matters

- **Maintainability** — Changes are localized and predictable.
- **Testability** — Loosely coupled code (especially via DIP) is easy to unit test with mocks/stubs.
- **Scalability** — New features can be added (OCP) without destabilizing existing functionality.
- **Readability** — Small, focused classes and interfaces (SRP, ISP) are easier to understand.

SOLID principles work best together — they are complementary, not isolated rules. For example, applying OCP often
naturally requires DIP (coding to interfaces) to allow extension without modification.
