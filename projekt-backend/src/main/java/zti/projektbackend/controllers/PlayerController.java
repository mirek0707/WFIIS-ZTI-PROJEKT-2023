package zti.projektbackend.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zti.projektbackend.models.Player;
import zti.projektbackend.payload.response.MessageResponse;
import zti.projektbackend.repositories.PlayerRepo;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class PlayerController {

    @Autowired
    PlayerRepo playerRepo;

    @PostMapping("/getPlayers")
    public ResponseEntity<List<Player>> getPlayers(@RequestBody Map map) {
        String nationality = map.get("nationality").toString();
        String position = map.get("position").toString();
        if (nationality.isEmpty() && position.isEmpty())
            return ResponseEntity.ok().body(playerRepo.findAllByOrderBySurnameAsc());
        else if (position.isEmpty())
            return ResponseEntity.ok().body(playerRepo.findAllByNationality(nationality));
        else if (nationality.isEmpty())
            return ResponseEntity.ok().body(playerRepo.findAllByPosition(position));
        else
            return ResponseEntity.ok().body(playerRepo.findAllByNationalityAndPosition(nationality, position));
    }

    @PostMapping("/getPlayersFromClub")
    public ResponseEntity<List<Player>> getPlayersFromClub(@RequestBody Map map) {
        String club = map.get("club").toString();
        return ResponseEntity.ok().body(playerRepo.findAllByClub(club));
    }

    @PostMapping("/addPlayer")
    public ResponseEntity<?> addClub(@Valid @RequestBody Player player){
        playerRepo.save(player);
        return ResponseEntity.ok(new MessageResponse("Player added successfully!"));
    }
}
