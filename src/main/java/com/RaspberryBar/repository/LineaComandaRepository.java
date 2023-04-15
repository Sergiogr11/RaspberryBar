package com.RaspberryBar.repository;

import com.RaspberryBar.entities.LineaComanda;
import com.RaspberryBar.entities.LineaComandaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LineaComandaRepository extends JpaRepository<LineaComanda, LineaComandaId> {

    public boolean existsById(LineaComandaId lineaComandaId);

    public Optional<LineaComanda> findById(LineaComandaId lineaComandaId);

    @Query("select max(s.numeroLinea) from LineaComanda s where s.numeroComanda = numeroComanda")
    public Integer findMaxId();
}
