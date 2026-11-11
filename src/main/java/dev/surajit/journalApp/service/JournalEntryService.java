package dev.surajit.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.surajit.journalApp.entity.JournalEntry;
import dev.surajit.journalApp.repository.JournalEntryRepository;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntry journalEntry) {
        try {
            journalEntry.setDate(LocalDateTime.now());
            journalEntryRepository.save(journalEntry);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<JournalEntry> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findEntryById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteEntryById(ObjectId id) {
        journalEntryRepository.deleteById(id);
    }

    public  void updateEntryById(JournalEntry newEntry) {
        journalEntryRepository.save(newEntry);
    }
}
