package esof.projeto.controllers;

import esof.projeto.models.Aluno;
import esof.projeto.models.Atendimento;
import esof.projeto.models.Explicador;
import esof.projeto.models.body.AtendimentoBody;
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




    /**
     * Procura um aluno pelo nome passado.
     * @param nome Nome a procurar pelo aluno
     * @return Aluno
     */
    @RequestMapping(value="/{nome}",method = RequestMethod.GET , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Aluno> getExplicadorNome(@PathVariable("nome") String nome) {
        this.logger.info("GET -> getExplicadorNome( " + nome + " )");
        Optional<Aluno> alunoOptional = this.alunoService.procurarAluno(nome);
        if(alunoOptional.isEmpty()) throw new AlunoAlreadyExistsException(nome);
        else return ResponseEntity.ok(alunoOptional.get());
    }



    /**
     * procurar todos os alunos
     * @return todos os alunos da bd
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Iterable<Aluno>> getAllAlunos(){
        this.logger.info("Received a get request");

        return ResponseEntity.ok(this.alunoService.findAll());
    }

    /**
     * procura um aluno pelo id
     * @param id id de pesquisa
     * @return success-> aluno com o id perquisado
     * @throws NoAlunoException - quando o aluno pelo id não existe
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public ResponseEntity<Aluno> getAlunobyId(@PathVariable("id") Long id) throws NoAlunoException {
        this.logger.info("Received a get request");

        Optional<Aluno> optionalClient=this.alunoService.findById(id);
        if(optionalClient.isPresent()) {
            return ResponseEntity.ok(optionalClient.get());
        }
        throw new NoAlunoException(id);
    }

    /**
     * cria um aluno na base de dados a partir do json
     * @param aluno json com a informação para criar o aluno
     * @return success-> aluno criado, error-> atira exception se já existir um aluno repetido
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Aluno> createAluno(@RequestBody Aluno aluno){
        Optional<Aluno> clientOptional=this.alunoService.createAluno(aluno);
        if(clientOptional.isPresent()){
            return ResponseEntity.ok(clientOptional.get());
        }
        throw new AlunoAlreadyExistsException(aluno.getNome());

    }

    /**
     * Exception para ser soltada quando o aluno que se procura pelo id não existe
     */
    @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such aluno")
    private static class NoAlunoException extends RuntimeException {

        public NoAlunoException(Long id) {
            super("No such aluno with id: "+id);
        }
    }




    @RequestMapping( value = "/atendimento",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Atendimento> marcarAtendimento(@RequestBody AtendimentoBody atendimentoBody){
        this.logger.info("POST -> marcarAtendimento");
        Optional<Atendimento> atendimentoOptional = this.alunoService.marcarAtendimento(atendimentoBody);
        if(atendimentoOptional.isPresent()) return ResponseEntity.ok(atendimentoOptional.get());
        throw new AtendimentoException();
    }

    /**
     * Exception para ser soltada quando o aluno que se tenta criar já existe
     */
    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Aluno already exists")
    private static class AlunoAlreadyExistsException extends RuntimeException {

        public AlunoAlreadyExistsException(String name) {
            super("A aluno with name: "+name+" already exists");
        }
    }


    /**
     *Resposta para quando não foi possível marcar um atendimento
     */
    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason = "Pedido sem sucesso.")
    private static class AtendimentoException extends RuntimeException {
        public AtendimentoException() {
            super("O pedido terminou sem sucesso.");
        }
    }

}
