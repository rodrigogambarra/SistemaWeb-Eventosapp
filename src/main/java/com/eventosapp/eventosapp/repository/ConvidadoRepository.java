package com.eventosapp.eventosapp.repository;

import com.eventosapp.eventosapp.models.Convidado;
import com.eventosapp.eventosapp.models.Evento;
import org.springframework.data.repository.CrudRepository;

public interface ConvidadoRepository extends CrudRepository<Convidado, String> {

    Iterable<Convidado> findByEvento(Evento evento);
    Convidado findByRg(String rg);
}
