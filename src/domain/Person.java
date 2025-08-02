package domain;

import java.util.Objects;

public abstract class Person {
    private String name;
    private String cpf;
    private String phoneNumber;

    public Person(String name) {
        this.name = name;
    }

    public Person(String name, String cpf) {
        this(name);
        this.cpf = cpf;
    }

    public Person(String name, String cpf, String phoneNumber) {
        this(name, cpf);
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Person person = (Person) object;
        return Objects.equals(getName(), person.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
