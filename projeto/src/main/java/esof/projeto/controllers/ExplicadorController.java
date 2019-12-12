package esof.projeto.controllers;

import esof.projeto.models.Explicador;
import esof.projeto.services.ExplicadorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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


}
