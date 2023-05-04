package com.RaspberryBar.service;

import com.RaspberryBar.entities.Articulo;
import com.RaspberryBar.entities.Categoria;
import com.RaspberryBar.entities.Factura;
import com.RaspberryBar.repository.ArticuloRepository;
import com.RaspberryBar.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ArticuloService {
    @Autowired
    private ArticuloRepository articuloRepository;

    @Transactional
    public String createArticulo(Articulo articulo) {
        try {
            if (!articuloRepository.existsById(articulo.getArticuloId())) {
                articulo.setArticuloId(null == articuloRepository.findMaxId() ? 0 : articuloRepository.findMaxId() + 1);
                articuloRepository.save(articulo);
                return "Articulo guardado correctamente.";
            } else {
                return "El articulo ya existe en la base de datos.";
            }
        } catch (Exception e) {
            throw e;
        }
    }

    public List<Articulo> readArticulos() {
        return articuloRepository.findAll();
    }

    @Transactional
    public String updateArticulo(Articulo articulo) {
        if (articuloRepository.existsById(articulo.getArticuloId())) {
            try {
                    Articulo articuloToBeUpdate = articuloRepository.findById(articulo.getArticuloId()).get(0);
                    articuloToBeUpdate.setArticuloId(articuloToBeUpdate.getArticuloId());
                    articuloToBeUpdate.setNombreArticulo(articulo.getNombreArticulo());
                    articuloToBeUpdate.setDescripcionArticulo(articulo.getDescripcionArticulo());
                    articuloToBeUpdate.setPrecio(articulo.getPrecio());
                    articuloToBeUpdate.setCategoriaId(articulo.getCategoriaId());

                    articuloRepository.save(articuloToBeUpdate);
                    return "Articulo actualizado correctamente.";
            } catch (Exception e) {
                throw e;
            }
        } else {
            return "El articulo no existe.";
        }
    }

    @Transactional
    public String deleteArticulo(Articulo articulo) {
        if (articuloRepository.existsById(articulo.getArticuloId())) {
            try {
                List<Articulo> articulos = articuloRepository.findById(articulo.getArticuloId());
                articulos.stream().forEach(s -> {
                    articuloRepository.delete(s);
                });
                return "Articulo eliminado correctamente.";
            } catch (Exception e) {
                throw e;
            }

        } else {
            return "El articulo no existe";
        }
    }

    @Transactional
    public Articulo findArticulo(int articuloId) {
        return this.articuloRepository.findById(articuloId).get(0);
    }

    @Transactional
    public List<String> findbyCategoria(int categoriaId) {
        return this.articuloRepository.findbyCategoria(categoriaId);
    }

    @Transactional
    public List<String> readNameArticulos() {
        return articuloRepository.readArticulosName();
    }

    @Transactional
    public Integer findIdByNombreArticulo(String nombreArticulo) {
        return articuloRepository.findIdByNombreArticulo(nombreArticulo);
    }

    @Transactional
    public boolean existeArticulo(Integer articuloId){
        Optional<Articulo> optionalArticulo = articuloRepository.findById(articuloId);
        if (optionalArticulo.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

}
