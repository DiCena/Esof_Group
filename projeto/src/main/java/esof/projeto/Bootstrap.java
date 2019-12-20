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
import java.time.DayOfWeek;
import java.util.Date;

@Component
@Transactional
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private AtendimentoRepo atendimentoRepo;


    @Autowired
    public Bootstrap(AtendimentoRepo atendimentoRepo) {
        this.atendimentoRepo=atendimentoRepo;
    }

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Faculdade f1=new Faculdade();
        f1.setNome("ufp");

        Curso c1=new Curso();
        c1.setNome("eng. inf");
        Curso c2=new Curso();
        c1.setNome("mat");


        Cadeira ca1=new Cadeira();
        ca1.setNome("bd");

        Explicador e1=new Explicador();
        e1.setNome("Zé");

        Disponibilidade di1=new Disponibilidade();
        di1.setDiaDaSemana(DayOfWeek.MONDAY);
        Disponibilidade di2=new Disponibilidade();
        di2.setDiaDaSemana(DayOfWeek.THURSDAY);
        Disponibilidade di3=new Disponibilidade();
        di3.setDiaDaSemana(DayOfWeek.TUESDAY);

        Atendimento a1= new Atendimento();

        Aluno al1=new Aluno();
        al1.setNome("Luís");

        al1.addAtendimento(a1);

        e1.addAtendimento(a1);

        e1.addDisponibilidade(di1);
        e1.addDisponibilidade(di2);
        e1.addDisponibilidade(di3);

        e1.addCadeira(ca1);

        c1.addCadeira(ca1);

        f1.addCurso(c1);

        f1.addCurso(c2);

        e1.addIdioma(Idiomas.idiomaEspanhol());

        e1.addIdioma(Idiomas.idiomaIngles());

        Aluno al2=new Aluno();
        Atendimento a2=new Atendimento();
        a2.setInicioAtendimento(new Date());
        Aluno al3=new Aluno();
        Atendimento a3=new Atendimento();
        Aluno al4=new Aluno();
        Atendimento a4=new Atendimento();
        Aluno al5=new Aluno();
        Atendimento a5=new Atendimento();

        al2.addAtendimento(a2);
        e1.addAtendimento(a2);
        al3.addAtendimento(a3);
        e1.addAtendimento(a3);
        al4.addAtendimento(a4);
        e1.addAtendimento(a4);
        al5.addAtendimento(a5);
        e1.addAtendimento(a5);

        this.atendimentoRepo.save(a1);
        this.atendimentoRepo.save(a2);
        this.atendimentoRepo.save(a3);
        this.atendimentoRepo.save(a4);
        this.atendimentoRepo.save(a5);

    }
}
