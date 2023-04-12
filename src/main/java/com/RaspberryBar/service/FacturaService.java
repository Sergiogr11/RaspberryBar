package com.RaspberryBar.service;

import com.RaspberryBar.entities.Factura;
import com.RaspberryBar.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class FacturaService {
    @Autowired
    private FacturaRepository facturaRepository;

    @Transactional
    public String createFactura(Factura factura){
        try {
            if (!facturaRepository.existsById(factura.getNumeroFactura())){
                factura.setNumeroFactura(null == facturaRepository.findMaxId()? 0 : facturaRepository.findMaxId() + 1);
                facturaRepository.save(factura);
                return "Factura guardada correctamente.";
            }else {
                return "La Factura ya existe en la base de datos.";
            }
        }catch (Exception e){
            throw e;
        }
    }

    public List<Factura> readFacturas(){
        return facturaRepository.findAll();
    }

    @Transactional
    public String updateFactura(Factura factura){
        if (facturaRepository.existById(factura.getNumeroFactura())){
            try {

                List<Factura> facturas = facturaRepository.findById(factura.getNumeroFactura());
                facturas.stream().forEach(s -> {
                    Factura facturaToBeUpdate = facturaRepository.findById(s.getNumeroFactura()).get(0);
                    facturaToBeUpdate.setNumeroFactura(s.getNumeroFactura());
                    facturaToBeUpdate.setBaseImponible(s.getBaseImponible());
                    facturaToBeUpdate.setComandaId(s.getComandaId());
                    facturaToBeUpdate.setDatosReceptor(s.getDatosReceptor());
                    facturaToBeUpdate.setFechaEmision(s.getFechaEmision());
                    facturaToBeUpdate.setImporteTotal(s.getImporteTotal());
                    facturaToBeUpdate.setRegistroMercantil(s.getRegistroMercantil());
                    facturaToBeUpdate.setRestauranteId(s.getRestauranteId());

                    facturaRepository.save(facturaToBeUpdate);
                });
                return "Factura actualizado correctamente.";
            }catch (Exception e){
                throw e;
            }
        }else {
            return "La Factura no existe.";
        }
    }

    @Transactional
    public String deleteFactura(Factura factura){
        if (facturaRepository.existsById(factura.getNumeroFactura())){
            try {
                List<Factura> facturas = facturaRepository.findById(factura.getNumeroFactura());
                facturas.stream().forEach(s -> {
                    facturaRepository.delete(s);
                });
                return "Factura eliminada correctamente.";
            }catch (Exception e){
                throw e;
            }

        }else {
            return "La Factura no existe";
        }
    }
}
