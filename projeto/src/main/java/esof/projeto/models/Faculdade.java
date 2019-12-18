package esof.projeto.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@NoArgsConstructor
public class Faculdade extends BaseModel{

  private String nome;

  private String morada;

  @OneToMany(mappedBy = "faculdade",cascade = CascadeType.PERSIST)
  @JsonManagedReference
  private Set<Curso> cursos=new HashSet<>();;

  public void addCurso(Curso curso){
      cursos.add(curso);
      curso.setFaculdade(this);

  }

}