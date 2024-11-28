package tech.buildrun.springsecurity.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.buildrun.springsecurity.service.CepServiceImpl;

@RestController
public class CepController {

    private final CepServiceImpl cepService;

    public CepController(CepServiceImpl cepService) {
        this.cepService = cepService;
    }

    @GetMapping("/consulta-cep")
    public String consultaCep(@RequestParam String cep, @RequestHeader HttpHeaders headers, @RequestParam String username) {


        return cepService.getCep(cep, headers, username);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/cadastrar-cep")
    public String cadastrarCep(@RequestBody String cep) {
        return "CEP cadastrado com sucesso: " + cep;
    }
}
