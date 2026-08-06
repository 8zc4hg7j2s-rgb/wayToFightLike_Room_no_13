package nitin.gupta.room.no13.coreJavaPrectise.Array;


class Student {
    String name;

    Student(String name) {
        this.name = name;
    }

    public void setName(String nameNew) {
        name = nameNew;
    }
}

public class PassByReference {
    public static void main(String[] args) {
        Student s1 = new Student("abc");
        Student s2 = new Student("def");
        swap(s1, s2);
        System.out.println(s1.name);
        System.out.println(s2.name);
    }

    static public void swap(Student student1, Student student2) {
        Student temp = student1;
        student1 = student2;
        student2 = temp;
    }
}

