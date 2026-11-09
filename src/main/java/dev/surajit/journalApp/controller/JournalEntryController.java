package dev.surajit.journalApp.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.surajit.journalApp.entity.JournalEntry;
import dev.surajit.journalApp.service.JournalEntryService;

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
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;
   
    @GetMapping
    public List<JournalEntry> getAll() {
        return journalEntryService.getAllEntries();
    }

    @GetMapping("id/{id}")
    public JournalEntry getById(@PathVariable ObjectId id) {
        return journalEntryService.findEntryById(id).orElse(null);
    }

    @PostMapping
    public JournalEntry create(@RequestBody JournalEntry myEntry) {
        myEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(myEntry);
        return myEntry;
    }

    @DeleteMapping("id/{id}")
    public Boolean deleteBYId(@PathVariable ObjectId id) {
        journalEntryService.deleteEntryById(id);
        return true;
    }

    @PutMapping("id/{id}")
    public JournalEntry updateById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        JournalEntry oldEntry = journalEntryService.findEntryById(id).orElse(null);

        // putting the new values in the old entry if they are not null or empty, otherwise keeping the old values
        if (oldEntry != null) {
            oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle(): oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : oldEntry.getContent());
        }

        journalEntryService.updateEntryById(oldEntry);
        return oldEntry;
    }

}
