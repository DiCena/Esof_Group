package esof.projeto.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import esof.projeto.models.Atendimento;
import esof.projeto.models.Explicador;
import esof.projeto.repositories.ExplicadorRepo;
import esof.projeto.services.ExplicadorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ExplicadorController.class)
class ExplicadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExplicadorService explicadorService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getTodosExplicadores() throws Exception{
        Explicador e1 = new Explicador("exp1");
        Explicador e2 = new Explicador("exp1");
        Explicador e3 = new Explicador("exp1");

        HashSet<Explicador> set1 = new HashSet<Explicador>();

        when(this.explicadorService.findAll()).thenReturn(set1);

        String responseJson = this.mockMvc.perform(get("/explicador"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();


    }


    @Test
    void getExplicadorNome() throws Exception{
        Explicador explicador1 = new Explicador("jose");

        // quando this.explicadorService.procurarExplicador("jose") for chamadao , vai retunar Optional.of(explicador)
        when(this.explicadorService.procurarExplicador("jose")).thenReturn(Optional.of(explicador1));

        if(this.explicadorService.procurarExplicador("jose").isPresent()) System.out.println("presente");
        else System.out.println("nao presente");


        String response = this.mockMvc.perform(get("/explicador/jose").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Explicador lido = this.objectMapper.readValue(response,Explicador.class);

        assertEquals("jose",lido.getNome());


        String response2 = this.mockMvc.perform(get("/explicador/jasinto").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

    }

    @Test
    void addExplicador() throws Exception{
        Explicador e1 = new Explicador("orlando");

        String exp = this.objectMapper.writeValueAsString(e1);

        when(explicadorService.criarExplicador(e1)).thenReturn(Optional.of(e1));

        String response = this.mockMvc.perform(post("/explicador")
                .contentType(MediaType.APPLICATION_JSON)
                .content(exp))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Explicador lido = this.objectMapper.readValue(response,Explicador.class);

        assertEquals("orlando",lido.getNome());


    }


    @Test
    void mudarExplicador() throws Exception{
        Explicador explicador = new Explicador("jose");

        String json = this.objectMapper.writeValueAsString(explicador);

        String response = this.mockMvc.perform(post("/explicador")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();


    }
}