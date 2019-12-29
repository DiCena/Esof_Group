package esof.projeto.services.filters.explicadores;

import esof.projeto.models.Disponibilidade;
import esof.projeto.models.Explicador;
import esof.projeto.services.filters.FilterI;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Retorna os explicadores que têm na dispobilidade o dia da semana do dia
 */
public class ExplicadorFilterByDay implements FilterI<Explicador> {

    private LocalDate date;

    public ExplicadorFilterByDay(LocalDate date) {
        this.date = date;
    }

    @Override
    public Set<Explicador> filter(Set<Explicador> entities) {
        if (this.date == null) {
            return entities;
        }

        DayOfWeek startDateDayOfWeek = date.getDayOfWeek();
        Set<Explicador> explicadoresReturned = new HashSet<>();


        for (Explicador ex : entities) {
            for (Disponibilidade dis : ex.getDisponibilidades()) {
                if(dis!= null && dis.getDiaDaSemana().equals(startDateDayOfWeek)){
                    explicadoresReturned.add(ex);
                }
            }
        }
        return explicadoresReturned;
    }
}
