package com.RaspberryBar.repository;

import com.RaspberryBar.entities.LineaComanda;
import com.RaspberryBar.entities.LineaComandaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LineaComandaRepository extends JpaRepository<LineaComanda, LineaComandaId> {

    boolean existsById(LineaComandaId lineaComandaId);

    Optional<LineaComanda> findById(LineaComandaId lineaComandaId);

    @Query("select max(l.lineaComandaId.numeroLinea) from LineaComanda l")
    Integer findMaxId();

    @Query("SELECT lc FROM LineaComanda lc WHERE lc.lineaComandaId.numeroComanda = :numeroComanda")
    List<LineaComanda> findAllByNumeroComanda(@Param("numeroComanda") int numeroComanda);

    @Query("DELETE FROM LineaComanda l WHERE l.lineaComandaId.numeroComanda = :comandaId")
    void deleteAllByLineaComandaIdNumeroComanda(@Param("comandaId") int comandaId);


}
