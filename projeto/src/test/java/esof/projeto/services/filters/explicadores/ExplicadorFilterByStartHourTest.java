package esof.projeto.services.filters.explicadores;

import esof.projeto.models.Disponibilidade;
import esof.projeto.models.Explicador;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ExplicadorFilterByStartHourTest {

    @Test
    void filter() {
        Set<Explicador> explicadores = new HashSet<>();

        Disponibilidade disponibilidade1 = new Disponibilidade();
        disponibilidade1.setHorarioInicio(LocalTime.of(14, 30));

        Disponibilidade disponibilidade2 = new Disponibilidade();
        disponibilidade2.setHorarioInicio(LocalTime.of(16, 0));

        Explicador explicador1 = new Explicador();
        explicador1.setNome("exp1");
        explicador1.addDisponibilidade(disponibilidade1);

        Explicador explicador2 = new Explicador();
        explicador2.setNome("exp2");
        explicador2.addDisponibilidade(disponibilidade1);

        Explicador explicador3 = new Explicador();
        explicador2.setNome("exp3");
        explicador3.addDisponibilidade(disponibilidade2);

        explicadores.add(explicador1);
        explicadores.add(explicador2);
        explicadores.add(explicador3);

        ExplicadorFilterByStartHour explicadorFilterByStartHour = new ExplicadorFilterByStartHour(LocalTime.of(15, 30));
        assertEquals(2, explicadorFilterByStartHour.filter(explicadores).size());

        explicadorFilterByStartHour = new ExplicadorFilterByStartHour(null);
        assertEquals(3, explicadorFilterByStartHour.filter(explicadores).size());
    }
}