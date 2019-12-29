package esof.projeto.services.filters.explicadores;

import esof.projeto.models.Cadeira;
import esof.projeto.models.Explicador;
import esof.projeto.services.filters.FilterI;

import java.util.HashSet;
import java.util.Set;

public class ExplicadorFilterByCursoName implements FilterI<Explicador> {

    private String cursoName;

    public ExplicadorFilterByCursoName(String cursoName) {
        this.cursoName = cursoName;
    }

    @Override
    public Set<Explicador> filter(Set<Explicador> entities) {
        if(this.cursoName==null || this.cursoName.isBlank()){
            return entities;
        }

        Set<Explicador> explicadoresReturned=new HashSet<>();

        for(Explicador ex: entities){
            for(Cadeira ca: ex.getCadeiras()){
                if(ca!=null && ca.getCurso()!=null){
                    if(ca.getCurso().getNome().equals(cursoName)){
                        explicadoresReturned.add(ex);
                        break;
                    }
                }
            }
        }
        return explicadoresReturned;
    }
}
