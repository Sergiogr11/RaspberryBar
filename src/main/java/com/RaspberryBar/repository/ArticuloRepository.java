package com.RaspberryBar.repository;

import com.RaspberryBar.entities.Articulo;
import com.RaspberryBar.entities.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Integer>{
    public boolean existsById(int articuloId);

    public List<Articulo> findById(int articuloId);

    @Query("select max(a.id) from Articulo a")
    public Integer findMaxId();

    @Query("select a.nombreArticulo from Articulo a where a.categoriaId = :categoriaId")
    public List<String> findbyCategoria(@Param("categoriaId") int categoriaId);

    @Query("select a.nombreArticulo from Articulo a")
    public List<String> readArticulosName();

    @Query("select a.id from Articulo a where a.nombreArticulo = :nombreArticulo")
    public Integer findIdByNombreArticulo(@Param("nombreArticulo") String nombreArticulo);

    @Query("select a.nombreArticulo from Articulo a where a.id = :articuloId")
    public String findNombreById(@Param("articuloId") int articuloId);

    @Query("select a from Articulo a where a.categoriaId = :categoriaId")
    public List<Articulo> findArticulosbyCategoria(@Param("categoriaId") int categoriaId);

}


