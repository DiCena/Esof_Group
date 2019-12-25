package esof.projeto.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import esof.projeto.models.Aluno;
import esof.projeto.services.AlunoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AlunoController.class)
class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlunoService alunoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllAlunos() {
    }

    @Test
    void getAlunoById() throws Exception{
        Aluno aluno=new Aluno("aluno1");
        //aluno.setId(1L);

        when(this.alunoService.findById(1L)).thenReturn(Optional.of(aluno));

        String responseJson=this.mockMvc.perform(
                get("/aluno/1")
        ).andExpect(
                status().isOk()
        ).andReturn().getResponse().getContentAsString();

        //System.out.println(responseJson);

        Aluno responseAluno=this.objectMapper.readValue(responseJson,Aluno.class);
        assertEquals(aluno,responseAluno);

        this.mockMvc.perform(
                get("/aluno/2")
        ).andExpect(
                status().isNotFound()
        );
    }

    @Test
    void createAluno()throws Exception {
        Aluno aluno=new Aluno("alu1");

        String jsonRequest=this.objectMapper.writeValueAsString(aluno);

        when(alunoService.createAluno(aluno)).thenReturn(Optional.of(aluno));

        this.mockMvc.perform(
                post("/aluno").contentType(MediaType.APPLICATION_JSON).content(jsonRequest)
        ).andExpect(
                status().isOk()
        );

        Aluno existingAluno=new Aluno("alu2");

        when(this.alunoService.createAluno(existingAluno)).thenReturn(Optional.empty());

        String existingAlunoJson=this.objectMapper.writeValueAsString(existingAluno);

        this.mockMvc.perform(
                post("/aluno").contentType(MediaType.APPLICATION_JSON).content(existingAlunoJson)
        ).andExpect(
                status().isBadRequest()
        );

    }
}