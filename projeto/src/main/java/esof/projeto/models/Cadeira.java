package esof.projeto.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;


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

    @ManyToMany(mappedBy = "explicadores")//nome na tabela
    private Set<Explicador> explicadores;

}