package com.RaspberryBar.repository;

import com.RaspberryBar.entities.Comanda;
import com.RaspberryBar.entities.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComandaRepository extends JpaRepository<Comanda, Integer> {

    public boolean existById(int numeroComanda);

    public List<Comanda> findById(int numeroComanda);

    @Query("select max(s.id) from Comanda s")
    public Integer findMaxId();
}
