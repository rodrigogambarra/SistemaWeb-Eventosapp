package com.eventosapp.eventosapp.controllers;

import com.eventosapp.eventosapp.models.Evento;
import com.eventosapp.eventosapp.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EventoController {

    @Autowired
    private EventoRepository er;


    @RequestMapping(value = "/cadastrarEvento", method= RequestMethod.GET)
    public String form(){
        return "evento/formEvento";
    }

    @RequestMapping(value = "/cadastrarEvento", method= RequestMethod.POST)
    public String form(Evento evento){
        er.save(evento);
        return "redirect:/cadastrarEvento";
    }

    @RequestMapping("/eventos")
    public ModelAndView listaEventos(){
        ModelAndView mv = new ModelAndView("/index");//Qual página ele vai renderizar para /eventos
        Iterable<Evento> eventos = er.findAll();
        mv.addObject("evento", eventos);//adiciona a palavra entre {} na view;
        return mv;
    }
}
