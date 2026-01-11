package com.tectoons.tectoonsweb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tectoons.tectoonsweb.model.Contacto;

public interface ContactoRepository extends JpaRepository<Contacto, Long> {
}
