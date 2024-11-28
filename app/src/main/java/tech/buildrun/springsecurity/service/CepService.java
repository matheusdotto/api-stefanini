package tech.buildrun.springsecurity.service;

import org.springframework.http.HttpHeaders;

public interface CepService {

    String getCep(String cep, HttpHeaders httpHeaders, String username);
}
