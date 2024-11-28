package tech.buildrun.springsecurity.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "tb_logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "type")
    private String type;

    private String cep;
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;

    private String details;

    @Lob
    private String resultado;

    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
