package esof.projeto.services.filters.explicadores;

import esof.projeto.models.Disponibilidade;
import esof.projeto.models.Explicador;
import esof.projeto.services.filters.FilterI;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Retorna explicadores com horas de inicio de disponiblidade maior ou igual ao LocalTime
 */
public class ExplicadorFilterByStartHour implements FilterI<Explicador> {

    private LocalTime startTime;

    public ExplicadorFilterByStartHour(LocalTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public Set<Explicador> filter(Set<Explicador> entities) {

        if(this.startTime==null)
            return entities;
        Set<Explicador> explicadoresReturned = new HashSet<>();
        for(Explicador ex: entities)
            for(Disponibilidade dis: ex.getDisponibilidades()){
                if(dis!= null && (dis.getHorarioInicio().isBefore(this.startTime) || dis.getHorarioInicio().equals(startTime)))
                    explicadoresReturned.add(ex);
            }
        return explicadoresReturned;
    }
}
