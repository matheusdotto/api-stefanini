package tech.buildrun.springsecurity.model.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
