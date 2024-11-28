package tech.buildrun.springsecurity.annotations;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import tech.buildrun.springsecurity.entities.User;

@Operation(
        summary = "Lista todos os usuários",
        description = "Este endpoint retorna a lista de todos os usuários cadastrados.",
        responses = {
                @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso",
                        content = @Content(mediaType = "application/json",
                                schema = @Schema(implementation = User.class))),
                @ApiResponse(responseCode = "403", description = "Acesso negado"),
                @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
        }
)
public @interface PostListUser {
}
