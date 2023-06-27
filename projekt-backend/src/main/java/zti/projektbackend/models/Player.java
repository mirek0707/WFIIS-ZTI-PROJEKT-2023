package zti.projektbackend.models;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Document(collection = "players")
public class Player {
    @Id
    private String id;

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    private String known_as;

    @NotBlank
    private String nationality;

    @NotBlank
    private String city_of_birth;

    private LocalDateTime date_of_birth;

    @NotBlank
    private String position;

    @NotBlank
    private String club;

    @Min(0)
    @Max(99)
    private int number;

    @Min(150)
    @Max(230)
    private int height;

    @Min(50)
    @Max(120)
    private int weight;

    public Player() {
    }

    public Player(String id, String name, String surname, String known_as, String nationality, String city_of_birth, LocalDateTime date_of_birth, String position, String club, int number, int height, int weight) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.known_as = known_as;
        this.nationality = nationality;
        this.city_of_birth = city_of_birth;
        this.date_of_birth = date_of_birth;
        this.position = position;
        this.club = club;
        this.number = number;
        this.height = height;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getKnown_as() {
        return known_as;
    }

    public void setKnown_as(String known_as) {
        this.known_as = known_as;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getCity_of_birth() {
        return city_of_birth;
    }

    public void setCity_of_birth(String city_of_birth) {
        this.city_of_birth = city_of_birth;
    }

    public LocalDateTime getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDateTime date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
