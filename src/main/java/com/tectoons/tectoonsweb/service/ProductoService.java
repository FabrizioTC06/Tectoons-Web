package com.tectoons.tectoonsweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tectoons.tectoonsweb.model.Producto;
import com.tectoons.tectoonsweb.repository.ProductoRepository;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productRepository;

    public List<Producto> listarTodos() {
        return productRepository.findAll();
    }

    public Producto obtenerPorId(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Producto> filtrarPorCategoria(String categoria) {
        return productRepository.findByCategoria(categoria);
    }

    public Producto guardar(Producto product) {
        return productRepository.save(product);
    }

    public void eliminar(Long id) {
        productRepository.deleteById(id);
    }
}
