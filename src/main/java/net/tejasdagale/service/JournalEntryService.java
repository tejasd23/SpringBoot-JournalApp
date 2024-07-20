package net.tejasdagale.service;

import lombok.extern.slf4j.Slf4j;
import net.tejasdagale.entity.JournalEntry;
import net.tejasdagale.entity.User;
import net.tejasdagale.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Component
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try {
            User user = userService.findByUserName(userName);
            JournalEntry saved = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveEntry(user);
        }catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occured while saving the entry");
        }
//        try {
//            journalEntry.setDate(LocalDateTime.now());
//            journalEntryRepository.save(journalEntry);
//        }catch (Exception e){
//            log.error("Exception ",e);
//        }

    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

        public List<JournalEntry> findAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }

    public void deleteById(ObjectId id, String userName){
         User user = userService.findByUserName(userName);
         user.getJournalEntries().removeIf(x -> x.getId().equals(id));
         userService.saveEntry(user);
         journalEntryRepository.deleteById(id);
    }
}



//controller --> service --> interface