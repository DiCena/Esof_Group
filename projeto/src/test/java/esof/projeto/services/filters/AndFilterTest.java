package esof.projeto.services.filters;

import esof.projeto.models.Disponibilidade;
import esof.projeto.models.Explicador;
import esof.projeto.services.filters.explicadores.ExplicadorFilterByDay;
import esof.projeto.services.filters.explicadores.ExplicadorFilterByStartHour;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AndFilterTest {

    @Test
    void filter() {

        Set<Explicador> explicadores=new HashSet<>();

        Disponibilidade disponibilidade1=new Disponibilidade();
        disponibilidade1.setDiaDaSemana(DayOfWeek.MONDAY);
        disponibilidade1.setHorarioInicio(LocalTime.of(15,30));

        Disponibilidade disponibilidade2=new Disponibilidade();
        disponibilidade2.setDiaDaSemana(DayOfWeek.MONDAY);
        disponibilidade2.setHorarioInicio(LocalTime.of(18,30));

        Explicador explicador1 = new Explicador();
        explicador1.setNome("exp1");
        explicador1.addDisponibilidade(disponibilidade1);

        Explicador explicador2= new Explicador();
        explicador2.setNome("exp2");
        explicador2.addDisponibilidade(disponibilidade2);

        Explicador explicador3 = new Explicador();
        explicador3.setNome("exp3");
        explicador3.addDisponibilidade(disponibilidade1);


        explicadores.add(explicador1);
        explicadores.add(explicador2);
        explicadores.add(explicador3);

        ExplicadorFilterByDay explicadorFilterByDay=new ExplicadorFilterByDay(LocalDate.of(2019,12,30));
        ExplicadorFilterByStartHour explicadorFilterByStartHour=new ExplicadorFilterByStartHour(LocalTime.of(16,0));
        AndFilter andFilterExplicador = new AndFilter(explicadorFilterByDay,explicadorFilterByStartHour);
        assertEquals(2,andFilterExplicador.filter(explicadores).size());
        
    }
}