package com.RaspberryBar.service;

import com.RaspberryBar.entities.Categoria;
import com.RaspberryBar.entities.Mesa;
import com.RaspberryBar.entities.Usuario;
import com.RaspberryBar.repository.MesaRepository;
import com.RaspberryBar.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class MesaService {
    @Autowired
    private MesaRepository mesaRepository;

    @Transactional
    public String createMesa(Mesa mesa){
        try {
            if (!mesaRepository.existsById(mesa.getMesaId())){
                mesa.setMesaId(null == mesaRepository.findMaxId()? 1 : mesaRepository.findMaxId() + 1);
                mesaRepository.save(mesa);
                return "Mesa guardada correctamente.";
            }else {
                return "La mesa ya existe en la base de datos.";
            }
        }catch (Exception e){
            throw e;
        }
    }

    public List<Mesa> readMesas(){
        return mesaRepository.findAll();
    }

    @Transactional
    public String updateMesa(Mesa mesa){
        if (mesaRepository.existsById(mesa.getMesaId())){
            try {
                    Mesa mesaToBeUpdate = mesaRepository.findById(mesa.getMesaId()).get(0);
                    mesaToBeUpdate.setMesaId(mesaToBeUpdate.getMesaId());
                    mesaToBeUpdate.setPosicion(mesa.getPosicion());
                    mesaToBeUpdate.setCapacidad(mesa.getCapacidad());
                    mesaToBeUpdate.setEstadoMesa(mesa.getEstadoMesa());
                    mesaToBeUpdate.setNombreMesa(mesa.getNombreMesa());
                return "Mesa actualizada correctamente.";
            }catch (Exception e){
                throw e;
            }
        }else {
            return "La mesa no existe en la base de datos.";
        }
    }

    @Transactional
    public String deleteMesa(Mesa mesa){
        if (mesaRepository.existsById(mesa.getMesaId())){
            try {
                List<Mesa> mesas = mesaRepository.findById(mesa.getMesaId());
                mesas.stream().forEach(s -> {
                    mesaRepository.delete(s);
                });
                return "Mesa eliminada correctamente.";
            }catch (Exception e){
                throw e;
            }

        }else {
            return "La mesa no existe";
        }
    }

    @Transactional
    public Mesa findMesa(int mesaId){
        return this.mesaRepository.findById(mesaId).get(0);
    }

    @Transactional
    public List<String> readMesasName(){
        return this.mesaRepository.readMesasName();
    }

    @Transactional
    public Integer findIdByNombreMesa(String nombreMesa){
        return mesaRepository.findIdByNombreMesa(nombreMesa);
    }

    @Transactional
    public List<Mesa> findMesaByPosicion(String posicion){
        return mesaRepository.findMesaByPosicion(posicion);
    }

    @Transactional
    public List<String> findNombreMesaByPosicion(String posicion){
        return mesaRepository.findNombreMesaByPosicion(posicion);
    }

}
