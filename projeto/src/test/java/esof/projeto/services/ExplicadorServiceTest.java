package esof.projeto.services;

import esof.projeto.models.Atendimento;
import esof.projeto.models.Cadeira;
import esof.projeto.models.Explicador;
import esof.projeto.repositories.AtendimentoRepo;
import esof.projeto.repositories.ExplicadorRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
class ExplicadorServiceTest {

  //  @Autowired
 //   private ExplicadorService explicadorService;

    @Mock
    private ExplicadorRepo explicadorRepo;
    @Mock
    private AtendimentoRepo atendimentoRepo;

    @InjectMocks
    private ExplicadorService explicadorService;


    @Test
    void criarExplicador() {
        //dado
        Explicador explicador = new Explicador();
        explicador.setNome("joao");
        when(this.explicadorRepo.save(explicador)).thenReturn(explicador);

        // --
        // adicionar explicador
        Optional<Explicador> explicadorOptional = this.explicadorService.criarExplicador(explicador);

        assertTrue(explicadorOptional.isPresent());
        assertEquals(explicador,explicadorOptional.get());

    }

    @Test
    void procurarExplicador() {
        // dado
        Explicador explicador = new Explicador();
        explicador.setNome("josefa");
        Explicador explicador1 = new Explicador();
        explicador1.setNome("filipe");
        Explicador explicador2 = new Explicador();
        explicador2.setNome("luisinho");

        when(this.explicadorRepo.findByNome("josefa")).thenReturn(Optional.of(explicador));
        when(this.explicadorRepo.findByNome("filipe")).thenReturn(Optional.of(explicador));
        when(this.explicadorRepo.findByNome("luisinho")).thenReturn(Optional.of(explicador));


        // --
        // procurar explicadores que existem no repositorio
        Optional<Explicador> explicadorOptional = this.explicadorService.procurarExplicador("josefa");
        assertTrue(explicadorOptional.isPresent());

        Optional<Explicador> explicadorOptional1 = this.explicadorService.procurarExplicador("filipe");
        assertTrue(explicadorOptional1.isPresent());

        Optional<Explicador> explicadorOptional2 = this.explicadorService.procurarExplicador("luisinho");
        assertTrue(explicadorOptional2.isPresent());

        // --
        // procurar explicadores que nao existem

        Optional<Explicador> explicadorOptional3 = this.explicadorService.procurarExplicador("naoexisto");
        assertFalse(explicadorOptional3.isPresent());
    }

    @Test
    void editarExplicador() {
        //dado
        Explicador explicador=new Explicador();
        explicador.setNome("expl1");
        Atendimento atendimento=new Atendimento();
        atendimento.setDiaAtendimento(LocalDate.now());
        atendimento.setHoraAtendimento(LocalTime.of(10,0));
        Atendimento atendimento1=new Atendimento();
        atendimento1.setDiaAtendimento(LocalDate.now());explicador.addAtendimento(atendimento);
        atendimento1.setHoraAtendimento(LocalTime.of(12,0));
        explicador.addAtendimento(atendimento);
        explicador.addAtendimento(atendimento1);

        when(this.explicadorRepo.save(explicador)).thenReturn(explicador);
        Explicador salvado = this.explicadorService.criarExplicador(explicador).get();
        assertEquals("expl1",salvado.getNome());
        assertEquals(2,salvado.getAtendimentos().size());       // tem 2 atendimentos
        assertEquals(0,salvado.getCadeiras().size());           // tem 0 cadeiras

        // --
        // explicador fica com 1 atendimento apenas

        Explicador explicador1 = new Explicador();
        explicador1.setNome("expl1");
        Atendimento atendimento2=new Atendimento();
        atendimento2.setDiaAtendimento(LocalDate.now());
        atendimento2.setHoraAtendimento(LocalTime.of(11,0));
        explicador1.addAtendimento(atendimento2);

        assertEquals(1,explicador1.getAtendimentos().size());

        // quando procurar pelo explicador existente , devolve explicador 1
        when(this.explicadorService.procurarExplicador("expl1")).thenReturn(Optional.of(explicador));

        Optional<Explicador> explicadorOptional = this.explicadorService.editarExplicador(explicador1);

        assertTrue(explicadorOptional.isPresent());
        assertEquals(1,explicadorOptional.get().getAtendimentos().size());
    }

    @Test
    void patchExplicador() {
        //dado
        Explicador explicador=new Explicador();
        explicador.setNome("expl1");
        Atendimento atendimento=new Atendimento();
        atendimento.setDiaAtendimento(LocalDate.now());
        atendimento.setHoraAtendimento(LocalTime.of(10,0));
        Atendimento atendimento1=new Atendimento();
        atendimento1.setDiaAtendimento(LocalDate.now());explicador.addAtendimento(atendimento);
        atendimento1.setHoraAtendimento(LocalTime.of(12,0));
        explicador.addAtendimento(atendimento);
        explicador.addAtendimento(atendimento1);


        when(this.explicadorRepo.save(explicador)).thenReturn(explicador);
        Explicador salvado = this.explicadorService.criarExplicador(explicador).get();
        assertEquals("expl1",salvado.getNome());
        assertEquals(2,salvado.getAtendimentos().size());       // tem 2 atendimentos
        assertEquals(0,salvado.getCadeiras().size());           // tem 0 cadeiras

        // --
        // adicionar uma cadeira com o patch


        Explicador expl1Editado = new Explicador();
        expl1Editado.setNome("expl1");
        Cadeira cadeira = new Cadeira();
        cadeira.setNome("Base Dados");
        expl1Editado.addCadeira(cadeira);

        // quando procurar pelo explicador existente , devolve explicador 1
        when(this.explicadorService.procurarExplicador("expl1")).thenReturn(Optional.of(explicador));

        Optional<Explicador> optionalExplicador = this.explicadorService.patchExplicador(expl1Editado);

        assertTrue(optionalExplicador.isPresent());
        assertEquals(2,optionalExplicador.get().getAtendimentos().size());  // tem na mesma 2 atendimentos
        assertEquals(1,optionalExplicador.get().getCadeiras().size()); // tem 1 cadeira agora


        // --
        // acrescentar um atendimento

        Explicador expl2Editado = new Explicador();
        expl2Editado.setNome("expl1");
        Atendimento atendimento3=new Atendimento();
        atendimento3.setDiaAtendimento(LocalDate.now());
        atendimento3.setHoraAtendimento(LocalTime.of(11,0));
        expl2Editado.addAtendimento(atendimento3);

        Optional<Explicador> optionalExplicador1 = this.explicadorService.patchExplicador(expl2Editado);


        assertTrue(optionalExplicador1.isPresent());
        assertEquals(3,optionalExplicador.get().getAtendimentos().size());  // tem 3 atendimento
        assertEquals(1,optionalExplicador.get().getCadeiras().size()); // tem 1 cadeira
    }
}