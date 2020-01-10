package esof.projeto.models;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ExplicadorTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    @Test
    void criarAtendimento() {
        Explicador e1 = new Explicador();
        e1.setNome("exp1");

        LocalDate ld1 = LocalDate.of(2019, 12, 20);
        LocalTime lt1 = LocalTime.of(15, 30);
        LocalTime lt2 = LocalTime.of(16, 30);
        LocalTime lt3 = LocalTime.of(14, 30);
        LocalTime lt4 = LocalTime.of(18, 30);
        LocalTime lt5 = LocalTime.of(17, 30);
        LocalTime lt6 = LocalTime.of(18, 0);


        Aluno al1 = new Aluno();
        al1.setNome("alu1");
        Aluno al2 = new Aluno();
        al1.setNome("alu2");
        Aluno al3 = new Aluno();
        al1.setNome("alu3");

        Disponibilidade d1 = new Disponibilidade();

        d1.setDiaDaSemana(DayOfWeek.FRIDAY);
        d1.setHorarioInicio(LocalTime.of(15, 30));
        d1.setHorarioFim(LocalTime.of(18, 0));

        e1.criarAtendimento(al1, ld1, lt1);
        e1.criarAtendimento(al2, ld1, lt1);//rejeitado

        e1.addDisponibilidade(d1);

        e1.criarAtendimento(al2, ld1, lt1);//rejeitado
        e1.criarAtendimento(al1, ld1, lt2);
        e1.criarAtendimento(al2, ld1, lt3); //recusado
        e1.criarAtendimento(al3, ld1, lt4); //recusado
        e1.criarAtendimento(al1, ld1, lt5);//recusado
        e1.criarAtendimento(al1, ld1, lt6); //recusado

        assertEquals(2, e1.getAtendimentos().size());

    }

    @Test
    void removeAtendimento() {
        // dado
        Explicador ex1 = new Explicador();
        ex1.setNome("jose");
        Atendimento atendimento = new Atendimento();
        atendimento.setDiaAtendimento(LocalDate.now());
        Atendimento atendimento1 = new Atendimento();
        atendimento.setDiaAtendimento(LocalDate.of(2020,10,20));
        ex1.addAtendimento(atendimento);
        ex1.addAtendimento(atendimento1);

        assertEquals(2,ex1.getAtendimentos().size());

        // --
        // apagar atendimento

        ex1.removeAtendimento(atendimento);

        assertEquals(1,ex1.getAtendimentos().size());

        // --
        // apagar tudo

        ex1.removeAtendimento(atendimento1);


        assertEquals(0,ex1.getAtendimentos().size());

    }

    @Test
    void removeAllAtendimentos() {
        // dado
        Explicador ex1 = new Explicador();
        ex1.setNome("jose");
        Atendimento atendimento = new Atendimento();
        atendimento.setDiaAtendimento(LocalDate.now());
        Atendimento atendimento1 = new Atendimento();
        atendimento.setDiaAtendimento(LocalDate.of(2020,10,20));
        ex1.addAtendimento(atendimento);
        ex1.addAtendimento(atendimento1);

        assertEquals(2,ex1.getAtendimentos().size());

        // --
        // apagar todos os atendimentos

        ex1.removeAllAtendimentos();
        assertEquals(0,ex1.getAtendimentos().size());



    }
}