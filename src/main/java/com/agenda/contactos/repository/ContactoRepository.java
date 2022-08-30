package com.agenda.contactos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.agenda.contactos.model.Contacto;

public interface ContactoRepository extends JpaRepository<Contacto, Integer> {

}
