package esof.projeto;

import esof.projeto.models.Aluno;
import esof.projeto.repositories.AlunoRepo;
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

    private AlunoRepo alunoRepo;

    @Autowired
    public Bootstrap(AlunoRepo alunoRepo) {
        this.alunoRepo = alunoRepo;
    }

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("Startup");
        Aluno a1=new Aluno("capela");

        this.alunoRepo.save(a1);
    }
}
