package com.RaspberryBar.repository;

import com.RaspberryBar.entities.LineaComanda;
import com.RaspberryBar.entities.LineaComandaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LineaComandaRepository extends JpaRepository<LineaComanda, LineaComandaId> {

    boolean existsById(LineaComandaId lineaComandaId);

    Optional<LineaComanda> findById(LineaComandaId lineaComandaId);

    @Query("select max(l.lineaComandaId.numeroLinea) from LineaComanda l")
    Integer findMaxId();

}
