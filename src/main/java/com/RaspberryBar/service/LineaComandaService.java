package com.RaspberryBar.service;

import com.RaspberryBar.entities.Comanda;
import com.RaspberryBar.entities.LineaComanda;
import com.RaspberryBar.entities.LineaComandaDTO;
import com.RaspberryBar.entities.LineaComandaId;
import com.RaspberryBar.repository.ComandaRepository;
import com.RaspberryBar.repository.LineaComandaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class LineaComandaService {

    @Autowired
    private LineaComandaRepository lineaComandaRepository;
    @Autowired
    private ComandaRepository comandaRepository;

    @Transactional
    public String createLineaComanda(LineaComanda lineaComanda){
        LineaComandaId id = lineaComanda.getLineaComandaId();
        if (!lineaComandaRepository.existsById(id)){
            lineaComanda.getLineaComandaId().setNumeroLinea(null == lineaComandaRepository.findMaxId(lineaComanda.getNumeroComanda()) ? 1 : lineaComandaRepository.findMaxId(lineaComanda.getNumeroComanda()) + 1);
            lineaComandaRepository.save(lineaComanda);
            return "Linea de comanda guardada correctamente.";
        }else {
            return "La Linea de Comanda ya existe en la base de datos.";
        }
    }

    public List<LineaComanda> readLineaComandas(){
        return lineaComandaRepository.findAll();
    }

    @Transactional
    public String updateLineaComanda(LineaComanda lineaComanda){
        LineaComandaId id = lineaComanda.getLineaComandaId();
        if (lineaComandaRepository.existsById(id)){
            LineaComanda lineaComandaToBeUpdate = lineaComandaRepository.findById(id).orElse(null);
            if(lineaComandaToBeUpdate != null) {
                lineaComandaToBeUpdate.setCantidad(lineaComanda.getCantidad());
                lineaComandaToBeUpdate.setPrecio(lineaComanda.getPrecio());
                lineaComandaToBeUpdate.setArticuloId(lineaComanda.getArticuloId());
                lineaComandaRepository.save(lineaComandaToBeUpdate);
                return "Linea Comanda actualizada correctamente.";
            } else {
                return "Error al obtener Linea Comanda para actualizar.";
            }
        }else {
            return "La Linea de Comanda no existe.";
        }
    }

    @Transactional
    public String deleteLineaComanda(LineaComanda lineaComanda) {
        LineaComandaId id = lineaComanda.getLineaComandaId();
        if (lineaComandaRepository.existsById(id)){
            Optional<LineaComanda> lineaComandas = lineaComandaRepository.findById(id);
            lineaComandaRepository.delete(lineaComandas.get());
            return "Linea Comanda eliminada correctamente.";
        } else {
            return "La Linea Comanda no existe";
        }
    }

    @Transactional
    public int findMaxId(int comandaId){
        Integer maxId = lineaComandaRepository.findMaxId(comandaId);
        if(maxId == null){
            maxId = 1;
        }
        return maxId;
    }

    @Transactional
    public List<LineaComanda> findAllByNumeroComanda (Integer numeroComanda){
        return lineaComandaRepository.findAllByNumeroComanda(numeroComanda);
    }

    @Modifying
    @Transactional
    public void deleteAllByComanda(int numeroComanda){
        lineaComandaRepository.deleteAllByLineaComandaIdNumeroComanda(numeroComanda);
    }

    @Transactional
    public List<LineaComandaDTO> findAllWithNombreArticulo(int numeroComanda){
        return lineaComandaRepository.findAllWithNombreArticulo(numeroComanda);
    }

    @Transactional
    public List<LineaComandaDTO> findByComandaAndCategoriaNombre(Integer numeroComanda, String nombreCategoria){
        return lineaComandaRepository.findByComandaAndCategoriaNombre(numeroComanda, nombreCategoria);
    }


}
