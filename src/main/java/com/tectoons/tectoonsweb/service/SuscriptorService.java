package com.tectoons.tectoonsweb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tectoons.tectoonsweb.model.Suscriptor;
import com.tectoons.tectoonsweb.repository.SuscriptorRepository;

@Service
public class SuscriptorService {
    @Autowired
    private SuscriptorRepository repo;

    public void guardar(String email) {
        Suscriptor s = new Suscriptor();
        s.setEmail(email);
        repo.save(s);
    }
}

