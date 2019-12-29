package esof.projeto.services.filters.explicadores;

import esof.projeto.models.Explicador;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ExplicadorFilterByNameTest {

    @Test
    void filter() {

        Set<Explicador> explicadores = new HashSet<>();
        Explicador explicador1 = new Explicador();
        explicador1.setNome("exp1");
        Explicador explicador2 = new Explicador();
        explicador2.setNome("exp2");
        Explicador explicador3 = new Explicador();
        explicador3.setNome("exp3");

        explicadores.add(explicador1);
        explicadores.add(explicador2);
        explicadores.add(explicador3);

        ExplicadorFilterByName explicadorFilterByName = new ExplicadorFilterByName("exp1");
        assertEquals(1, explicadorFilterByName.filter(explicadores).size());

        explicadorFilterByName= new ExplicadorFilterByName("non existing name");
        assertEquals(0,explicadorFilterByName.filter(explicadores).size());

        explicadorFilterByName= new ExplicadorFilterByName(null);
        assertEquals(3,explicadorFilterByName.filter(explicadores).size());

    }
}