package esof.projeto.services.filters.explicadores;

import esof.projeto.models.Cadeira;
import esof.projeto.models.Curso;
import esof.projeto.models.Explicador;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ExplicadorFilterByCursoNameTest {

    @Test
    void filter() {
        String cursoNome="Cursooooo1";

        Set<Explicador> explicadores=new HashSet<>();

        Curso curso1=new Curso();
        curso1.setNome(cursoNome);

        Cadeira cadeira1=new Cadeira();
        Cadeira cadeira2=new Cadeira();

        Explicador explicador1=new Explicador();
        explicador1.setNome("exp1");
        Explicador explicador2=new Explicador();
        explicador2.setNome("exp2");
        Explicador explicador3=new Explicador();
        explicador3.setNome("exp3");

        curso1.addCadeira(cadeira1);
        curso1.addCadeira(cadeira2);


        explicador1.addCadeira(cadeira1);
        explicador2.addCadeira(cadeira1);
        explicador2.addCadeira(cadeira2);

        explicadores.add(explicador1);
        explicadores.add(explicador2);
        explicadores.add(explicador3);

        ExplicadorFilterByCursoName explicadorFilterByCursoName= new ExplicadorFilterByCursoName(cursoNome);
        assertEquals(2,explicadorFilterByCursoName.filter(explicadores).size());

        explicadorFilterByCursoName= new ExplicadorFilterByCursoName("non existing curso name");
        assertEquals(0,explicadorFilterByCursoName.filter(explicadores).size());

        explicadorFilterByCursoName= new ExplicadorFilterByCursoName(null);
        assertEquals(3,explicadorFilterByCursoName.filter(explicadores).size());
    }
}