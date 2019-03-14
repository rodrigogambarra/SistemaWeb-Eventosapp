package com.eventosapp.eventosapp.repository;

import com.eventosapp.eventosapp.models.Evento;
import org.springframework.data.repository.CrudRepository;

public interface EventoRepository extends CrudRepository<Evento, String> {

}
