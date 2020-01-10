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
    void findAll() {
    }

    @Test
    void criarExplicador() {
    }

    @Test
    void procurarExplicador() {
    }

    @Test
    void editarExplicador() {


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

        this.explicadorRepo.save(explicador);


        assertEquals(1,this.explicadorRepo.count());
        assertEquals(2,this.atendimentoRepo.count());

        Optional<Explicador> optionalExplicador=this.explicadorRepo.findByNome("expl1");

        assertTrue(optionalExplicador.isPresent());

        Explicador explicadorFromDB=optionalExplicador.get();

        explicadorFromDB.removeAtendimento(atendimento);
        assertEquals(1,explicadorFromDB.getAtendimentos().size());

        Atendimento atendimento2=new Atendimento();
        atendimento2.setDiaAtendimento(LocalDate.now().plusDays(1l));
        atendimento2.setHoraAtendimento(LocalTime.of(10,0));

        explicadorFromDB.addAtendimento(atendimento2);
        assertEquals(2,explicadorFromDB.getAtendimentos().size());
        assertTrue(explicadorFromDB.getAtendimentos().contains(atendimento2));
        
        Long explicadorId=explicadorFromDB.getId();
        System.out.println(explicadorFromDB.getAtendimentos());

        this.explicadorRepo.save(explicadorFromDB);

        optionalExplicador=this.explicadorRepo.findByNome("expl1");
        assertTrue(optionalExplicador.isPresent());
        explicadorFromDB=optionalExplicador.get();

        assertEquals(explicadorId,optionalExplicador.get().getId());
        assertEquals(2,optionalExplicador.get().getAtendimentos().size());

        System.out.println(explicadorFromDB.getAtendimentos());
        assertTrue(explicadorFromDB.getAtendimentos().contains(atendimento2));


        explicadorFromDB.removeAtendimento(atendimento2);

        this.explicadorRepo.save(explicadorFromDB);
        optionalExplicador=this.explicadorRepo.findByNome("expl1");
        assertTrue(optionalExplicador.isPresent());

        assertEquals(1,optionalExplicador.get().getAtendimentos().size());


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