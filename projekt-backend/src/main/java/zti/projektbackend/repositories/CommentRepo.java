package zti.projektbackend.repositories;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import zti.projektbackend.models.Comment;

import java.util.List;

public interface CommentRepo extends MongoRepository<Comment, String> {

    @NotNull
    @Query(value="{transfer_id: '?0'}", sort = "{date:  -1}")
    List<Comment> findAllByTransfer_id(String transfer_id);
}
