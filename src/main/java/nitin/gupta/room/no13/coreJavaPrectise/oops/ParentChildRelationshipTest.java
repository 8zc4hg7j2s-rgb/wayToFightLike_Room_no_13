package nitin.gupta.room.no13.coreJavaPrectise.oops;

interface ISuper {
    public void display();
}

class SuperClass implements ISuper {
    public void displaySuper() {
        System.out.println("super display");
    }

    @Override
    public void display() {
        System.out.println("I super");
    }
}

class Subclass extends SuperClass {
    public void displaySub() {
        System.out.println("sub class  display");
    }
}

public class ParentChildRelationshipTest {
    static void main() {
        ParentChildRelationshipTest test = new ParentChildRelationshipTest();
        Subclass subclass = new Subclass();
        subclass.displaySuper();
        subclass.displaySub();
        subclass.display();
        ISuper iSuper = new Subclass();
        iSuper.display();
        // iSuper.displaySuper();
        ///iSuper.displaySub();
    }
}
