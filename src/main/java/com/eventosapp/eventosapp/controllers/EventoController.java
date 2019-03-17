package com.eventosapp.eventosapp.controllers;

import com.eventosapp.eventosapp.models.Convidado;
import com.eventosapp.eventosapp.models.Evento;
import com.eventosapp.eventosapp.repository.ConvidadoRepository;
import com.eventosapp.eventosapp.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.Binding;
import javax.validation.Valid;

@Controller
public class EventoController {

    @Autowired
    private EventoRepository er;

    @Autowired
    private ConvidadoRepository cr;


    @RequestMapping(value = "/cadastrarEvento", method= RequestMethod.GET)//quando entrar nesta url
    public String form(){
        return "evento/formEvento";//executar este arquivo html
    }

    @RequestMapping(value = "/cadastrarEvento", method= RequestMethod.POST)//quando ter um post desta url
    public String form(@Valid Evento evento, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem","Verifique os campos");
            return "redirect:/cadastrarEvento";
        }
        er.save(evento);
        attributes.addFlashAttribute("mensagem","Evento cadastrado com sucesso!");
        return "redirect:/cadastrarEvento";//redirecionar para este url
    }

    @RequestMapping("/eventos")
    public ModelAndView listaEventos(){
        ModelAndView mv = new ModelAndView("index");//Qual página ele vai renderizar para /eventos
        Iterable<Evento> eventos = er.findAll();
        mv.addObject("eventos", eventos);//adiciona a palavra entre {} na view;
        return mv;
    }

    @RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
    public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo){
        Evento evento = er.findByCodigo(codigo);
        ModelAndView mv = new ModelAndView("evento/detalhesEvento");
        mv.addObject("evento", evento);

        Iterable<Convidado>convidados = cr.findByEvento(evento);
        mv.addObject("convidados",convidados);
        System.out.println("AQUI ESTA O EVENTO: " + evento.toString());
        return mv;
    }

    @RequestMapping(value = "/{codigo}", method = RequestMethod.POST)
    public String detalhesEventoPost(@PathVariable("codigo") long codigo, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes){//nome do método deve ser difeirente do outro para funcionar os links da página index
        if(result.hasErrors()){//se tiver algum erro
            attributes.addFlashAttribute("mensagem","Verifique os campos!");//a view deve ter o retorno para a variável "mensagem"
            return "redirect:/{codigo}";
        }

        Evento evento = er.findByCodigo(codigo);
        convidado.setEvento(evento);
        cr.save(convidado);
        attributes.addFlashAttribute("mensagem","Convidado adicionado com sucesso!");
        return "redirect:/{codigo}";
    }
}
