package esof.projeto.models.body;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AtendimentoBody {
    private String aluno;
    private String explicador;
    private LocalDate diaAtendimento;
    private LocalTime horaAtendimento;
}
