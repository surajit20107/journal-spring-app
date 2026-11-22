package dev.surajit.journalApp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import dev.surajit.journalApp.entity.JournalEntity;
import dev.surajit.journalApp.entity.UserEntity;
import dev.surajit.journalApp.repository.JournalRepository;

@Component
public class JournalService {

    @Autowired
    private JournalRepository journalEntryRepository;

    @Autowired 
    private UserService userService;

    @Transactional 
    public void saveEntry(JournalEntity journalEntry, String username) {
        try {
            UserEntity user = userService.findByUserName(username);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntity saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error saving entry:", e);
        }
    }

    // overloaded method of saveEntry
    public void saveEntry(JournalEntity journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntity> getAllEntries() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntity> findEntryById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteEntryById(ObjectId id, String username) {
        UserEntity user = userService.findByUserName(username);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveUser(user);
        journalEntryRepository.deleteById(id);
    }

    public  void updateEntryById(JournalEntity newEntry) {
        journalEntryRepository.save(newEntry);
    }
}
