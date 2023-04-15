package com.RaspberryBar.service;

import com.RaspberryBar.entities.Articulo;
import com.RaspberryBar.entities.Factura;
import com.RaspberryBar.repository.ArticuloRepository;
import com.RaspberryBar.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ArticuloService {
    @Autowired
    private ArticuloRepository articuloRepository;

    @Transactional
    public String createArticulo(Articulo articulo){
        try {
            if (!articuloRepository.existsById(articulo.getArticuloId())){
                articulo.setArticuloId(null == articuloRepository.findMaxId()? 0 : articuloRepository.findMaxId() + 1);
                articuloRepository.save(articulo);
                return "Articulo guardado correctamente.";
            }else {
                return "El articulo ya existe en la base de datos.";
            }
        }catch (Exception e){
            throw e;
        }
    }

    public List<Articulo> readArticulos(){
        return articuloRepository.findAll();
    }

    @Transactional
    public String updateArticulo(Articulo articulo){
        if (articuloRepository.existsById(articulo.getArticuloId())){
            try {

                List<Articulo> articulos = articuloRepository.findById(articulo.getArticuloId());
                articulos.stream().forEach(s -> {
                    Articulo articuloToBeUpdate = articuloRepository.findById(s.getArticuloId()).get(0);
                    articuloToBeUpdate.setArticuloId(s.getArticuloId());
                    articuloToBeUpdate.setNombreArticulo(s.getNombreArticulo());
                    articuloToBeUpdate.setDescripcionArticulo(s.getDescripcionArticulo());
                    articuloToBeUpdate.setPrecio(s.getPrecio());
                    articuloToBeUpdate.setCategoriaId(s.getCategoriaId());

                    articuloRepository.save(articuloToBeUpdate);
                });
                return "Articulo actualizado correctamente.";
            }catch (Exception e){
                throw e;
            }
        }else {
            return "El articulo no existe.";
        }
    }

    @Transactional
    public String deleteArticulo(Articulo articulo){
        if (articuloRepository.existsById(articulo.getArticuloId())){
            try {
                List<Articulo> articulos = articuloRepository.findById(articulo.getArticuloId());
                articulos.stream().forEach(s -> {
                    articuloRepository.delete(s);
                });
                return "Articulo eliminado correctamente.";
            }catch (Exception e){
                throw e;
            }

        }else {
            return "El articulo no existe";
        }
    }
}
