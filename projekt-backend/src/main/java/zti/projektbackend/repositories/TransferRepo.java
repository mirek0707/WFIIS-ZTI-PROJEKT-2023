package zti.projektbackend.repositories;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import zti.projektbackend.models.Transfer;

import java.util.List;

public interface TransferRepo extends MongoRepository<Transfer, String> {
    @NotNull
    @Query(value="{season: '?0'}", sort = "{date:  -1}")
    List<Transfer> findAllBySeason(String season);

    @Query(value="{'$or': [{ club_left: '?0' }, { club_joined: '?0' }], season: '?1'}", sort = "{date:  -1}")
    List<Transfer> findAllByClub(String club, String season);

    @Query(value="{player_id: '?0'}", sort = "{date:  -1}")
    List<Transfer> findAllByPlayer_id(String player_id);


    @Query(sort = "{date:  1}")
    List<Transfer> findAllByOrderByDateDesc();

}
