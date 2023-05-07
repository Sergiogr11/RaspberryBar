package com.RaspberryBar.service;

import com.RaspberryBar.entities.Categoria;
import com.RaspberryBar.entities.Factura;
import com.RaspberryBar.entities.Usuario;
import com.RaspberryBar.repository.CategoriaRepository;
import com.RaspberryBar.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public String createCategoria(Categoria categoria){
        try {
            if (!categoriaRepository.existsById(categoria.getCategoriaId())){
                categoria.setCategoriaId(null == categoriaRepository.findMaxId()? 1 : categoriaRepository.findMaxId() + 1);
                 categoriaRepository.save(categoria);
                return "Categoria guardada correctamente.";
            }else {
                return "La Categoria ya existe en la base de datos.";
            }
        }catch (Exception e){
            throw e;
        }
    }

    public List<Categoria> readCategoria(){
        return categoriaRepository.findAll();
    }

    @Transactional
    public String updateCategoria(Categoria categoria){
        if (categoriaRepository.existsById(categoria.getCategoriaId())){
            try {
                Categoria categoriaToBeUpdate = categoriaRepository.findById(categoria.getCategoriaId()).get(0);
                categoriaToBeUpdate.setCategoriaId(categoriaToBeUpdate.getCategoriaId());
                categoriaToBeUpdate.setNombreCategoria(categoria.getNombreCategoria());
                categoriaToBeUpdate.setDescripcionCategoria(categoria.getDescripcionCategoria());
                categoriaRepository.save(categoriaToBeUpdate);
                return "Categoria actualizado correctamente.";
            }catch (Exception e){
                throw e;
            }
        }else {
            return "La Categoria no existe.";
        }
    }

    @Transactional
    public String deleteCategoria(Categoria categoria){
        if (categoriaRepository.existsById(categoria.getCategoriaId())){
            try {
                List<Categoria> categorias = categoriaRepository.findById(categoria.getCategoriaId());
                categorias.stream().forEach(s -> {
                    categoriaRepository.delete(s);
                });
                return "Categoria eliminada correctamente.";
            }catch (Exception e){
                throw e;
            }

        }else {
            return "La Categoria no existe";
        }
    }

    @Transactional
    public Categoria findCategoria(int categoriaId){
        return this.categoriaRepository.findById(categoriaId).get(0);
    }

    @Transactional
    public List<String> readCategoriasName(){
        return this.categoriaRepository.readCategoriasName();
    }

    @Transactional
    public Integer findIdByNombreCategoria(String nombreCategoria){
        return categoriaRepository.findIdByNombreCategoria(nombreCategoria);
    }

}
