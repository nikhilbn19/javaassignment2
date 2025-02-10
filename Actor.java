package org.example;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Actor {
    private String actorId;
    private String name;
    private LocalDate dateOfBirth;
    private String nationality;

    public Actor(String actorId, String name, String dateOfBirth, String nationality) {
        this.actorId = actorId;
        this.name = name;
        this.dateOfBirth = LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.nationality = nationality;
    }

    public String getActorId() {
        return actorId;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public String getNationality() {
        return nationality;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "actorId='" + actorId + '\'' +
                ", name='" + name + '\'' +
                ", age=" + getAge() +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
