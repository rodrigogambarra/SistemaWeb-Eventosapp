package com.eventosapp.eventosapp.controllers;

import com.eventosapp.eventosapp.models.Evento;
import com.eventosapp.eventosapp.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EventoController {

    @Autowired
    private EventoRepository er;


    @RequestMapping(value = "/cadastrarEvento", method= RequestMethod.GET)//quando entrar nesta url
    public String form(){
        return "evento/formEvento";//executar este arquivo html
    }

    @RequestMapping(value = "/cadastrarEvento", method= RequestMethod.POST)//quando ter um post desta url
    public String form(Evento evento){
        er.save(evento);
        return "redirect:/cadastrarEvento";//redirecionar para este url
    }

    @RequestMapping("/eventos")
    public ModelAndView listaEventos(){
        ModelAndView mv = new ModelAndView("index");//Qual página ele vai renderizar para /eventos
        Iterable<Evento> eventos = er.findAll();
        mv.addObject("eventos", eventos);//adiciona a palavra entre {} na view;
        return mv;
    }

    @RequestMapping(value = "/{codigo}")
    public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo){
        Evento evento = er.findByCodigo(codigo);
        ModelAndView mv = new ModelAndView("evento/detalhesEvento");
        mv.addObject("evento", evento);
        System.out.println("AQUI ESTA O EVENTO: " + evento.toString());
        return mv;
    }
}
