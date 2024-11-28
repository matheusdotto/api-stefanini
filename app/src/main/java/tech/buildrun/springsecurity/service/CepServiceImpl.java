package tech.buildrun.springsecurity.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tech.buildrun.springsecurity.entities.Log;
import tech.buildrun.springsecurity.repository.LogRepository;
import tech.buildrun.springsecurity.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class CepServiceImpl implements CepService {

    private final RestTemplate restTemplate;
    private final LogRepository logRepository;
    private final UserRepository userRepository;

    @Value("${cep.api.uri}")
    private String uriCep;

    @Value("${cep.gtw.id}")
    private String cepId;

    private static final String GTW_ID_HEADER = "gtw-id";

    public CepServiceImpl(RestTemplate restTemplate, LogRepository logRepository, UserRepository userRepository ) {
        this.restTemplate = restTemplate;
        this.logRepository = logRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public String getCep(String cep, HttpHeaders headers, String username) {
        if (cep == null || cep.isEmpty()) {
            throw new IllegalArgumentException("CEP não pode ser nulo ou vazio");
        }

        headers.add(GTW_ID_HEADER, cepId);

        String fullUrl = uriCep.endsWith("/") ? uriCep + cep : uriCep + "/" + cep;

        Map<String, String> response = restTemplate.getForObject(fullUrl, Map.class);

        String logradouro = response.get("logradouro");
        String bairro = response.get("bairro");
        String cidade = response.get("cidade");
        String uf = response.get("estado");

        var user = userRepository.findByUsername(username);

        Log log = new Log();
        log.setCep(cep);
        log.setLogradouro(logradouro);
        log.setBairro(bairro);
        log.setCidade(cidade);
        log.setUf(uf);
        log.setResultado(response.toString());
        log.setDataHora(LocalDateTime.now());
        log.setUser(user.orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado")));

        logRepository.save(log);

        return response.toString();
    }

}
