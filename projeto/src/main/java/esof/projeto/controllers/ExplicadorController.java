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
import org.springframework.web.bind.annotation.*;

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
        this.logger.info("GET -> getTodosExplicadores()");
        return ResponseEntity.ok(this.explicadorService.findAll());
    }

    @RequestMapping(value="/{nome}",method = RequestMethod.GET , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Explicador> getExplicadorNome(@PathVariable("nome") String nome) {
        this.logger.debug("GET -> getExplicadorNome( " + nome + " )");
        Optional<Explicador> explicadorOptional = this.explicadorService.procurarExplicador(nome);
        if(explicadorOptional.isEmpty()) throw new ExplicadorException();
        else return ResponseEntity.ok(explicadorOptional.get());
    }


    @RequestMapping(method = RequestMethod.POST ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Explicador> addExplicador(@RequestBody Explicador explicador) {
        this.logger.info("POST -> addExplicador( " + explicador.getNome()+" )");
        Optional<Explicador> explicadorOptional = this.explicadorService.criarExplicador(explicador);
        if (explicadorOptional.isPresent()) return ResponseEntity.ok(explicadorOptional.get());
        else throw new ExplicadorException();
    }

    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Explicador> mudarExplicador(@RequestBody Explicador explicador) {
        this.logger.info("PUT -> mudarExplicador( "+ explicador.getNome() +" )");
        Optional<Explicador> explicadorOptional = this.explicadorService.editarExplicador(explicador);
        if(explicadorOptional.isPresent()) return ResponseEntity.ok(explicadorOptional.get());
        throw new ExplicadorException();
    }

    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason = "Pedido sem sucesso.")
    private static class ExplicadorException extends RuntimeException {
        public ExplicadorException() {
            super("O pedido terminou sem sucesso.");
        }
    }
}
