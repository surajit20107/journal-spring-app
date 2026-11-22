package dev.surajit.journalApp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.surajit.journalApp.entity.JournalEntity;
import dev.surajit.journalApp.entity.UserEntity;
import dev.surajit.journalApp.service.JournalService;
import dev.surajit.journalApp.service.UserService;

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

    @Autowired
    private JournalService journalEntryService;

    @Autowired 
    private UserService userService;

    // get all journals of a user
    @GetMapping("/{username}")
    public ResponseEntity<List<JournalEntity>> getAllJournelEntriesOfUser(@PathVariable String username) {
        UserEntity user = userService.findByUserName(username);

        List<JournalEntity> all = user.getJournalEntries();

        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // get one journal by id
    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntity> getById(@PathVariable ObjectId id) {
        Optional<JournalEntity> journal = journalEntryService.findEntryById(id);

        if (journal.isPresent()) {
            return new ResponseEntity<>(journal.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // create a journal
    @PostMapping("/{username}")
    public ResponseEntity<JournalEntity> create(@RequestBody JournalEntity myEntry, @PathVariable String username) {
        try {
            journalEntryService.saveEntry(myEntry, username);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.printf(e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // deleteing a journal by id
    @DeleteMapping("/{username}/{id}")
    public ResponseEntity<?> deleteBYId(@PathVariable ObjectId id, @PathVariable String username) {
        journalEntryService.deleteEntryById(id, username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // update a journal by id
    @PutMapping("/{username}/{id}")
    public ResponseEntity<JournalEntity> updateById(@PathVariable ObjectId id, String username, @RequestBody JournalEntity newEntry) {
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
