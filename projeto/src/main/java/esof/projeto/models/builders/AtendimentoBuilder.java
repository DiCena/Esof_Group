package esof.projeto.models.builders;

import esof.projeto.models.Aluno;
import esof.projeto.models.Atendimento;
import esof.projeto.models.BaseModel;
import esof.projeto.models.Explicador;

import java.time.LocalDate;
import java.time.LocalTime;

public class AtendimentoBuilder extends BaseModel {


    private LocalDate diaAtendimento;

    private LocalTime horaAtendimento;

    private Aluno aluno;

    private Explicador explicador;



    public AtendimentoBuilder setDiaAtendimento(LocalDate diaAtendimento) {
        this.diaAtendimento = diaAtendimento;
        return this;
    }

    public AtendimentoBuilder setHoraAtendimento(LocalTime horaAtendimento) {
        this.horaAtendimento = horaAtendimento;
        return this;
    }

    public AtendimentoBuilder setAluno(Aluno aluno) {
        this.aluno = aluno;
        return this;
    }

    public AtendimentoBuilder setExplicador(Explicador explicador) {
        this.explicador = explicador;
        return this;
    }

    public Atendimento build(){
        return new Atendimento(diaAtendimento,horaAtendimento,aluno,explicador);
    }
}
