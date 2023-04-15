package com.RaspberryBar.repository;

import com.RaspberryBar.entities.Factura;
import com.RaspberryBar.entities.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Integer> {

        public boolean existsById(int facturaId);

        public List<Factura> findById(int facturaId);

        @Query("select max(s.id) from Factura s")
        public Integer findMaxId();
}
