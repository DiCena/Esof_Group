package esof.projeto.services.filters.explicadores;

import esof.projeto.models.Disponibilidade;
import esof.projeto.models.Explicador;
import esof.projeto.services.filters.FilterI;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Retorna explicadores com horas de fim de disponiblidade menor ou igual ao LocalTime
 */
public class ExplicadorFilterByEndHour implements FilterI<Explicador> {

    private LocalTime endTime;

    public ExplicadorFilterByEndHour(LocalTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public Set<Explicador> filter(Set<Explicador> entities) {

        if(this.endTime==null)
            return entities;
        Set<Explicador> explicadoresReturned = new HashSet<>();
        for(Explicador ex: entities)
            for(Disponibilidade dis: ex.getDisponibilidades()){
                if(dis!= null && (dis.getHorarioFim().isAfter(this.endTime) || dis.getHorarioFim().equals(endTime)))
                    explicadoresReturned.add(ex);
            }
        return explicadoresReturned;
    }
}

