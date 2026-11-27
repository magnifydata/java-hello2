package com.example.demo;

import com.example.demo.model.Excuse;
import com.example.demo.service.ExcuseService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
public class HelloController {

    private final ExcuseService excuseService;

    public HelloController(ExcuseService excuseService) {
        this.excuseService = excuseService;
    }

    @GetMapping("/excuse")
    public Map<String, Object> getExcuse(@RequestParam(defaultValue = "Developer") String name) {
        return excuseService.getRandomExcuse(name);
    }

    @GetMapping("/excuses")
    public List<Excuse> getAllExcuses() {
        return excuseService.getAllExcuses();
    }

    @PostMapping("/excuse")
    public Map<String, Object> addExcuse(@RequestBody Map<String, String> payload) {
        return excuseService.addExcuse(payload.get("excuse"));
    }

    @DeleteMapping("/excuse/{id}")
    public Map<String, Object> deleteExcuse(@PathVariable Long id) {
        return excuseService.deleteExcuse(id);
    }
}
