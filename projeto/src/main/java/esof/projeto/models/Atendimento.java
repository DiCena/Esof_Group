package esof.projeto.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalTime;


@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@NoArgsConstructor
public class Atendimento extends BaseModel {

    private LocalDate diaAtendimento;

    private LocalTime horaAtendimento;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    private Aluno aluno;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Explicador explicador;

}