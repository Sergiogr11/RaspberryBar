package com.RaspberryBar.service;

import com.RaspberryBar.entities.Comanda;
import com.RaspberryBar.entities.LineaComanda;
import com.RaspberryBar.entities.LineaComandaId;
import com.RaspberryBar.repository.ComandaRepository;
import com.RaspberryBar.repository.LineaComandaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


//TODO
@Service
public class LineaComandaService {
    @Autowired
    private LineaComandaRepository lineaComandaRepository;

    @Transactional
    public String createLineaComanda(LineaComanda lineaComanda){
        try {
            if (!lineaComandaRepository.existById(lineaComanda.get())){
                lineaComanda.setNumeroComanda(null == comandaRepository.findMaxId()? 0 : comandaRepository.findMaxId() + 1);
                comandaRepository.save(comanda);
                return "Comanda guardada correctamente.";
            }else {
                return "La Comanda ya existe en la base de datos.";
            }
        }catch (Exception e){
            throw e;
        }
    }

    public List<Comanda> readComandas(){
        return comandaRepository.findAll();
    }

    @Transactional
    public String updateComanda(Comanda comanda){
        if (comandaRepository.existById(comandaId.getNumeroComanda())){
            try {

                List<Comanda> comandas = comandaRepository.findById(comanda.getNumeroComanda());
                comandas.stream().forEach(s -> {
                    Comanda comandaToBeUpdate = comandaRepository.findById(s.getNumeroComanda()).get(0);
                    comandaToBeUpdate.setNumeroComanda(s.getNumeroComanda());
                    comandaToBeUpdate.setMesaId(s.getMesaId());
                    comandaToBeUpdate.setNumeroComensales(s.getNumeroComanda());
                    comandaToBeUpdate.setFechaHoraApertura(s.getFechaHoraApertura());
                    comandaToBeUpdate.setFechaHoraCierre(s.getFechaHoraCierre());
                    comandaToBeUpdate.setPrecioTotal(s.getPrecioTotal());
                    comandaToBeUpdate.setUsuarioId(s.getUsuarioId());

                    comandaRepository.save(comandaToBeUpdate);
                });
                return "Comanda actualizado correctamente.";
            }catch (Exception e){
                throw e;
            }
        }else {
            return "La Comanda no existe.";
        }
    }

    @Transactional
    public String deleteComanda(Comanda comanda){
        if (comandaRepository.existsById(comanda.getNumeroComanda())){
            try {
                List<Comanda> comandas = comandaRepository.findById(comanda.getNumeroComanda());
                comandas.stream().forEach(s -> {
                    comandaRepository.delete(s);
                });
                return "Comanada eliminada correctamente.";
            }catch (Exception e){
                throw e;
            }

        }else {
            return "La Comanda no existe";
        }
    }
}
