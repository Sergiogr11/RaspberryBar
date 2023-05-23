package com.RaspberryBar.service;

import com.RaspberryBar.entities.Comanda;
import com.RaspberryBar.entities.Factura;
import com.RaspberryBar.entities.Mesa;
import com.RaspberryBar.repository.ComandaRepository;
import com.RaspberryBar.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class ComandaService {

    @Autowired
    private ComandaRepository comandaRepository;

    @Transactional
    public String createComanda(Comanda comanda){
        try {
            if (!comandaRepository.existsById(comanda.getNumeroComanda())){
                comanda.setNumeroComanda(null == comandaRepository.findMaxId()? 1 : comandaRepository.findMaxId() + 1);
                comandaRepository.save(comanda);
                return "Comanda guardada correctamente.";
            }else {
                return "La Comanda ya existe en la base de datos.";
            }
        }catch (Exception e){
            throw e;
        }
    }

    @Transactional
    public List<Comanda> readComandas(){
        return comandaRepository.findAll();
    }

    @Transactional
    public String updateComanda(Comanda comanda){
        if (comandaRepository.existsById(comanda.getNumeroComanda())){
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

    @Transactional
    public int findMaxId(){
        Integer maxId = comandaRepository.findMaxId();
        return maxId != null ? maxId : 0;
    }

    @Transactional
    public Optional<Comanda> findComandaById(Integer comandaId){
        return comandaRepository.findById(comandaId);
    }

    @Transactional
    public void save(Comanda comanda){
        comandaRepository.save(comanda);
    }

    @Transactional
    public Comanda findLastComandaByMesa(int mesaId) {
        return comandaRepository.findLastComandaByMesa(mesaId);
    }
}
