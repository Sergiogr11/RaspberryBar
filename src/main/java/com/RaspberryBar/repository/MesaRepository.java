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

    @Query("select m from Mesa m where m.posicion = :posicion")
    public List<Mesa> findMesaByPosicion(@Param("posicion") String posicion);

}
