package esof.projeto;

import esof.projeto.models.*;
import esof.projeto.repositories.AlunoRepo;
import esof.projeto.repositories.AtendimentoRepo;
import esof.projeto.repositories.ExplicadorRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Clock;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Component
@Transactional
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private AtendimentoRepo atendimentoRepo;


    @Autowired
    public Bootstrap(AtendimentoRepo atendimentoRepo) {
        this.atendimentoRepo = atendimentoRepo;
    }

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Explicador e1 = new Explicador();
        e1.setNome("exp1");
        LocalDate ld1 = LocalDate.of(2020, 1, 10);
        LocalTime lt1 = LocalTime.of(15, 30);

        Aluno al1 = new Aluno();
        al1.setNome("alu1");

        e1.criarAtendimento(al1, ld1, lt1);

    }
}
