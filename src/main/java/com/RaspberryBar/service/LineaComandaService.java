package com.RaspberryBar.service;

import com.RaspberryBar.entities.Comanda;
import com.RaspberryBar.entities.LineaComanda;
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
            lineaComanda.getLineaComandaId().setNumeroLinea(null == lineaComandaRepository.findMaxId() ? 1 : lineaComandaRepository.findMaxId() + 1);
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
            List<LineaComanda> lineaComandas = lineaComandaRepository.findAll();
            lineaComandas.stream().forEach(s -> {
                LineaComanda lineaComandaToBeUpdate = lineaComandaRepository.findById(s.getLineaComandaId()).get();
                lineaComandaToBeUpdate.setLineaComandaId(s.getLineaComandaId());
                lineaComandaToBeUpdate.setCantidad(s.getCantidad());
                lineaComandaToBeUpdate.setPrecio(s.getPrecio());
                lineaComandaToBeUpdate.setArticuloId(s.getArticuloId());
                lineaComandaRepository.save(lineaComandaToBeUpdate);
            });
            return "Linea Comanda actualizada correctamente.";
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
    public int findMaxId(){
        Integer maxId = lineaComandaRepository.findMaxId();
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
    public void deleteAllByComanda(int comandaId){
        lineaComandaRepository.deleteAllByLineaComandaIdNumeroComanda(comandaId);
    }

}
