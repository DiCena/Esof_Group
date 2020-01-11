package esof.projeto.services;

import esof.projeto.models.Cadeira;
import esof.projeto.models.Curso;
import esof.projeto.models.Faculdade;
import esof.projeto.repositories.CadeiraRepo;
import esof.projeto.repositories.CursoRepo;
import esof.projeto.repositories.FaculdadeRepo;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
class GestorServiceTest {

    @InjectMocks
    private GestorService gestorService;

    @Mock
    private FaculdadeRepo faculdadeRepo;

    @Mock
    private CursoRepo cursoRepo;

    @Mock
    private CadeiraRepo cadeiraRepo;

    @Test
    void createFaculdade() {
        Faculdade faculdade = new Faculdade();
        faculdade.setNome("f1");
        when(this.faculdadeRepo.save(faculdade)).thenReturn(faculdade);

        Optional<Faculdade> optionalFaculdade = this.gestorService.createFaculdade(faculdade);

        assertTrue(optionalFaculdade.isPresent());
        assertEquals(faculdade, optionalFaculdade.get());
    }

    @Test
    void createCursoByFaculdade() {
        Curso curso = new Curso();
        curso.setNome("c1");

        Faculdade faculdade = new Faculdade();
        faculdade.setNome("f1");

        when(this.faculdadeRepo.findByNome("f1")).thenReturn(Optional.of(faculdade));
        when(this.cursoRepo.save(curso)).thenReturn(curso);

        //faculdade existe e curso não existe na faculdade
        Optional<Curso> optionalCurso = gestorService.createCursoByFaculdade("f1", curso);

        assertTrue(optionalCurso.isPresent());
        assertEquals(curso, optionalCurso.get());

        //adicionar curso repetido
        Optional<Curso> optionalCursoDup = gestorService.createCursoByFaculdade("f1", curso);

        assertFalse(optionalCursoDup.isPresent());
        assertEquals(Optional.empty(), optionalCursoDup);

        //adicionar com faculdade inexistente
        Optional<Curso> optionalCursoNoFac = gestorService.createCursoByFaculdade("f2", curso);

        assertFalse(optionalCursoNoFac.isPresent());
        assertEquals(Optional.empty(), optionalCursoNoFac);

    }

    @Test
    void createCadeiraByCurso() {
        Cadeira cadeira = new Cadeira();
        cadeira.setNome("ca1");

        Curso curso = new Curso();
        curso.setNome("c1");

        when(this.cursoRepo.findByNome("c1")).thenReturn(Optional.of(curso));
        when(this.cadeiraRepo.save(cadeira)).thenReturn(cadeira);

        //curso existe e cadeira ainda não existe dentro deste
        Optional<Cadeira> optionalCadeira = gestorService.createCadeiraByCurso("c1", cadeira);

        assertTrue(optionalCadeira.isPresent());
        assertEquals(cadeira, optionalCadeira.get());

        //adicionar cadeira repetida
        Optional<Cadeira> optionalCadeiraDup = gestorService.createCadeiraByCurso("c1", cadeira);

        assertFalse(optionalCadeiraDup.isPresent());
        assertEquals(Optional.empty(), optionalCadeiraDup);

        //adicionar a um curso inexistente
        Optional<Cadeira> optionalCadeiraNoCur = gestorService.createCadeiraByCurso("c2", cadeira);

        assertFalse(optionalCadeiraNoCur.isPresent());
        assertEquals(Optional.empty(), optionalCadeiraNoCur);



    }
}