package esof.projeto;

import esof.projeto.models.*;
import esof.projeto.repositories.AlunoRepo;
import esof.projeto.repositories.AtendimentoRepo;
import esof.projeto.repositories.ExplicadorRepo;
import esof.projeto.repositories.IdiomaRepo;
import esof.projeto.services.IdiomaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private AtendimentoRepo atendimentoRepo;
    private IdiomaRepo idiomaRepo;
    private IdiomaService idiomaService;


    @Autowired
    public Bootstrap(AtendimentoRepo atendimentoRepo,IdiomaRepo idiomaRepo) {
        this.idiomaRepo=idiomaRepo;
        this.atendimentoRepo=atendimentoRepo;
    }

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("Startup");

        idiomaRepo.save(Idiomas.idiomaIngles());
        idiomaRepo.save(Idiomas.idiomaFrances());
        idiomaRepo.save(Idiomas.idiomaPortugues());
        idiomaRepo.save(Idiomas.idiomaEspanhol());

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
        Explicador e2=new Explicador();
        e2.setNome("Luis");

        Disponibilidade di1=new Disponibilidade();
        Disponibilidade di2=new Disponibilidade();
        Disponibilidade di3=new Disponibilidade();

        Atendimento a1= new Atendimento();
        Atendimento a2= new Atendimento();

        Aluno al1=new Aluno();
        al1.setNome("Luís");

        al1.addAtendimento(a1);
        e1.addAtendimento(a1);

        e2.addAtendimento(a2);


        e1.addDisponibilidade(di1);
        e1.addDisponibilidade(di2);
        e1.addDisponibilidade(di3);

        e1.addCadeira(ca1);
        e2.addCadeira(ca1);
        c1.addCadeira(ca1);

        f1.addCurso(c1);

        f1.addCurso(c2);


        e1.addIdioma(idiomaService.espanhol());
        e2.addIdioma(idiomaService.espanhol());
        e2.addIdioma(idiomaService.ingles());

        e1.addIdioma(Idiomas.idiomaEspanhol());

        Aluno al2=new Aluno("T");
        Aluno al3=new Aluno("E");
        Atendimento a3=new Atendimento();


        al2.addAtendimento(a2);
        e1.addAtendimento(a2);
        al3.addAtendimento(a3);
        e1.addAtendimento(a3);



        this.atendimentoRepo.save(a1);
        this.atendimentoRepo.save(a2);
        this.atendimentoRepo.save(a3);




    }
}
