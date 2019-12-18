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


@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@NoArgsConstructor
public class Cadeira extends BaseModel{

    private String nome;

    private int ects;

    public Cadeira(String nome , int ects) {
        this.nome = nome;
        this.ects = ects;
    }

    @ManyToOne(cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Curso curso;

    @ManyToMany//nome na tabela
    @EqualsAndHashCode.Exclude
    private Set<Explicador> explicadores=new HashSet<>();

    public void addExplicador(Explicador explicador){
            this.explicadores.add(explicador);
            explicador.getCadeiras().add(this);
    }

}