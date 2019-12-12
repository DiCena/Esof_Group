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

        Atendimento a1= new Atendimento();

        Aluno al1=new Aluno();
        al1.setNome("Luís");

        al1.addAtendimento(a1);
        e1.addAtendimento(a1);

        e1.addDisponibilidade(di1);

        //e1.addCadeira(ca1);

        //c1.addCadeira(ca1);

        //f1.addCurso(c1);

        this.atendimentoRepo.save(a1);

        assertEquals(1,this.alunoRepo.count());
        assertEquals(1,this.atendimentoRepo.count());
        assertEquals(0,this.cadeiraRepo.count());
        assertEquals(0,this.cursoRepo.count());
        assertEquals(1,this.disponibilidadeRepo.count());
        assertEquals(1,this.explicadorRepo.count());
        assertEquals(0,this.faculdadeRepo.count());





    }



}