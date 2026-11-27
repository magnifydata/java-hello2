package com.example.demo.service;

import com.example.demo.model.Excuse;
import com.example.demo.repository.ExcuseRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class ExcuseService {

    private final ExcuseRepository excuseRepository;
    private final Random random = new Random();

    public ExcuseService(ExcuseRepository excuseRepository) {
        this.excuseRepository = excuseRepository;
    }

    @PostConstruct
    public void seedDefaultExcuses() {
        if (excuseRepository.count() == 0) {
            excuseRepository.saveAll(List.of(
                new Excuse("It works on my machine."),
                new Excuse("That's a feature, not a bug."),
                new Excuse("It must be a caching issue."),
                new Excuse("The cloud provider is having a hiccup."),
                new Excuse("We will fix that in the next release.")
            ));
        }
    }

    public Map<String, Object> getRandomExcuse(String name) {
        List<Excuse> all = excuseRepository.findAll();
        if (all.isEmpty()) {
            return Map.of(
                "status", "empty",
                "for", name,
                "excuse", "No excuses left in MySQL! Add one below.",
                "totalExcuses", 0
            );
        }
        Excuse picked = all.get(random.nextInt(all.size()));
        return Map.of(
            "status", "success",
            "for", name,
            "excuse", picked.getText(),
            "totalExcuses", all.size()
        );
    }

    public List<Excuse> getAllExcuses() {
        return excuseRepository.findAll();
    }

    public Map<String, Object> addExcuse(String rawText) {
        if (rawText == null || rawText.isBlank()) {
            return Map.of("status", "error", "message", "Excuse cannot be empty.");
        }
        Excuse saved = excuseRepository.save(new Excuse(rawText.trim()));
        return Map.of(
            "status", "added",
            "id", saved.getId(),
            "excuse", saved.getText(),
            "totalExcuses", excuseRepository.count()
        );
    }

    public Map<String, Object> deleteExcuse(Long id) {
        Optional<Excuse> target = excuseRepository.findById(id);
        if (target.isEmpty()) {
            return Map.of("status", "error", "message", "Excuse ID not found.");
        }
        excuseRepository.deleteById(id);
        return Map.of(
            "status", "deleted",
            "removed", target.get().getText(),
            "totalExcuses", excuseRepository.count()
        );
    }
}
