package esof.projeto.controllers;

import esof.projeto.models.Aluno;
import esof.projeto.models.Cadeira;
import esof.projeto.models.Curso;
import esof.projeto.models.Faculdade;
import esof.projeto.services.GestorService;
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
@RequestMapping("")
public class GestorController {
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    private GestorService gestorService;

    @Autowired
    public GestorController(GestorService gestorService) {
        this.gestorService = gestorService;
    }

    /**
     * cria uma faculdade a partir do json
     * @param faculdade json com os dados do objeto faculdade
     * @return success-> optional da faculdade criada, error-> atira uma exception se houver uma faculdade repetida
     */
    @PostMapping(value = "/faculdade",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Faculdade> createFaculdade(@RequestBody Faculdade faculdade){
        this.logger.info("Post -> createFaculdade");
        Optional<Faculdade> faculdadeOptional=this.gestorService.createFaculdade(faculdade);
        if(faculdadeOptional.isPresent()){
            return ResponseEntity.ok(faculdadeOptional.get());
        }
        throw new GestorController.ElementAlreadyExistsException(faculdade.getNome());

    }

    /**
     * cria um curso dentro de uma faculdade
     * @param curso json do objeto custo
     * @param faculdade nome da faculdade
     * @return success-> optional do curso criado, error(ou pelo curso já existir dentro da faculdade ou por a faculdade
     * não existir)-> atira uma exception
     */
    @PostMapping(value = "/curso/{faculdade}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Curso> createCursoByFaculdade(@RequestBody Curso curso, @PathVariable String faculdade){
        this.logger.info("Post -> createCursoByFaculdade");
        Optional<Curso> cursoOptional=this.gestorService.createCursoByFaculdade(faculdade,curso);
        if(cursoOptional.isPresent()){
            return ResponseEntity.ok(cursoOptional.get());
        }
        throw new GestorController.ElementAlreadyExistsException(curso.getNome());

    }

    /**
     * cria uma cadeira dentro de um curso
     * @param cadeira json da cadeira
     * @param curso nome do curso
     * @return success-> option da cadeira criada, error(ou pela cadeira já existir dentro do curso ou pelo curso
     * não existir)-> atira uma exception
     *
     */
    @PostMapping(value = "/cadeira/{curso}",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cadeira> createCadeiraByCurso(@RequestBody Cadeira cadeira, @PathVariable String curso){
        this.logger.info("Post -> createCadeiraByCurso");
        Optional<Cadeira> cadeiraOptional=this.gestorService.createCadeiraByCurso(curso,cadeira);
        if(cadeiraOptional.isPresent()){
            return ResponseEntity.ok(cadeiraOptional.get());
        }
        throw new GestorController.ElementAlreadyExistsException((cadeira.getNome()));
    }

    //exception para quando se tenta inserir um elemento repetido
    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Element already exists")
    private static class ElementAlreadyExistsException extends RuntimeException {

        public ElementAlreadyExistsException(String name) {
            super("A element with name: "+name+" already exists");
        }
    }


}
