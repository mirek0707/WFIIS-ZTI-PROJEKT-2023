package zti.projektbackend.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zti.projektbackend.models.Club;
import zti.projektbackend.payload.response.MessageResponse;
import zti.projektbackend.repositories.ClubRepo;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class ClubController {

    @Autowired
    ClubRepo clubRepo;

    @PostMapping("/getClubs")
    public ResponseEntity<List<Club>> getClubs(@RequestBody Map map) {
        String nation = map.get("nation").toString();
        if (nation.isEmpty())
            return ResponseEntity.ok().body(clubRepo.findAllByOrderByNameAsc());
        else
            return ResponseEntity.ok().body(clubRepo.findAllByNation(nation));
    }


    @PostMapping("/addClub")
    public ResponseEntity<?> addClub(@Valid @RequestBody Club club){
        clubRepo.save(club);
        return ResponseEntity.ok(new MessageResponse("Club added successfully!"));
    }
}
