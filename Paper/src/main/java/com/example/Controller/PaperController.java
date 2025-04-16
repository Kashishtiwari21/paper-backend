package com.example.Controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Entity.Paper;
import com.example.Repository.PaperRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
@CrossOrigin(origins = "http://localhost:4200")  // ✅ CORS enabled for Angular
@Tag(name = "Paper Management", description = "Operations related to papers")
@RestController
@Tag(name = "Paper Management", description = "Operations related to papers")
public class PaperController {

    @Autowired
    private PaperRepository paperRepository;

    @GetMapping("/papers")
    @Operation(summary = "Get All Papers", description = "Retrieves all papers.")
    public List<Paper> getPapers() {
        return paperRepository.findAll();
    }

    @GetMapping("/papers/{id}")
    @Operation(summary = "Get Paper by ID", description = "Retrieves a paper by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval"),
            @ApiResponse(responseCode = "404", description = "Paper not found")
    })
    public ResponseEntity<Paper> getPaperById(@PathVariable("id") int id) {
        return paperRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/papers")
    @Operation(summary = "Create Paper", description = "Creates a new paper.")
    public ResponseEntity<Paper> addPaper(@RequestBody Paper paper) {
        // Ensure paperId is not manually set
        System.out.println("Paper ID: " + paper);
        Paper newPaper = new Paper(
                paper.getAuthorId(),
                paper.getTitle(),
                paper.getDetails(),
                paper.getContent(),
                paper.getCoAuthor(),
                paper.isAcceptance(),
                paper.getTopic()
        );
        Paper savedPaper = paperRepository.save(newPaper);
        return ResponseEntity.ok(savedPaper);
    }

    @PutMapping("/papers/{id}")
    @Operation(summary = "Update Paper", description = "Updates an existing paper.")
    public ResponseEntity<Paper> updatePaper(@PathVariable("id") int id, @RequestBody Paper paperDetails) {
        Optional<Paper> paperOptional = paperRepository.findById(id);
        if (paperOptional.isPresent()) {
            Paper paper = paperOptional.get();
            paper.setAuthorId(paperDetails.getAuthorId());
            paper.setTitle(paperDetails.getTitle());
            paper.setDetails(paperDetails.getDetails());
            paper.setContent(paperDetails.getContent());
            paper.setCoAuthor(paperDetails.getCoAuthor());
            paper.setAcceptance(paperDetails.isAcceptance());
            paper.setTopic(paperDetails.getTopic());
            return ResponseEntity.ok(paperRepository.save(paper));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/papers/{id}")
    @Operation(summary = "Partial Update Paper", description = "Updates specific fields of an existing paper.")
    public ResponseEntity<Paper> patchPaper(@PathVariable("id") int id, @RequestBody Map<String, Object> updates) {
        Optional<Paper> existingPaperOptional = paperRepository.findById(id);
        if (existingPaperOptional.isPresent()) {
            Paper existingPaper = existingPaperOptional.get();
            updates.forEach((key, value) -> {
                switch (key) {
                    case "title":
                        existingPaper.setTitle((String) value);
                        break;
                    case "details":  // Fixed from "abstractText"
                        existingPaper.setDetails((String) value);
                        break;
                    case "authorId":
                        existingPaper.setAuthorId((Integer) value);
                        break;
                    case "topic":
                        existingPaper.setTopic((String) value);
                        break;
                }
            });
            return ResponseEntity.ok(paperRepository.save(existingPaper));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/papers/{id}")
    @Operation(summary = "Delete Paper", description = "Deletes a paper by its ID.")
    public ResponseEntity<String> deletePaper(@PathVariable("id") int id) {
        if (paperRepository.existsById(id)) {
            paperRepository.deleteById(id);
            return ResponseEntity.ok("Paper deleted with id: " + id);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
