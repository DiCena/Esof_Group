package esof.projeto.repositories;

import esof.projeto.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class AtendimentoRepoTest {


    @Autowired
    private AlunoRepo alunoRepo;

    @Autowired
    private AtendimentoRepo atendimentoRepo;

    @Autowired
    private CadeiraRepo cadeiraRepo;

    @Autowired
    private CursoRepo cursoRepo;

    @Autowired
    private DisponibilidadeRepo disponibilidadeRepo;

    @Autowired
    private ExplicadorRepo explicadorRepo;

    @Autowired
    private IdiomaRepo idiomaRepo;

    @Autowired
    private FaculdadeRepo faculdadeRepo;


    @Test
    public void test(){
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
        Disponibilidade di2=new Disponibilidade();
        Disponibilidade di3=new Disponibilidade();

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

        Idiomas i1 =new Idiomas();
        e1.addIdioma(i1);

        Idiomas i2 =new Idiomas();
        e1.addIdioma(i2);

        Aluno al2=new Aluno();
        Atendimento a2=new Atendimento();
        Aluno al3=new Aluno();
        Atendimento a3=new Atendimento();
        Aluno al4=new Aluno();
        Atendimento a4=new Atendimento();
        Aluno al5=new Aluno();
        Atendimento a5=new Atendimento();
        Aluno al6=new Aluno();
        Atendimento a6=new Atendimento();
        Aluno al7=new Aluno();
        Atendimento a7=new Atendimento();

        al2.addAtendimento(a2);
        e1.addAtendimento(a2);
        al3.addAtendimento(a3);
        e1.addAtendimento(a3);
        al4.addAtendimento(a4);
        e1.addAtendimento(a4);
        al5.addAtendimento(a5);
        e1.addAtendimento(a5);
        al6.addAtendimento(a6);
        e1.addAtendimento(a6);
        al7.addAtendimento(a7);
        e1.addAtendimento(a7);


        this.atendimentoRepo.save(a1);

        assertEquals(7,this.alunoRepo.count());
        assertEquals(7,this.atendimentoRepo.count());
        assertEquals(1,this.cadeiraRepo.count());
        assertEquals(2,this.cursoRepo.count());
        assertEquals(3,this.disponibilidadeRepo.count());
        assertEquals(1,this.explicadorRepo.count());
        assertEquals(2,this.idiomaRepo.count());
        assertEquals(1,this.faculdadeRepo.count());





    }



}