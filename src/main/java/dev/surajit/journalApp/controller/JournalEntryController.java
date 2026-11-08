package dev.surajit.journalApp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.surajit.journalApp.entity.JournalEntry;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/journal")
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntries = new HashMap<>();
   
    @GetMapping
    public List<JournalEntry> getAll() {
        return new ArrayList<>(journalEntries.values());
    }

    @GetMapping("id/{id}")
    public JournalEntry getById(@PathVariable Long id) {
        return journalEntries.get(id);
    }

    @PostMapping
    public Boolean create(@RequestBody JournalEntry myEntry) {
        journalEntries.put(myEntry.getId(), myEntry);
        return true;
    }

    @DeleteMapping("id/{id}")
    public Boolean deleteBYId(@PathVariable long id) {
        journalEntries.remove(id);
        return  true;
    }

    @PutMapping("id/{id}")
    public Boolean updateById(@PathVariable long id, @RequestBody JournalEntry newEntry) {
        journalEntries.put(id, newEntry);
        return true;
    }

}
