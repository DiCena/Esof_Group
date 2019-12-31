package esof.projeto.services.filters.explicadores;


import esof.projeto.models.Explicador;
import esof.projeto.services.filters.AndFilter;
import esof.projeto.services.filters.FilterI;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ExplicadorFilterService {

    /**
     * Filtra explicadores com os parametros e devolve os explicadores filtrados
     * @param explicadores conjunto de explicadores a filtrar
     * @param filterObject objeto filtro com os parametros
     * @return explicadores filtrados
     */
    public Set<Explicador> filter(Set<Explicador> explicadores , FilterObject filterObject){

        FilterI<Explicador> explicadorNomeFilter = new ExplicadorFilterByName(filterObject.getNome());

        FilterI<Explicador> explicadorCursoFilter = new ExplicadorFilterByCursoName(filterObject.getCursoName());

        FilterI<Explicador> explicadorDayFilter = new ExplicadorFilterByDay(filterObject.getDia());
        FilterI<Explicador> explicadorEndHour = new ExplicadorFilterByEndHour(filterObject.getHoraFim());

        FilterI<Explicador> explicadorStartHour = new ExplicadorFilterByStartHour(filterObject.getHoraInicio());


        FilterI<Explicador> nomeAndCursoFilter = new AndFilter(explicadorNomeFilter,explicadorCursoFilter);
        FilterI<Explicador> dayAndEndHourFilter = new AndFilter(explicadorDayFilter,explicadorEndHour);

        FilterI<Explicador> nomeAndCursoAndStartHourFilter = new AndFilter(nomeAndCursoFilter,explicadorStartHour);

        return new AndFilter(dayAndEndHourFilter,nomeAndCursoAndStartHourFilter).filter(explicadores);

     }
}
