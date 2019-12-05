package esof.projeto.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import java.time.DayOfWeek;
import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
public class Disponibilidade extends BaseModel{

  private DayOfWeek diaDaSemana;

  private LocalTime horarioInicio;

  private LocalTime horarioFim;

}