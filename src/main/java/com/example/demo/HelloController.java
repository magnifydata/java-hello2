package com.example.demo;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
public class HelloController {

    private final List<String> excuses = new CopyOnWriteArrayList<>(List.of(
        "It works on my machine.",
        "That's a feature, not a bug.",
        "It must be a caching issue.",
        "The cloud provider is having a hiccup.",
        "We will fix that in the next release."
    ));

    @GetMapping("/excuse")
    public Map<String, Object> getExcuse(@RequestParam(defaultValue = "Developer") String name) {
        if (excuses.isEmpty()) {
            return Map.of(
                "status", "empty",
                "for", name,
                "excuse", "No excuses left! Add one below.",
                "totalExcuses", 0
            );
        }
        int index = new Random().nextInt(excuses.size());
        return Map.of(
            "status", "success",
            "for", name,
            "excuse", excuses.get(index),
            "totalExcuses", excuses.size()
        );
    }

    @GetMapping("/excuses")
    public List<String> getAllExcuses() {
        return excuses;
    }

    @PostMapping("/excuse")
    public Map<String, Object> addExcuse(@RequestBody Map<String, String> payload) {
        String newExcuse = payload.get("excuse");
        if (newExcuse == null || newExcuse.isBlank()) {
            return Map.of("status", "error", "message", "Excuse cannot be empty.");
        }
        excuses.add(newExcuse.trim());
        return Map.of(
            "status", "added",
            "excuse", newExcuse.trim(),
            "totalExcuses", excuses.size()
        );
    }

    @DeleteMapping("/excuse/{index}")
    public Map<String, Object> deleteExcuse(@PathVariable int index) {
        if (index < 0 || index >= excuses.size()) {
            return Map.of("status", "error", "message", "Invalid excuse index.");
        }
        String removed = excuses.remove(index);
        return Map.of(
            "status", "deleted",
            "removed", removed,
            "totalExcuses", excuses.size()
        );
    }
}
