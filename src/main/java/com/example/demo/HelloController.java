package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
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
        int index = new Random().nextInt(excuses.size());
        return Map.of(
            "status", "success",
            "for", name,
            "excuse", excuses.get(index),
            "totalExcuses", excuses.size()
        );
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
}
