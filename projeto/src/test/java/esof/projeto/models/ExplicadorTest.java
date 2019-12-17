package esof.projeto.models;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ExplicadorTest {
    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Test
    void addIdioma() {



    }

    @Test
    void addAtendimento() {
        Atendimento a1 = new Atendimento();

        Atendimento a2 = new Atendimento();

        Explicador e1 = new Explicador();

        e1.addAtendimento(a1);

        e1.addAtendimento(a2);

        // Tem que conter o primeiro e segundo atendimento adicionado
        assertTrue(e1.getAtendimentos().contains(a1));
        assertTrue(e1.getAtendimentos().contains(a2));

        e1.addAtendimento(a1);

        // Atendimento ja existente , nao pode adicionar outro igual
        assertEquals(e1.getAtendimentos().size(),2);
    }

    @Test
    void addCadeira() {
        Explicador e1 = new Explicador();

        Cadeira c1 = new Cadeira("BD",15);

        Cadeira c2 = new Cadeira("AED",10);

        Cadeira c3 = new Cadeira("AED",10);

        e1.addCadeira(c1);
        e1.addCadeira(c2);
        e1.addCadeira(c3);

        logger.debug(c3.toString());
        logger.debug(c2.toString());


        assertTrue(c3.hashCode() == c2.hashCode());

        assertEquals(2,e1.getCadeiras().size());


    }

    @Test
    void addDisponibilidade() {






    }
}