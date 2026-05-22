package in.kevinlobo.journalApp.service;

import in.kevinlobo.journalApp.entity.JournalEntry;
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

    public void saveEntry(JournalEntry journalEntry) {
        try {
            journalEntry.setDate(LocalDateTime.now());
            journalEntryRepository.save(journalEntry);
        }catch (Exception e) {
            log.error("Exception ",e);
        }
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findEntryById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteEntryById(ObjectId id) {
        journalEntryRepository.deleteById(id);
    }
}
