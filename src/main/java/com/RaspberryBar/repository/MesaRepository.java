package com.RaspberryBar.repository;

import com.RaspberryBar.entities.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Integer> {

    public boolean existsById(int mesaId);

    public List<Mesa> findById(int mesaId);

    @Query("select max(m.id) from Mesa m")
    public Integer findMaxId();

    @Query("select m.nombreMesa from Mesa m")
    public List<String> readMesasName();

    @Query("select m.id from Mesa m where m.nombreMesa = :nombreMesa")
    public Integer findIdByNombreMesa(@Param("nombreMesa") String nombreMesa);

    @Query("select m from Mesa m where m.posicion = :posicion")
    public List<Mesa> findMesasByPosicion(@Param("posicion") String posicion);

    @Query("select m.nombreMesa from Mesa m where m.posicion = :posicion")
    public List<String> findNombreMesaByPosicion(@Param("posicion") String posicion);

}
