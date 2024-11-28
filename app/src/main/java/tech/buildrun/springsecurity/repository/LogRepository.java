package tech.buildrun.springsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.buildrun.springsecurity.entities.Log;

import java.util.List;
import java.util.UUID;

public interface LogRepository extends JpaRepository<Log, Long> {

    @Query("SELECT l FROM tb_logs l WHERE l.user.userId = :userId")
    List<Log> findLogsByUserId(@Param("userId") UUID userId);


    List<Log> findByCep(String cep);
}
