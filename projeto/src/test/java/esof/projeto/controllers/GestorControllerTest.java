package esof.projeto.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import esof.projeto.models.Cadeira;
import esof.projeto.models.Curso;
import esof.projeto.models.Faculdade;
import esof.projeto.services.GestorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GestorController.class)
class GestorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GestorService gestorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createFaculdade() throws Exception{
        Faculdade faculdade=new Faculdade();
        faculdade.setNome("fac1");

        String jsonRequest=this.objectMapper.writeValueAsString(faculdade);

        when(gestorService.createFaculdade(faculdade)).thenReturn(Optional.of(faculdade));

        this.mockMvc.perform(
                post("/faculdade").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)
        ).andExpect(
                status().isOk()
        );

        Faculdade existingFaculdade=new Faculdade();
        existingFaculdade.setNome("fac1");

        when(this.gestorService.createFaculdade(existingFaculdade)).thenReturn(Optional.empty());

        String existingAlunoJson=this.objectMapper.writeValueAsString(existingFaculdade);

        this.mockMvc.perform(
                post("/faculdade").contentType(MediaType.APPLICATION_JSON).content(existingAlunoJson)
        ).andExpect(
                status().isBadRequest()
        );

    }

    @Test
    void createCursoByFaculdade() throws Exception {

        Curso curso=new Curso();
        curso.setNome("cur1");

        String jsonRequest=this.objectMapper.writeValueAsString(curso);

        when(gestorService.createCursoByFaculdade("fac",curso)).thenReturn(Optional.of(curso));

        this.mockMvc.perform(
                post("/curso/fac").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)
        ).andExpect(
                status().isOk()
        );

        Curso existingCurso=new Curso();
        existingCurso.setNome("cur1");

        when(this.gestorService.createCursoByFaculdade("fac",existingCurso)).thenReturn(Optional.empty());

        String existingCursoJson=this.objectMapper.writeValueAsString(existingCurso);

        this.mockMvc.perform(
                post("/curso/fac").contentType(MediaType.APPLICATION_JSON).content(existingCursoJson)
        ).andExpect(
                status().isBadRequest()
        );


    }

    @Test
    void createCadeiraByCurso() throws Exception {

        Cadeira cadeira=new Cadeira();
        cadeira.setNome("ca1");

        String jsonRequest=this.objectMapper.writeValueAsString(cadeira);

        when(gestorService.createCadeiraByCurso("cur1",cadeira)).thenReturn(Optional.of(cadeira));

        this.mockMvc.perform(
                post("/cadeira/cur1").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)
        ).andExpect(
                status().isOk()
        );

        Cadeira existingCadeira=new Cadeira();
        existingCadeira.setNome("ca1");

        when(this.gestorService.createCadeiraByCurso("cur1",existingCadeira)).thenReturn(Optional.empty());

        String existingCadeiraJson=this.objectMapper.writeValueAsString(existingCadeira);

        this.mockMvc.perform(
                post("/cadeira/cur1").contentType(MediaType.APPLICATION_JSON).content(existingCadeiraJson)
        ).andExpect(
                status().isBadRequest()
        );
    }
}