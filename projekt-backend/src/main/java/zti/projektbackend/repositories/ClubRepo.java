package zti.projektbackend.repositories;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import zti.projektbackend.models.Club;

import java.util.List;


public interface ClubRepo extends MongoRepository<Club, String> {
    @NotNull
    @Query(value="{nation: ?0}", sort = "{name:  1}")
    List<Club> findAllByNation(String nation);

    @NotNull
    @Query(sort = "{name:  1}")
    List<Club> findAllByOrderByNameAsc();
}
