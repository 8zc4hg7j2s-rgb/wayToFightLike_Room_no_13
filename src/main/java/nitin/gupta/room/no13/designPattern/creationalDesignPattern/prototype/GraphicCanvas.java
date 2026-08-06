package nitin.gupta.room.no13.designPattern.creationalDesignPattern.prototype;

/**
 * The Prototype Design Pattern is a creational pattern used when object creation is expensive,
 * complex, or time-consuming, and you want to create new instances by cloning an existing instance
 * (the prototype) rather than using new to instantiate from scratch.
 */
import java.util.ArrayList;
import java.util.List;

public class GraphicCanvas implements Cloneable {
    private String name;
    private List<String> shapes;

    public GraphicCanvas(String name) {
        this.name = name;
        this.shapes = new ArrayList<>();
    }

    public void addShape(String shape) {
        this.shapes.add(shape);
    }

    public List<String> getShapes() { return shapes; }

    @Override
    public GraphicCanvas clone() {
        try {
            GraphicCanvas cloned = (GraphicCanvas) super.clone(); // Shallow copy
            // Deep copy mutable reference objects manually
            cloned.shapes = new ArrayList<>(this.shapes);
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning failed", e);
        }
    }

    @Override
    public String toString() {
        return "GraphicCanvas[Name='" + name + "', Shapes=" + shapes + "]";
    }
}