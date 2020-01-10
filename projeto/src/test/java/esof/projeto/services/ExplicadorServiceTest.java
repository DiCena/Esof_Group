package esof.projeto.services;

import esof.projeto.models.Atendimento;
import esof.projeto.models.Explicador;
import esof.projeto.repositories.AtendimentoRepo;
import esof.projeto.repositories.ExplicadorRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
class ExplicadorServiceTest {

  //  @Autowired
 //   private ExplicadorService explicadorService;

    @Autowired
    private ExplicadorRepo explicadorRepo;
    @Autowired
    private AtendimentoRepo atendimentoRepo;




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
        
    }
}