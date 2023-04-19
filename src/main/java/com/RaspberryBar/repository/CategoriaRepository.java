package com.RaspberryBar.repository;


import com.RaspberryBar.entities.Categoria;
import com.RaspberryBar.entities.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

    public boolean existsById(int categoriaId);

    public List<Categoria> findById(int categoriaId);

    @Query("select max(s.id) from Categoria s")
    public Integer findMaxId();
}
