package com.RaspberryBar.service;

import com.RaspberryBar.entities.Comanda;
import com.RaspberryBar.entities.LineaComanda;
import com.RaspberryBar.repository.LineaComandaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class LineaComandaService {

    @Autowired
    private LineaComandaRepository lineaComandaRepository;

    @Transactional
    public String createLineaComanda(LineaComanda lineaComanda){
        try {
            if (!lineaComandaRepository.existsById(lineaComanda.getLineaComandaId())){
                lineaComanda.setNumeroComanda(null == lineaComandaRepository.findMaxId()? 0 : lineaComandaRepository.findMaxId() + 1);
                lineaComandaRepository.save(lineaComanda);
                return "Linea de comanda guardada correctamente.";
            }else {
                return "La Linea de Comanda ya existe en la base de datos.";
            }
        }catch (Exception e){
            throw e;
        }
    }

    public List<LineaComanda> readLineaComandas(){
        return lineaComandaRepository.findAll();
    }

    @Transactional
    public String updateLineaComanda(LineaComanda lineaComanda){
        if (lineaComandaRepository.existsById(lineaComanda.getLineaComandaId())){
            try {

                List<LineaComanda> lineaComandas = lineaComandaRepository.findAll();
                lineaComandas.stream().forEach(s -> {
                    LineaComanda lineaComandaToBeUpdate = lineaComandaRepository.findById(s.getLineaComandaId()).get();
                    lineaComandaToBeUpdate.setLineaComandaId(s.getLineaComandaId());
                    lineaComandaToBeUpdate.setNumeroLinea(s.getNumeroLinea());
                    lineaComandaToBeUpdate.setNumeroComanda(s.getNumeroComanda());
                    lineaComandaToBeUpdate.setCantidad(s.getCantidad());
                    lineaComandaToBeUpdate.setPrecio(s.getPrecio());
                    lineaComandaToBeUpdate.setArticuloId(s.getArticuloId());


                    lineaComandaRepository.save(lineaComandaToBeUpdate);
                });
                return "Linea Comanda actualizada correctamente.";
            }catch (Exception e){
                throw e;
            }
        }else {
            return "La Linea de Comanda no existe.";
        }
    }

    @Transactional
    public String deleteLineaComanda(LineaComanda lineaComanda) {
        if (lineaComandaRepository.existsById(lineaComanda.getLineaComandaId())) {
            try {
                Optional<LineaComanda> lineaComandas = lineaComandaRepository.findById(lineaComanda.getLineaComandaId());
                    lineaComandaRepository.delete(lineaComandas.get());
                return "Linea Comanda eliminada correctamente.";
            } catch (Exception e) {
                throw e;
            }

        } else {
            return "La Linea Comanda no existe";
        }
    }
}
