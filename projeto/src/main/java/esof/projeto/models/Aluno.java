package esof.projeto.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(value={("id")})
public class Aluno extends BaseModel {

  private String nome;

  @OneToMany(mappedBy = "aluno",cascade = CascadeType.PERSIST)
  @JsonManagedReference
  private Set<Atendimento> atendimentos=new HashSet<>();

  public Aluno(String nome) {
    this.nome = nome;
  }

  public void addAtendimento(Atendimento a){
      atendimentos.add(a);
      a.setAluno(this);
  }



}