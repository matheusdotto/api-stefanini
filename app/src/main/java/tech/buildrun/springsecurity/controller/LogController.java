package tech.buildrun.springsecurity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.buildrun.springsecurity.entities.Log;
import tech.buildrun.springsecurity.service.LogService;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @GetMapping("/user")
    public ResponseEntity<List<Log>> getLogsByUsername(@RequestParam String username) {
        List<Log> logs = logService.getLogsByUser(username);
        return ResponseEntity.ok(logs);
    }

    @GetMapping("/cep")
    public List<Log> getLogsByCep(@RequestParam String cep) {
        return logService.getLogsByCep(cep);
    }
}
