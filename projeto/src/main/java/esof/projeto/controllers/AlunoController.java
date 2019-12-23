package esof.projeto.controllers;

import esof.projeto.models.Aluno;
import esof.projeto.services.AlunoService;
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
@RequestMapping("/aluno")
public class AlunoController {

    private Logger logger= LoggerFactory.getLogger(this.getClass());


    private AlunoService alunoService;

    @Autowired
    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Aluno>> getAllAlunos(){
        this.logger.info("Received a get request");

        return ResponseEntity.ok(this.alunoService.findAll());
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseEntity<Aluno> getAlunobyId(@PathVariable("id") Long id) throws NoAlunoException {
        this.logger.info("Received a get request");

        Optional<Aluno> optionalClient=this.alunoService.findById(id);
        if(optionalClient.isPresent()) {
            return ResponseEntity.ok(optionalClient.get());
        }
        throw new NoAlunoException(id);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Aluno> createAluno(@RequestBody Aluno aluno){
        Optional<Aluno> clientOptional=this.alunoService.createAluno(aluno);
        if(clientOptional.isPresent()){
            return ResponseEntity.ok(clientOptional.get());
        }
        throw new AlunoAlreadyExistsException(aluno.getNome());

    }



    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such aluno")
    private static class NoAlunoException extends RuntimeException {

        public NoAlunoException(Long id) {
            super("No such aluno with id: "+id);
        }
    }

    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Aluno already exists")
    private static class AlunoAlreadyExistsException extends RuntimeException {

        public AlunoAlreadyExistsException(String name) {
            super("A aluno with name: "+name+" already exists");
        }
    }

}
