package com.RaspberryBar.repository;

import com.RaspberryBar.entities.Comanda;
import com.RaspberryBar.entities.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Integer> {
    public boolean existsById(int restauranteId);

    public List<Restaurante> findById(int restauranteId);

    @Query("select max(s.id) from Restaurante s")
    public Integer findMaxId();
}
