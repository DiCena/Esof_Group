package esof.projeto.services.filters.explicadores;

import esof.projeto.models.Explicador;
import esof.projeto.services.filters.FilterI;

import java.util.HashSet;
import java.util.Set;

public class ExplicadorFilterByName implements FilterI<Explicador> {

    private String name;

    public ExplicadorFilterByName(String name) {
        this.name = name;
    }


    @Override
    public Set<Explicador> filter(Set<Explicador> entities) {
        if(name==null || name.isEmpty()){
            return entities;
        }
        Set<Explicador> explicadores=new HashSet<>();
        for(Explicador explicador:entities){
            if(explicador.getNome()!=null && explicador.getNome().equalsIgnoreCase(name)){
                explicadores.add(explicador);
            }
        }
        return explicadores;
    }
}
