package com.tectoons.tectoonsweb.service;

import org.springframework.stereotype.Service;

import com.tectoons.tectoonsweb.model.Contacto;
import com.tectoons.tectoonsweb.repository.ContactoRepository;

import jakarta.transaction.Transactional;

@Service
public class ContactoService {
    private final ContactoRepository contactoRepository;

    public ContactoService(ContactoRepository contactoRepository) {
        this.contactoRepository = contactoRepository;
    }

    @Transactional
    public Contacto guardar(Contacto contacto) {
        return contactoRepository.save(contacto);
    }
}
