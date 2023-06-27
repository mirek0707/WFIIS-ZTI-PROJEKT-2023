package zti.projektbackend.repositories;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import zti.projektbackend.models.Player;

import java.util.List;

public interface PlayerRepo extends MongoRepository<Player, String> {
    @NotNull
    @Query(value="{nationality: '?0', position: '?1'}", sort = "{surname:  1}")
    List<Player> findAllByNationalityAndPosition(String nationality, String position);

    @NotNull
    @Query(value="{nationality: '?0'}", sort = "{surname:  1}")
    List<Player> findAllByNationality(String nationality);

    @NotNull
    @Query(value="{position: '?0'}", sort = "{surname:  1}")
    List<Player> findAllByPosition(String position);

    @Query(value="{club: '?0'}", sort = "{surname:  1}")
    List<Player> findAllByClub(String club);

    @NotNull
    @Query(sort = "{surname:  1}")
    List<Player> findAllByOrderBySurnameAsc();
}
