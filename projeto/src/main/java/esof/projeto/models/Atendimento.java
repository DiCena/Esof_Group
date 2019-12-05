package esof.projeto.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.DayOfWeek;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
public class Atendimento extends BaseModel {

    private DayOfWeek diaDaSemana;

    private LocalTime horarioAtendimento;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Aluno aluno;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Explicador explicador;

}