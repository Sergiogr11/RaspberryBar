package com.RaspberryBar.repository;

import com.RaspberryBar.entities.Articulo;
import com.RaspberryBar.entities.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Integer>{
    public boolean existsById(int articuloId);

    public List<Articulo> findById(int articuloId);

    @Query("select max(s.id) from Articulo s")
    public Integer findMaxId();
}


