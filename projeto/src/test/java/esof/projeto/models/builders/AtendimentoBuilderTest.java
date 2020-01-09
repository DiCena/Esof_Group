package esof.projeto.models.builders;

import esof.projeto.models.Aluno;
import esof.projeto.models.Atendimento;
import esof.projeto.models.Explicador;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class AtendimentoBuilderTest {

    @Test
    void build() {
        AtendimentoBuilder atendimentoBuilder = new AtendimentoBuilder();
        atendimentoBuilder.setAluno(new Aluno())
                .setDiaAtendimento(LocalDate.now())
                .setHoraAtendimento(LocalTime.now())
                .setExplicador(new Explicador());
        Atendimento atendimento=atendimentoBuilder.build();

        assertNotNull(atendimento.getAluno());
        assertNotNull(atendimento.getDiaAtendimento());
        assertNotNull(atendimento.getHoraAtendimento());
        assertNotNull(atendimento.getExplicador());
    }
}