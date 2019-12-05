package esof.projeto.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
public class Faculdade extends BaseModel{

  private String nome;

  private String morada;

  @OneToMany(mappedBy = "cursos",cascade = CascadeType.PERSIST)
  @JsonManagedReference
  private Set<Curso> cursos;



}