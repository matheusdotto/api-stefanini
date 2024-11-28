package tech.buildrun.springsecurity.service;

import tech.buildrun.springsecurity.entities.Log;

import java.util.List;

public interface LogService {

    List<Log> getLogsByUser(String username);
    List<Log> getLogsByCep(String cep);
}
