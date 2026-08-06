package nitin.gupta.room.no13.designPattern.creationalDesignPattern.builder;

import java.util.UUID;

public class Student {
    private String name;
    private UUID id;
    private String subject;
    private String grade;

    private Student(Builder builder) {
        name = builder.name;
        id = builder.id;
        subject = builder.subject;
        grade = builder.grade;
    }



    public static final class Builder {
        private String name;
        private UUID id;
        private String subject;
        private String grade;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder id(UUID val) {
            id = val;
            return this;
        }

        public Builder subject(String val) {
            subject = val;
            return this;
        }

        public Builder grade(String val) {
            grade = val;
            return this;
        }

        public Student build() {
            return new Student(this);
        }
    }
}

class TestingBuilder{
    public static void main(String[] args) {
        Student ss222 = Student.Builder.builder().name("sdfsfd").build();
    }
}
