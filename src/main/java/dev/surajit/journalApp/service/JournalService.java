package dev.surajit.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.surajit.journalApp.entity.JournalEntity;
import dev.surajit.journalApp.entity.UserEntity;
import dev.surajit.journalApp.repository.JournalRepository;

@Component
public class JournalService {

    @Autowired
    private JournalRepository journalEntryRepository;

    @Autowired 
    private UserService userService;

    public void saveEntry(JournalEntity journalEntry, String username) {
        try {
            UserEntity user = userService.findByUserName(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntity saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<JournalEntity> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntity> findEntryById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteEntryById(ObjectId id) {
        journalEntryRepository.deleteById(id);
    }

    public  void updateEntryById(JournalEntity newEntry) {
        journalEntryRepository.save(newEntry);
    }
}
