package in.kevinlobo.journalApp.service;

import in.kevinlobo.journalApp.entity.JournalEntry;
import in.kevinlobo.journalApp.entity.User;
import in.kevinlobo.journalApp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class JournalEntryService {


    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;


    public void saveEntry(JournalEntry journalEntry, String userName) {
            User user = userService.findUserByUsername(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry save = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(save);
            userService.saveEntry(user);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findEntryById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteEntryById(ObjectId id, String userName) {
        User user = userService.findUserByUsername(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id);
    }
}
