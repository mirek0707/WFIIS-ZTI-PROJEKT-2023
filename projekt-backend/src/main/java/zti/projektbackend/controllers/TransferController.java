package zti.projektbackend.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zti.projektbackend.models.Transfer;
import zti.projektbackend.payload.response.MessageResponse;
import zti.projektbackend.repositories.TransferRepo;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TransferController {

    @Autowired
    TransferRepo transferRepo;

    @PostMapping("/getTransfers")
    public ResponseEntity<List<Transfer>> getTransfers(@RequestBody Map map) {
        String season = map.get("season").toString();
        if (season.isEmpty())
            return ResponseEntity.ok().body(transferRepo.findAllByOrderByDateDesc());
        else
            return ResponseEntity.ok().body(transferRepo.findAllBySeason(season));
    }

    @PostMapping("/getClubTransfers")
    public ResponseEntity<List<Transfer>> getClubTransfers(@RequestBody Map map) {
        String club = map.get("club").toString();
        String season = map.get("season").toString();
        return ResponseEntity.ok().body(transferRepo.findAllByClub(club, season));
    }

    @PostMapping("/getPlayerTransfers")
    public ResponseEntity<List<Transfer>> getPlayerTransfers(@RequestBody Map map) {
        String player_id = map.get("player_id").toString();
        return ResponseEntity.ok().body(transferRepo.findAllByPlayer_id(player_id));
    }

    @PostMapping("/addTransfer")
    public ResponseEntity<?> addTransfer(@Valid @RequestBody Transfer transfer){
        transferRepo.save(transfer);
        return ResponseEntity.ok(new MessageResponse("Transfer added successfully!"));
    }
}
