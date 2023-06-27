package zti.projektbackend.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "comments")
public class Comment {

    @Id
    private String id;

    @NotBlank
    private String transfer_id;

    @NotBlank
    private String user;

    @NotBlank
    @Size(max = 300)
    private String content;

    private LocalDateTime date;

    public Comment() {
    }

    public Comment(String id, String transfer_id, String user, String content, LocalDateTime date) {
        this.id = id;
        this.transfer_id = transfer_id;
        this.user = user;
        this.content = content;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getTransfer_id() {
        return transfer_id;
    }

    public String getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTransfer_id(String transfer_id) {
        this.transfer_id = transfer_id;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
