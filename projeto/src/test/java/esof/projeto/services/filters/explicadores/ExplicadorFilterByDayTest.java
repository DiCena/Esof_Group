package esof.projeto.services.filters.explicadores;

import esof.projeto.models.Disponibilidade;
import esof.projeto.models.Explicador;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ExplicadorFilterByDayTest {

    @Test
    void filter() {

        Set<Explicador> explicadores=new HashSet<>();

        Disponibilidade disponibilidade1 = new Disponibilidade();
        disponibilidade1.setDiaDaSemana(DayOfWeek.MONDAY);

        Disponibilidade disponibilidade2 = new Disponibilidade();
        disponibilidade2.setDiaDaSemana(DayOfWeek.TUESDAY);

        Explicador explicador1=new Explicador();
        explicador1.setNome("exp1");
        explicador1.addDisponibilidade(disponibilidade1);

        Explicador explicador2= new Explicador();
        explicador2.setNome("exp2");
        explicador2.addDisponibilidade(disponibilidade1);

        Explicador explicador3=new Explicador();
        explicador3.addDisponibilidade(disponibilidade2);

        explicadores.add(explicador1);
        explicadores.add(explicador2);
        explicadores.add(explicador3);

        ExplicadorFilterByDay explicadorFilterByDay=new ExplicadorFilterByDay(LocalDate.of(2019,12,30));
        assertEquals(2,explicadorFilterByDay.filter(explicadores).size());

        explicadorFilterByDay=new ExplicadorFilterByDay(null);
        assertEquals(3,explicadorFilterByDay.filter(explicadores).size());

    }
}