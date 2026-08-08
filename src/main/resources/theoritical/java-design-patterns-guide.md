# Java Design Patterns: A Comprehensive Guide

Design patterns are formalized, reusable solutions to common problems encountered in software design and development. In
Java, design patterns help create flexible, maintainable, and modular object-oriented systems.

This guide provides an overview and detailed implementation details for the three classic categories of Gang of Four
(GoF) design patterns: **Creational**, **Structural**, and **Behavioral**.

---

## Table of Contents

1. [Overview of Design Pattern Categories](#overview-of-design-pattern-categories)
2. [1. Creational Design Patterns](#1-creational-design-patterns)
    - [Singleton](#11-singleton)
    - [Factory Method](#12-factory-method)
    - [Abstract Factory](#13-abstract-factory)
    - [Builder](#14-builder)
    - [Prototype](#15-prototype)
3. [2. Structural Design Patterns](#2-structural-design-patterns)
    - [Adapter](#21-adapter)
    - [Decorator](#22-decorator)
    - [Facade](#23-facade)
    - [Proxy](#24-proxy)
    - [Composite](#25-composite)
    - [Bridge](#26-bridge)
    - [Flyweight](#27-flyweight)
4. [3. Behavioral Design Patterns](#3-behavioral-design-patterns)
    - [Observer](#31-observer)
    - [Strategy](#32-strategy)
    - [Command](#33-command)
    - [State](#34-state)
    - [Chain of Responsibility](#35-chain-of-responsibility)
    - [Template Method](#36-template-method)
    - [Iterator](#37-iterator)
    - [Mediator](#38-mediator)
    - [Memento](#39-memento)
    - [Visitor](#310-visitor)
5. [Summary Matrix](#summary-matrix)

---

## Overview of Design Pattern Categories

| Category                | Primary Focus                           | Key Objective                                                                                       |
|:------------------------|:----------------------------------------|:----------------------------------------------------------------------------------------------------|
| **Creational Patterns** | Object creation mechanisms              | Isolate object creation logic from client code to increase flexibility and reuse.                   |
| **Structural Patterns** | Class and object composition            | Structure large hierarchies or assemble objects into larger structures while keeping them flexible. |
| **Behavioral Patterns** | Object communication and responsibility | Manage algorithms, relationships, and communication protocols between objects.                      |

---

## 1. Creational Design Patterns

Creational design patterns deal with object creation mechanisms, seeking to create objects in a manner suitable to the
situation.

### 1.1 Singleton

Ensures a class has only one instance and provides a global point of access to it.

```java
// Thread-safe Bill Pugh Singleton Implementation
public class Singleton {
    private Singleton() {}

    private static class SingletonHelper {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return SingletonHelper.INSTANCE;
    }
}
```

### 1.2 Factory Method

Defines an interface for creating an object, but lets subclasses decide which class to instantiate.

```java
interface Notification {
    void notifyUser();
}

class SMSNotification implements Notification {
    public void notifyUser() { System.out.println("Sending SMS..."); }
}

class EmailNotification implements Notification {
    public void notifyUser() { System.out.println("Sending Email..."); }
}

class NotificationFactory {
    public Notification createNotification(String channel) {
        if (channel == null || channel.isEmpty()) return null;
        switch (channel) {
            case "SMS": return new SMSNotification();
            case "EMAIL": return new EmailNotification();
            default: throw new IllegalArgumentException("Unknown channel: " + channel);
        }
    }
}
```

### 1.3 Abstract Factory

Provides an interface for creating families of related or dependent objects without specifying their concrete classes.

```java
interface Button { void render(); }
interface Checkbox { void render(); }

class WinButton implements Button { public void render() { System.out.println("Windows Button"); } }
class WinCheckbox implements Checkbox { public void render() { System.out.println("Windows Checkbox"); } }

interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

class WinFactory implements GUIFactory {
    public Button createButton() { return new WinButton(); }
    public Checkbox createCheckbox() { return new WinCheckbox(); }
}
```

### 1.4 Builder

Separates the construction of a complex object from its representation so that the same construction process can create
different representations.

```java
public class User {
    private final String firstName; // Required
    private final String lastName;  // Required
    private final int age;          // Optional
    private final String phone;        // Optional

    private User(UserBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.age = builder.age;
        this.phone = builder.phone;
    }

    public static class UserBuilder {
        private final String firstName;
        private final String lastName;
        private int age;
        private String phone;

        public UserBuilder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public UserBuilder age(int age) { this.age = age; return this; }
        public UserBuilder phone(String phone) { this.phone = phone; return this; }

        public User build() { return new User(this); }
    }
}
```

### 1.5 Prototype

Creates new objects by cloning an existing instance (the prototype) rather than constructing from scratch.

```java
public class Document implements Cloneable {
    private String title;

    public Document(String title) { this.title = title; }
    public void setTitle(String title) { this.title = title; }

    @Override
    public Document clone() {
        try {
            return (Document) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
```

---

## 2. Structural Design Patterns

Structural patterns explain how to assemble objects and classes into larger structures while keeping these structures
flexible and efficient.

### 2.1 Adapter

Allows incompatible interfaces to collaborate.

```java
interface Target { void request(); }

class Adaptee {
    public void specificRequest() { System.out.println("Adaptee request executed."); }
}

class Adapter implements Target {
    private final Adaptee adaptee;
    public Adapter(Adaptee adaptee) { this.adaptee = adaptee; }

    @Override
    public void request() { adaptee.specificRequest(); }
}
```

### 2.2 Decorator

Attaches additional responsibilities to an object dynamically without modifying its structure.

```java
interface Coffee { double getCost(); String getDescription(); }

class SimpleCoffee implements Coffee {
    public double getCost() { return 2.0; }
    public String getDescription() { return "Simple Coffee"; }
}

abstract class CoffeeDecorator implements Coffee {
    protected final Coffee decoratedCoffee;
    public CoffeeDecorator(Coffee coffee) { this.decoratedCoffee = coffee; }
    public double getCost() { return decoratedCoffee.getCost(); }
    public String getDescription() { return decoratedCoffee.getDescription(); }
}

class MilkDecorator extends CoffeeDecorator {
    public MilkDecorator(Coffee coffee) { super(coffee); }
    @Override public double getCost() { return super.getCost() + 0.5; }
    @Override public String getDescription() { return super.getDescription() + ", Milk"; }
}
```

### 2.3 Facade

Provides a simplified interface to a library, a framework, or any other complex set of classes.

```java
class CPU { void start() {} }
class Memory { void load() {} }

class ComputerFacade {
    private CPU cpu = new CPU();
    private Memory memory = new Memory();

    public void start() {
        cpu.start();
        memory.load();
    }
}
```

### 2.4 Proxy

Provides a surrogate or placeholder for another object to control access to it.

```java
interface Image { void display(); }

class RealImage implements Image {
    private String fileName;
    public RealImage(String fileName) { this.fileName = fileName; loadFromDisk(); }
    private void loadFromDisk() { System.out.println("Loading " + fileName); }
    public void display() { System.out.println("Displaying " + fileName); }
}

class ProxyImage implements Image {
    private RealImage realImage;
    private String fileName;

    public ProxyImage(String fileName) { this.fileName = fileName; }

    public void display() {
        if (realImage == null) realImage = new RealImage(fileName);
        realImage.display();
    }
}
```

### 2.5 Composite

Composes objects into tree structures to represent part-whole hierarchies. Clients treat individual objects and
compositions uniformly.

### 2.6 Bridge

Decouples an abstraction from its implementation so that the two can vary independently.

### 2.7 Flyweight

Minimizes memory usage by sharing as much data as possible with similar objects.

---

## 3. Behavioral Design Patterns

Behavioral design patterns are concerned with algorithms and the assignment of responsibilities between objects.

### 3.1 Observer

Defines a one-to-many dependency between objects so that when one object changes state, all its dependents are notified
automatically.

```java
import java.util.*;

interface Observer { void update(String message); }

class Subject {
    private final List<Observer> observers = new ArrayList<>();
    public void addObserver(Observer o) { observers.add(o); }
    public void notifyObservers(String message) {
        for (Observer o : observers) o.update(message);
    }
}
```

### 3.2 Strategy

Defines a family of algorithms, encapsulates each one, and makes them interchangeable at runtime.

```java
interface PaymentStrategy { void pay(int amount); }

class CreditCardStrategy implements PaymentStrategy {
    public void pay(int amount) { System.out.println("Paid " + amount + " via Credit Card."); }
}

class PayPalStrategy implements PaymentStrategy {
    public void pay(int amount) { System.out.println("Paid " + amount + " via PayPal."); }
}

class ShoppingCart {
    private PaymentStrategy strategy;
    public void setPaymentStrategy(PaymentStrategy strategy) { this.strategy = strategy; }
    public void checkout(int amount) { strategy.pay(amount); }
}
```

### 3.3 Command

Encapsulates a request as an object, thereby letting you parameterize clients with different requests, queue, or log
requests.

```java
interface Command { void execute(); }

class Light {
    public void turnOn() { System.out.println("Light ON"); }
}

class LightOnCommand implements Command {
    private final Light light;
    public LightOnCommand(Light light) { this.light = light; }
    public void execute() { light.turnOn(); }
}
```

### 3.4 State

Allows an object to alter its behavior when its internal state changes.

### 3.5 Chain of Responsibility

Passes requests along a chain of handlers. Upon receiving a request, each handler decides either to process it or to
pass it to the next handler in the chain.

### 3.6 Template Method

Defines the skeleton of an algorithm in a method, deferring some steps to subclasses.

### 3.7 Iterator

Provides a way to access the elements of an aggregate object sequentially without exposing its underlying
representation.

### 3.8 Mediator

Reduces chaotic dependencies between objects by restricting direct communications between them and forcing them to
collaborate only via a mediator object.

### 3.9 Memento

Captures and externalizes an object's internal state so that the object can be restored to this state later without
violating encapsulation.

### 3.10 Visitor

Separates an algorithm from an object structure on which it operates.

---

## Summary Matrix

| Pattern Name       | Category   | Primary Use Case                                                |
|:-------------------|:-----------|:----------------------------------------------------------------|
| **Singleton**      | Creational | Single instance throughout application (e.g., DB pool, Logger). |
| **Factory Method** | Creational | Delegating object instantiation to subclasses.                  |
| **Builder**        | Creational | Constructing complex objects with optional parameters.          |
| **Prototype**      | Creational | Copying existing objects without relying on concrete classes.   |
| **Adapter**        | Structural | Enabling incompatible interfaces to interact.                   |
| **Decorator**      | Structural | Dynamic extension of object behaviors without inheritance.      |
| **Facade**         | Structural | Simplifying interfaces for complex subsystems.                  |
| **Proxy**          | Structural | Access control, lazy initialization, caching.                   |
| **Observer**       | Behavioral | Event-driven notification systems.                              |
| **Strategy**       | Behavioral | Swappable algorithms or payment gateways.                       |
| **Command**        | Behavioral | Encapsulating requests, supporting undo/redo operations.        |
