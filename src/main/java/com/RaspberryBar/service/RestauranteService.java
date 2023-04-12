package com.RaspberryBar.service;

import com.RaspberryBar.entities.Factura;
import com.RaspberryBar.entities.Restaurante;
import com.RaspberryBar.repository.FacturaRepository;
import com.RaspberryBar.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RestauranteService {
    @Autowired
    private RestauranteRepository restauranteRepository;

    @Transactional
    public String createRestaurante(Restaurante restaurante){
        try {
            if (!restauranteRepository.existById(restaurante.getRestauranteId())){
                restaurante.setRestauranteId(null == restauranteRepository.findMaxId()? 0 : restauranteRepository.findMaxId() + 1);
                restauranteRepository.save(restaurante);
                return "Restaurante guardado correctamente.";
            }else {
                return "El restaurante ya existe en la base de datos.";
            }
        }catch (Exception e){
            throw e;
        }
    }

    public List<Restaurante> readRestaurante(){
        return restauranteRepository.findAll();
    }

    @Transactional
    public String updateRestaurante(Restaurante restaurante){
        if (restauranteRepository.existById(restaurante.getRestauranteId())){
            try {

                List<Restaurante> restaurantes = restauranteRepository.findById(restaurante.getRestauranteId());
                restaurantes.stream().forEach(s -> {
                    Restaurante restauranteToBeUpdate = restauranteRepository.findById(s.getRestauranteId()).get(0);
                    restauranteToBeUpdate.setRestauranteId(s.getRestauranteId());
                    restauranteToBeUpdate.setCif(s.getCif());
                    restauranteToBeUpdate.setDireccion(s.getDireccion());
                    restauranteToBeUpdate.setTelefono(s.getTelefono());
                    restauranteToBeUpdate.setTipoImpositivo(s.getTipoImpositivo());

                    restauranteRepository.save(restauranteToBeUpdate);
                });
                return "Restaurante actualizado correctamente.";
            }catch (Exception e){
                throw e;
            }
        }else {
            return "El restaurante no existe.";
        }
    }

    @Transactional
    public String deleteRestaurante(Restaurante restaurante){
        if (restauranteRepository.existsById(restaurante.getRestauranteId())){
            try {
                List<Restaurante> restaurantes = restauranteRepository.findById(restaurante.getRestauranteId());
                restaurantes.stream().forEach(s -> {
                    restauranteRepository.delete(s);
                });
                return "Restaurante eliminado correctamente.";
            }catch (Exception e){
                throw e;
            }

        }else {
            return "El restaurante no existe";
        }
    }
}
