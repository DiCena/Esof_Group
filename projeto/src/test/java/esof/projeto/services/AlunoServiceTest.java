package esof.projeto.services;

import esof.projeto.models.Aluno;
import esof.projeto.repositories.AlunoRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.HashSet;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AlunoServiceTest {

    @Mock
    private AlunoRepo alunoRepo;

    @InjectMocks
    private AlunoService alunoService;

    @Test
    void findAll() {
        Aluno aluno1 = new Aluno("a1");
        Aluno aluno2 = new Aluno("a2");
        Aluno aluno3 = new Aluno("a3");

        HashSet<Aluno> hs = new HashSet<>();
        hs.add(aluno1);
        hs.add(aluno2);
        hs.add(aluno3);
        when(this.alunoRepo.findAll()).thenReturn(hs);


        assertEquals(3, alunoService.findAll().size());
    }

    @Test
    void findById() {
        Aluno aluno1 = new Aluno("a1");
        Aluno aluno2 = new Aluno("a2");

        when(this.alunoRepo.findById(1L)).thenReturn(Optional.of(aluno1));
        when(this.alunoRepo.findById(2L)).thenReturn(Optional.of(aluno2));

        assertTrue(alunoService.findById(1L).isPresent());
        assertTrue(alunoService.findById(2L).isPresent());
        assertFalse(alunoService.findById(3L).isPresent());


    }

    @Test
    void createAluno() {
        Aluno aluno = new Aluno("a1");
        when(this.alunoRepo.save(aluno)).thenReturn(aluno);

        Optional<Aluno> optionalAluno = this.alunoService.createAluno(aluno);

        assertTrue(optionalAluno.isPresent());
        assertEquals(aluno, optionalAluno.get());

    }
}