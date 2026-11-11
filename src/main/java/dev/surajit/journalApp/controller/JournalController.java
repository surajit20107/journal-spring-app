package dev.surajit.journalApp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.surajit.journalApp.entity.JournalEntity;
import dev.surajit.journalApp.service.JournalService;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/journal")
public class JournalController {
//    create object of JournalEntryService class
    @Autowired
    private JournalService journalEntryService;

//    get all journals
    @GetMapping
    public ResponseEntity<List<JournalEntity>> getAll() {
        List<JournalEntity> all = journalEntryService.getAllEntries();

        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // get one journal by id
    @GetMapping("id/{id}")
    public ResponseEntity<JournalEntity> getById(@PathVariable ObjectId id) {
        Optional<JournalEntity> journal = journalEntryService.findEntryById(id);

        if (journal.isPresent()) {
            return new ResponseEntity<>(journal.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // create a journal
    @PostMapping
    public ResponseEntity<JournalEntity> create(@RequestBody JournalEntity myEntry) {
        try {
            journalEntryService.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.printf(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    deleteing a journal by id
    @DeleteMapping("id/{id}")
    public ResponseEntity<?> deleteBYId(@PathVariable ObjectId id) {
        journalEntryService.deleteEntryById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    update a journal by id
    @PutMapping("id/{id}")
    public ResponseEntity<JournalEntity> updateById(@PathVariable ObjectId id, @RequestBody JournalEntity newEntry) {
        JournalEntity oldEntry = journalEntryService.findEntryById(id).orElse(null);

        // putting the new values in the old entry if they are not null or empty, otherwise keeping the old values
        if (oldEntry != null) {
            oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle(): oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : oldEntry.getContent());
            journalEntryService.updateEntryById(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
