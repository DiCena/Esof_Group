package esof.projeto.services.filters.explicadores;

import esof.projeto.models.Disponibilidade;
import esof.projeto.models.Explicador;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ExplicadorFilterByEndHourTest {

    @Test
    void filter() {
        Set<Explicador> explicadores=new HashSet<>();

        Disponibilidade disponibilidade1 = new Disponibilidade();
        disponibilidade1.setHorarioFim(LocalTime.of(18,30));

        Disponibilidade disponibilidade2 = new Disponibilidade();
        disponibilidade2.setHorarioFim(LocalTime.of(20,0));

        Explicador explicador1=new Explicador();
        explicador1.setNome("exp1");
        explicador1.addDisponibilidade(disponibilidade1);

        Explicador explicador2= new Explicador();
        explicador2.setNome("exp2");
        explicador2.addDisponibilidade(disponibilidade2);

        Explicador explicador3=new Explicador();
        explicador2.setNome("exp3");
        explicador3.addDisponibilidade(disponibilidade2);

        explicadores.add(explicador1);
        explicadores.add(explicador2);
        explicadores.add(explicador3);

        ExplicadorFilterByEndHour explicadorFilterByEndHour=new ExplicadorFilterByEndHour(LocalTime.of(19,30));
        assertEquals(2,explicadorFilterByEndHour.filter(explicadores).size());

        explicadorFilterByEndHour=new ExplicadorFilterByEndHour(null);
        assertEquals(3,explicadorFilterByEndHour.filter(explicadores).size());
    }
}