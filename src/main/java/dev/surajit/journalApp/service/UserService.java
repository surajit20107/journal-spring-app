package dev.surajit.journalApp.service;

import dev.surajit.journalApp.entity.JournalEntity;
import dev.surajit.journalApp.entity.UserEntity;
import dev.surajit.journalApp.repository.JournalRepository;
import dev.surajit.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void createUser(UserEntity user) {
        userRepository.save(user);
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public  Optional<UserEntity> findUserById(ObjectId id) {
        return userRepository.findById(id);
    }

    public  void deleteUserById(ObjectId id) {
        userRepository.deleteById(id);
    }

}
