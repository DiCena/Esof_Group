package esof.projeto.controllers;

import esof.projeto.models.Atendimento;
import esof.projeto.models.Explicador;
import esof.projeto.models.body.AtendimentoBody;
import esof.projeto.services.ExplicadorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
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


    /**
     * Devolve todos os explicadores na base de dados com
     * os devidos parametros. Se nao tiver searchParams, devolve tudo
     * @return Set<Explicador>
     */
    @RequestMapping(method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Explicador>> getTodosExplicadores(@RequestParam Map<String,String> searchParams){
        this.logger.info("GET -> getTodosExplicadores()");
        return ResponseEntity.ok(this.explicadorService.filterExplicador(searchParams));
    }


    /**
     * Procura um explicador pelo nome passado.
     * @param nome Nome a procurar pelo explicador
     * @return Explicador
     */
    @RequestMapping(value="/{nome}",method = RequestMethod.GET , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Explicador> getExplicadorNome(@PathVariable("nome") String nome) {
        this.logger.info("GET -> getExplicadorNome( " + nome + " )");
        Optional<Explicador> explicadorOptional = this.explicadorService.procurarExplicador(nome);
        if(explicadorOptional.isEmpty()) throw new ExplicadorException();
        else return ResponseEntity.ok(explicadorOptional.get());
    }

    /**
     * Adiciona um explicador na base dados caso não exista o nome ainda.
     * @param explicador Explicador a adicionar
     * @return Explicador
     */
    @RequestMapping(method = RequestMethod.POST ,consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Explicador> addExplicador(@RequestBody Explicador explicador) {
        this.logger.info("POST -> addExplicador( " + explicador.getNome()+" )");
        Optional<Explicador> explicadorOptional = this.explicadorService.criarExplicador(explicador);
        if (explicadorOptional.isPresent()) return ResponseEntity.ok(explicadorOptional.get());
        else throw new ExplicadorException();
    }



    /**
     * Muda atributos de um explicador caso o mesmo exista.
     * Ao contrário de um put , o patch acrescenta valores.
     * @param explicador Explicador com os atributos novos
     * @return Explicador (editado)
     */
    @RequestMapping(method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Explicador> editarExplicador(@RequestBody Explicador explicador) {
        this.logger.info("PATCH -> mudarExplicador( "+ explicador.getNome() +" )");
        Optional<Explicador> explicadorOptional = this.explicadorService.patchExplicador(explicador);
        if(explicadorOptional.isPresent()) return ResponseEntity.ok(explicadorOptional.get());
        throw new ExplicadorException();
    }


    /**
     * Muda atributos de um explicador caso o mesmo exista.
     * @param explicador Explicador com os atributos novos
     * @return Explicador (editado)
     */
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Explicador> mudarExplicador(@RequestBody Explicador explicador) {
        this.logger.info("PUT -> mudarExplicador( "+ explicador.getNome() +" )");
        Optional<Explicador> explicadorOptional = this.explicadorService.editarExplicador(explicador);
        if(explicadorOptional.isPresent()) return ResponseEntity.ok(explicadorOptional.get());
        throw new ExplicadorException();
    }





    /**
     * Resposta para todos os pedidos que não encontrem um dado explicador
     * ou definam um erro de procura no repositorio explicador.
     */
    @ResponseStatus(value= HttpStatus.BAD_REQUEST, reason = "Pedido sem sucesso.")
    private static class ExplicadorException extends RuntimeException {
        public ExplicadorException() {
            super("O pedido terminou sem sucesso.");
        }
    }







}
