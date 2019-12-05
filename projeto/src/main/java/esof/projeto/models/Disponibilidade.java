package esof.projeto.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Data
@Entity
@NoArgsConstructor
public class Disponibilidade {

  private DayOfWeek diaDaSemana;

  private LocalTime horarioInicio;

  private LocalTime horarioFim;

}