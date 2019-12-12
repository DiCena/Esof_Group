package esof.projeto.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
public class Cadeira extends BaseModel{

    private String nome;

    private int ects;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Curso curso;

    @ManyToMany//nome na tabela
    private Set<Explicador> explicadores=new HashSet<>();

    public void addExplicador(Explicador explicador){
        if(!this.explicadores.contains(explicador)){
            this.explicadores.add(explicador);
            explicador.getCadeiras().add(this);
        }
    }
}