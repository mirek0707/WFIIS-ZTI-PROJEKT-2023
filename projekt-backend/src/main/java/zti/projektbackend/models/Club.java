package zti.projektbackend.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "clubs")
public class Club {
    @Id
    private String id;

    @NotBlank
    @Indexed(unique = true)
    private String name;

    @NotBlank
    private String nation;

    @NotBlank
    private String city;

    @Min(1800)
    private int year_founded;

    public Club() {
    }
    public Club(String id, String name, String nation, String city, int year_founded) {
        this.id = id;
        this.name = name;
        this.nation = nation;
        this.city = city;
        this.year_founded = year_founded;
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

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getYear_founded() {
        return year_founded;
    }

    public void setYear_founded(int year_founded) {
        this.year_founded = year_founded;
    }
}
