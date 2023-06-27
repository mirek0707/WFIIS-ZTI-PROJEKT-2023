package zti.projektbackend.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zti.projektbackend.models.Comment;
import zti.projektbackend.payload.response.MessageResponse;
import zti.projektbackend.repositories.CommentRepo;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class CommentController {

    @Autowired
    CommentRepo commentRepo;

    @PostMapping("/getComments")
    public ResponseEntity<List<Comment>> getComments(@RequestBody Map map) {
        String transfer_id = map.get("transfer_id").toString();
        return ResponseEntity.ok().body(commentRepo.findAllByTransfer_id(transfer_id));
    }


    @PostMapping("/addComment")
    public ResponseEntity<?> addClub(@Valid @RequestBody Comment comment){
        commentRepo.save(comment);
        return ResponseEntity.ok(new MessageResponse("Comment added successfully!"));
    }
}
