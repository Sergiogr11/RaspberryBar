package com.RaspberryBar.repository;

import com.RaspberryBar.entities.Comanda;
import com.RaspberryBar.entities.LineaComanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineaComandaRepository extends JpaRepository<LineaComanda, Integer> {

    public boolean existById(int lineaComandaId);

    public List<LineaComanda> findById(int lineaComandaId);

    @Query("select max(s.id) from LineaComanda s")
    public Integer findMaxId();
}
