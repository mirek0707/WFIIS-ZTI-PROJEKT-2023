package zti.projektbackend.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "transfers")
public class Transfer {
    @Id
    private String id;

    @NotBlank
    private String player_id;

    @NotBlank
    private String club_left;

    @NotBlank
    private String club_joined;

    @NotBlank
    private String type;

    private int fee;

    private LocalDateTime date;

    @NotBlank
    private String season;

    public Transfer() {
    }

    public Transfer(String id, String player_id, String club_left, String club_joined, String type, int fee, LocalDateTime date, String season) {
        this.id = id;
        this.player_id = player_id;
        this.club_left = club_left;
        this.club_joined = club_joined;
        this.type = type;
        this.fee = fee;
        this.date = date;
        this.season = season;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(String player_id) {
        this.player_id = player_id;
    }

    public String getClub_left() {
        return club_left;
    }

    public void setClub_left(String club_left) {
        this.club_left = club_left;
    }

    public String getClub_joined() {
        return club_joined;
    }

    public void setClub_joined(String club_joined) {
        this.club_joined = club_joined;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }
}
