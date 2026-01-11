package com.tectoons.tectoonsweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tectoons.tectoonsweb.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> { }
