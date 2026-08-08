# Java Design Patterns — Complete Guide

Design patterns are proven, reusable solutions to common software design problems. The Gang of Four (GoF) classified 23
classic patterns into three categories:

1. **Creational** — deal with object creation mechanisms
2. **Structural** — deal with object composition and relationships
3. **Behavioral** — deal with object collaboration and responsibility

---

## 1. Creational Design Patterns

Creational patterns abstract the instantiation process, making a system independent of how its objects are created,
composed, and represented.

### 1.1 Singleton

Ensures a class has only one instance and provides a global point of access to it.

```java
public class Singleton {
    private static volatile Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

**Use case:** Logging, configuration managers, thread pools, database connection pools.

---

### 1.2 Factory Method

Defines an interface for creating an object but lets subclasses decide which class to instantiate.

```java
interface Shape {
    void draw();
}

class Circle implements Shape {
    public void draw() { System.out.println("Drawing Circle"); }
}

class Square implements Shape {
    public void draw() { System.out.println("Drawing Square"); }
}

abstract class ShapeFactory {
    abstract Shape createShape();
}

class CircleFactory extends ShapeFactory {
    Shape createShape() { return new Circle(); }
}
```

**Use case:** Frameworks that need to delegate object creation to subclasses (e.g., `Calendar.getInstance()`).

---

### 1.3 Abstract Factory

Provides an interface for creating families of related objects without specifying their concrete classes.

```java
interface Button { void render(); }
interface Checkbox { void render(); }

class WinButton implements Button { public void render() { System.out.println("Windows Button"); } }
class MacButton implements Button { public void render() { System.out.println("Mac Button"); } }

interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

class WinFactory implements GUIFactory {
    public Button createButton() { return new WinButton(); }
    public Checkbox createCheckbox() { return null; /* WinCheckbox */ }
}
```

**Use case:** Cross-platform UI toolkits, families of related products.

---

### 1.4 Builder

Separates the construction of a complex object from its representation.

```java
class Pizza {
    private final String size;
    private final boolean cheese;

    static class Builder {
        private String size;
        private boolean cheese;

        Builder size(String size) { this.size = size; return this; }
        Builder cheese(boolean cheese) { this.cheese = cheese; return this; }
        Pizza build() { return new Pizza(this); }
    }

    private Pizza(Builder b) {
        this.size = b.size;
        this.cheese = b.cheese;
    }
}

// Usage
Pizza pizza = new Pizza.Builder().size("Large").cheese(true).build();
```

**Use case:** Objects with many optional parameters (e.g., `StringBuilder`, `Lombok @Builder`).

---

### 1.5 Prototype

Creates new objects by copying an existing object (clone) rather than creating from scratch.

```java
class Document implements Cloneable {
    String content;

    Document(String content) { this.content = content; }

    @Override
    public Document clone() {
        return new Document(this.content);
    }
}
```

**Use case:** Expensive object creation, object caching, game entity spawning.

---

## 2. Structural Design Patterns

Structural patterns explain how to assemble objects and classes into larger structures while keeping them flexible and
efficient.

### 2.1 Adapter

Converts the interface of a class into another interface clients expect.

```java
interface MediaPlayer { void play(String fileName); }

class LegacyPlayer { void playVlc(String fileName) { System.out.println("Playing vlc: " + fileName); } }

class MediaAdapter implements MediaPlayer {
    LegacyPlayer legacyPlayer = new LegacyPlayer();
    public void play(String fileName) { legacyPlayer.playVlc(fileName); }
}
```

**Use case:** Integrating legacy code or third-party libraries with incompatible interfaces.

---

### 2.2 Bridge

Decouples an abstraction from its implementation so both can vary independently.

```java
interface Color { String fill(); }
class Red implements Color { public String fill() { return "Red"; } }

abstract class Shape {
    protected Color color;
    Shape(Color color) { this.color = color; }
    abstract void draw();
}

class Circle extends Shape {
    Circle(Color color) { super(color); }
    void draw() { System.out.println("Circle filled with " + color.fill()); }
}
```

**Use case:** Avoiding class explosion when combining multiple dimensions (shape × color, device × OS).

---

### 2.3 Composite

Composes objects into tree structures to represent part-whole hierarchies.

```java
interface Employee { void showDetails(); }

class Developer implements Employee {
    String name;
    Developer(String name) { this.name = name; }
    public void showDetails() { System.out.println("Developer: " + name); }
}

class Manager implements Employee {
    List<Employee> team = new ArrayList<>();
    void add(Employee e) { team.add(e); }
    public void showDetails() {
        System.out.println("Manager");
        for (Employee e : team) e.showDetails();
    }
}
```

**Use case:** File systems, UI component trees, organization hierarchies.

---

### 2.4 Decorator

Attaches additional responsibilities to an object dynamically, without altering its structure.

```java
interface Coffee { double cost(); }

class SimpleCoffee implements Coffee {
    public double cost() { return 5.0; }
}

abstract class CoffeeDecorator implements Coffee {
    protected Coffee coffee;
    CoffeeDecorator(Coffee coffee) { this.coffee = coffee; }
}

class MilkDecorator extends CoffeeDecorator {
    MilkDecorator(Coffee coffee) { super(coffee); }
    public double cost() { return coffee.cost() + 1.5; }
}

// Usage
Coffee order = new MilkDecorator(new SimpleCoffee());
```

**Use case:** `java.io` streams (`BufferedReader`, `InputStreamReader`), adding features without subclassing.

---

### 2.5 Facade

Provides a simplified, unified interface to a complex subsystem.

```java
class CPU { void start() { System.out.println("CPU started"); } }
class Memory { void load() { System.out.println("Memory loaded"); } }

class ComputerFacade {
    private CPU cpu = new CPU();
    private Memory memory = new Memory();

    void startComputer() {
        cpu.start();
        memory.load();
    }
}
```

**Use case:** Simplifying complex library/API usage (e.g., `javax.faces.context.FacesContext`).

---

### 2.6 Flyweight

Minimizes memory usage by sharing common parts of state between multiple objects.

```java
class TreeType {
    String name, color;
    TreeType(String name, String color) { this.name = name; this.color = color; }
}

class TreeFactory {
    static Map<String, TreeType> cache = new HashMap<>();
    static TreeType getTreeType(String name, String color) {
        String key = name + color;
        return cache.computeIfAbsent(key, k -> new TreeType(name, color));
    }
}
```

**Use case:** `Integer.valueOf()` caching, text editors (character formatting), game rendering.

---

### 2.7 Proxy

Provides a surrogate or placeholder for another object to control access to it.

```java
interface Image { void display(); }

class RealImage implements Image {
    String fileName;
    RealImage(String fileName) { this.fileName = fileName; loadFromDisk(); }
    void loadFromDisk() { System.out.println("Loading " + fileName); }
    public void display() { System.out.println("Displaying " + fileName); }
}

class ProxyImage implements Image {
    private RealImage realImage;
    private String fileName;
    ProxyImage(String fileName) { this.fileName = fileName; }
    public void display() {
        if (realImage == null) realImage = new RealImage(fileName);
        realImage.display();
    }
}
```

**Use case:** Lazy loading, access control, Spring AOP proxies, RMI.

---

## 3. Behavioral Design Patterns

Behavioral patterns focus on communication and responsibility distribution between objects.

### 3.1 Strategy

Defines a family of algorithms and makes them interchangeable at runtime.

```java
interface PaymentStrategy { void pay(int amount); }

class CreditCardStrategy implements PaymentStrategy {
    public void pay(int amount) { System.out.println("Paid " + amount + " via Credit Card"); }
}

class ShoppingCart {
    PaymentStrategy strategy;
    void setStrategy(PaymentStrategy s) { this.strategy = s; }
    void checkout(int amount) { strategy.pay(amount); }
}
```

**Use case:** Sorting algorithms, payment methods, validation rules.

---

### 3.2 Observer

Defines a one-to-many dependency so that when one object changes state, all dependents are notified.

```java
interface Observer { void update(String message); }

class Subscriber implements Observer {
    String name;
    Subscriber(String name) { this.name = name; }
    public void update(String message) { System.out.println(name + " received: " + message); }
}

class Publisher {
    List<Observer> observers = new ArrayList<>();
    void subscribe(Observer o) { observers.add(o); }
    void notifyAll(String message) { observers.forEach(o -> o.update(message)); }
}
```

**Use case:** Event listeners, `java.util.Observer`, pub-sub systems, MVC view updates.

---

### 3.3 Command

Encapsulates a request as an object, allowing parameterization and queuing of requests.

```java
interface Command { void execute(); }

class Light {
    void on() { System.out.println("Light ON"); }
}

class LightOnCommand implements Command {
    Light light;
    LightOnCommand(Light light) { this.light = light; }
    public void execute() { light.on(); }
}

class RemoteControl {
    void pressButton(Command command) { command.execute(); }
}
```

**Use case:** Undo/redo functionality, task queues, GUI button actions.

---

### 3.4 State

Allows an object to alter its behavior when its internal state changes.

```java
interface State { void handle(); }

class StartState implements State {
    public void handle() { System.out.println("Player in Start State"); }
}

class StopState implements State {
    public void handle() { System.out.println("Player in Stop State"); }
}

class Context {
    State state;
    void setState(State state) { this.state = state; }
    void request() { state.handle(); }
}
```

**Use case:** Order status workflows, TCP connection states, media player controls.

---

### 3.5 Template Method

Defines the skeleton of an algorithm in a method, deferring some steps to subclasses.

```java
abstract class DataProcessor {
    final void process() {
        readData();
        processData();
        writeData();
    }
    abstract void readData();
    abstract void processData();
    void writeData() { System.out.println("Writing default data"); }
}

class CsvProcessor extends DataProcessor {
    void readData() { System.out.println("Reading CSV"); }
    void processData() { System.out.println("Processing CSV"); }
}
```

**Use case:** Frameworks defining an algorithm skeleton (e.g., `HttpServlet.service()`).

---

### 3.6 Chain of Responsibility

Passes a request along a chain of handlers until one of them handles it.

```java
abstract class Approver {
    protected Approver next;
    void setNext(Approver next) { this.next = next; }
    abstract void approve(int amount);
}

class Manager extends Approver {
    void approve(int amount) {
        if (amount <= 1000) System.out.println("Manager approved");
        else if (next != null) next.approve(amount);
    }
}

class Director extends Approver {
    void approve(int amount) { System.out.println("Director approved"); }
}
```

**Use case:** Middleware pipelines, servlet filters, approval workflows, logging levels.

---

### 3.7 Iterator

Provides a way to access elements of a collection sequentially without exposing its underlying structure.

```java
class NameRepository implements Iterable<String> {
    private List<String> names = List.of("A", "B", "C");
    public Iterator<String> iterator() { return names.iterator(); }
}
```

**Use case:** `java.util.Iterator`, custom collection traversal.

---

### 3.8 Mediator

Reduces coupling between components by having them communicate through a mediator object.

```java
interface ChatMediator { void sendMessage(String msg, User user); }

class ChatRoom implements ChatMediator {
    public void sendMessage(String msg, User user) {
        System.out.println(user.name + " sends: " + msg);
    }
}

class User {
    String name;
    ChatMediator mediator;
    User(String name, ChatMediator mediator) { this.name = name; this.mediator = mediator; }
    void send(String msg) { mediator.sendMessage(msg, this); }
}
```

**Use case:** Chat applications, air traffic control systems, UI dialog coordination.

---

### 3.9 Memento

Captures and externalizes an object's internal state so it can be restored later, without violating encapsulation.

```java
class Memento {
    private final String state;
    Memento(String state) { this.state = state; }
    String getState() { return state; }
}

class Originator {
    private String state;
    void setState(String state) { this.state = state; }
    Memento save() { return new Memento(state); }
    void restore(Memento m) { this.state = m.getState(); }
}
```

**Use case:** Undo mechanisms, checkpoints, save/load game state.

---

### 3.10 Visitor

Lets you add further operations to objects without modifying their classes.

```java
interface Visitor { void visit(Book book); }

class Book {
    double price;
    Book(double price) { this.price = price; }
    void accept(Visitor v) { v.visit(this); }
}

class PriceVisitor implements Visitor {
    public void visit(Book book) { System.out.println("Book price: " + book.price); }
}
```

**Use case:** Compilers (AST traversal), operations across heterogeneous object structures.

---

### 3.11 Interpreter

Defines a grammar for a language and provides an interpreter to evaluate sentences in it.

```java
interface Expression { boolean interpret(String context); }

class TerminalExpression implements Expression {
    private String data;
    TerminalExpression(String data) { this.data = data; }
    public boolean interpret(String context) { return context.contains(data); }
}
```

**Use case:** SQL parsers, regex engines, rule engines.

---

## Quick Reference Table

| Category   | Pattern                 | Core Intent                                  |
|------------|-------------------------|----------------------------------------------|
| Creational | Singleton               | One instance, global access                  |
| Creational | Factory Method          | Delegate instantiation to subclasses         |
| Creational | Abstract Factory        | Create families of related objects           |
| Creational | Builder                 | Step-by-step construction of complex objects |
| Creational | Prototype               | Clone existing objects                       |
| Structural | Adapter                 | Convert incompatible interfaces              |
| Structural | Bridge                  | Decouple abstraction from implementation     |
| Structural | Composite               | Tree structures of part-whole hierarchies    |
| Structural | Decorator               | Add behavior dynamically                     |
| Structural | Facade                  | Simplify complex subsystem interface         |
| Structural | Flyweight               | Share state to save memory                   |
| Structural | Proxy                   | Control access to another object             |
| Behavioral | Strategy                | Interchangeable algorithms                   |
| Behavioral | Observer                | Notify dependents of state changes           |
| Behavioral | Command                 | Encapsulate requests as objects              |
| Behavioral | State                   | Change behavior based on internal state      |
| Behavioral | Template Method         | Skeleton algorithm, customizable steps       |
| Behavioral | Chain of Responsibility | Pass request along a handler chain           |
| Behavioral | Iterator                | Sequential access without exposing structure |
| Behavioral | Mediator                | Centralize communication between objects     |
| Behavioral | Memento                 | Save/restore object state                    |
| Behavioral | Visitor                 | Add operations without modifying classes     |
| Behavioral | Interpreter             | Evaluate sentences in a defined grammar      |

---

*Reference guide covering all 23 Gang of Four (GoF) design patterns with Java implementations.*
