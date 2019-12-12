package esof.projeto.controllers;

import esof.projeto.models.Explicador;
import esof.projeto.services.ExplicadorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

@Controller
@RequestMapping("/explicador")
public class ExplicadorController {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    private ExplicadorService explicadorService;

    @Autowired
    public ExplicadorController(ExplicadorService explicadorService) {
        this.explicadorService=explicadorService;
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Explicador>> getTodosExplicadores(){
        this.logger.info("Recebi um GET em getTodosExplicadores()");
        return ResponseEntity.ok(this.explicadorService.findAll());
    }



    @RequestMapping(method = RequestMethod.POST ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Explicador> addExplicador(@RequestBody Explicador explicador) {
        this.logger.info("Recebi um POST em addExplicador()");
        Optional<Explicador> explicadorOptional = this.explicadorService.criarExplicador(explicador);
        if (explicadorOptional.isPresent()) {
            return ResponseEntity.ok(explicadorOptional.get());
        }
           throw new ExplicadorJaExisteException(explicador.getNome());



    }


    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason = "Explicador ja existe")
    private static class ExplicadorJaExisteException extends RuntimeException {
        public ExplicadorJaExisteException(String message) {
            super("O explicador com o nome" + message + "nao existe");
        }
    }

    





}
