package com.RaspberryBar.repository;

import com.RaspberryBar.entities.LineaComanda;
import com.RaspberryBar.entities.LineaComandaDTO;
import com.RaspberryBar.entities.LineaComandaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LineaComandaRepository extends JpaRepository<LineaComanda, LineaComandaId> {

    boolean existsById(LineaComandaId lineaComandaId);

    Optional<LineaComanda> findById(LineaComandaId lineaComandaId);

    @Query("select max(lc.lineaComandaId.numeroLinea) from LineaComanda lc WHERE lc.lineaComandaId.numeroComanda = :numeroComanda")
    Integer findMaxId(@Param("numeroComanda") int numeroComanda);

    @Query("SELECT lc FROM LineaComanda lc WHERE lc.lineaComandaId.numeroComanda = :numeroComanda")
    List<LineaComanda> findAllByNumeroComanda(@Param("numeroComanda") int numeroComanda);

    @Modifying
    @Query("DELETE FROM LineaComanda l WHERE l.lineaComandaId.numeroComanda = :numeroComanda")
    void deleteAllByLineaComandaIdNumeroComanda(@Param("numeroComanda") int numeroComanda);

    @Query("SELECT new com.RaspberryBar.entities.LineaComandaDTO(lc, a.nombreArticulo) " +
            "FROM LineaComanda lc " +
            "JOIN Articulo a ON lc.articuloId = a.articuloId " +
            "WHERE lc.lineaComandaId.numeroComanda = :numeroComanda")
    List<LineaComandaDTO> findAllWithNombreArticulo(@Param("numeroComanda") int numeroComanda);

    //PRUEBA
    @Query("SELECT new com.RaspberryBar.entities.LineaComandaDTO(lc, a.nombreArticulo) " +
            "FROM LineaComanda lc " +
            "JOIN Articulo a ON lc.articuloId = a.articuloId " +
            "JOIN Categoria c ON a.categoriaId = c.categoriaId " +
            "WHERE c.nombreCategoria = :nombreCategoria AND lc.lineaComandaId.numeroComanda = :numeroComanda")
    List<LineaComandaDTO> findByComandaAndCategoriaNombre(@Param("numeroComanda") Integer numeroComanda,
                                                       @Param("nombreCategoria") String nombreCategoria);
}
