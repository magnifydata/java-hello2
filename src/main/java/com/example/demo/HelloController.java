package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import java.util.Random;

@RestController
public class HelloController {

    private final String[] excuses = {
        "It works on my machine.",
        "That's a feature, not a bug.",
        "It must be a caching issue.",
        "The cloud provider is having a hiccup.",
        "We will fix that in the next release."
    };

    @GetMapping("/excuse")
    public Map<String, String> getExcuse(@RequestParam(defaultValue = "Developer") String name) {
        int index = new Random().nextInt(excuses.length);
        return Map.of(
            "status", "success",
            "for", name,
            "excuse", excuses[index]
        );
    }
}
