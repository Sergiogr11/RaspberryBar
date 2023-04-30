package com.RaspberryBar.repository;

import com.RaspberryBar.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    public boolean existsByUsername(String username);

    public List<Usuario> findByUsername(String username);

    @Query("select max(s.id) from Usuario s")
    public Integer findMaxId();
}
