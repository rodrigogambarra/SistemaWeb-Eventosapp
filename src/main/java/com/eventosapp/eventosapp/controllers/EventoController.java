package com.eventosapp.eventosapp.controllers;

import com.eventosapp.eventosapp.models.Convidado;
import com.eventosapp.eventosapp.models.Evento;
import com.eventosapp.eventosapp.models.Usuario;
import com.eventosapp.eventosapp.repository.ConvidadoRepository;
import com.eventosapp.eventosapp.repository.EventoRepository;
import com.eventosapp.eventosapp.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @RequestMapping(value = "/cadastrarEvento", method= RequestMethod.POST)//quando um post desta url
    public String form(@Valid Evento evento, BindingResult result, RedirectAttributes attributes){
        if(result.hasErrors()){
            attributes.addFlashAttribute("mensagem","Verifique os campos");
            return "redirect:/cadastrarEvento";
        }
        er.save(evento);
        attributes.addFlashAttribute("mensagem","Evento cadastrado com sucesso!");
        return "redirect:/cadastrarEvento";//redireciona para /cadastrarEvento com método get
    }

    @RequestMapping("/eventos")
    public ModelAndView listaEventos(){
        ModelAndView mv = new ModelAndView("listaEventos");//Qual página ele vai renderizar para /eventos
        Iterable<Evento> eventos = er.findAll();
        mv.addObject("eventos", eventos);//renderiza a página index vinculando a variável eventos com a lista de eventos;
        return mv;
    }

    @RequestMapping(value = "/{codigo}", method = RequestMethod.GET)
    public ModelAndView detalhesEvento(@PathVariable("codigo") long codigo){
        Evento evento = er.findByCodigo(codigo);
        ModelAndView mv = new ModelAndView("evento/detalhesEvento");
        mv.addObject("evento", evento);

        Iterable<Convidado>convidados = cr.findByEvento(evento);
        mv.addObject("convidados",convidados);
        return mv;
    }

    @RequestMapping("/deletarEvento")
    public String deletarEvento(long codigo){
        Evento evento = er.findByCodigo(codigo);
        er.delete(evento);
        return "redirect:/eventos";
    }

    @RequestMapping("/deletarConvidado")
    public String deletarConvidado(String rg){
        Convidado convidado = cr.findByRg(rg);
        cr.delete(convidado);

        Evento evento = convidado.getEvento();
        long codigoLong = evento.getCodigo();
        String codigo = "" + codigoLong;
        return "redirect:/" + codigo;
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
