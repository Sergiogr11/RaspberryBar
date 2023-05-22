package com.RaspberryBar.repository;

import com.RaspberryBar.entities.Comanda;
import com.RaspberryBar.entities.Factura;
import com.RaspberryBar.entities.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComandaRepository extends JpaRepository<Comanda, Integer> {

    public boolean existsById(int numeroComanda);

    public List<Comanda> findById(int numeroComanda);

    @Query("select max(c.id) from Comanda c")
    public Integer findMaxId();

    @Query(value = "SELECT * FROM Comanda WHERE MesaId = :mesaId ORDER BY FechaHoraApertura DESC LIMIT 1", nativeQuery = true)
    public Comanda findLastComandaByMesa(int mesaId);

}
