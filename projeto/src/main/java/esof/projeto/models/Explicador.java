package esof.projeto.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"id","cadeiras"})
public class Explicador extends BaseModel {

    private String nome;

    public Explicador(String nome) {
        this.nome = nome;
    }



    @OneToMany(mappedBy = "explicador", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonManagedReference(value = "explicador-atendimento")
    private Set<Atendimento> atendimentos = new HashSet<>();

    @ManyToMany(mappedBy = "explicadores", cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Set<Cadeira> cadeiras = new HashSet<>();

    @OneToMany(mappedBy = "explicador", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private Set<Disponibilidade> disponibilidades = new HashSet<>();


    public void addAtendimento(Atendimento atendimento) {
            atendimentos.add(atendimento);
            atendimento.setExplicador(this);
    }

    public void addCadeira(Cadeira cadeira) {
            cadeiras.add(cadeira);
            cadeira.getExplicadores().add(this);
    }

    public void addDisponibilidade(Disponibilidade disponibilidade) {
        disponibilidades.add(disponibilidade);
        disponibilidade.setExplicador(this);
}

    /**
     * Função que verifica se é possivel marcar o atendimento no horário desejado
     * @return true se for marcado com sucesso
     */
    public boolean criarAtendimento(Aluno aluno, LocalDate data, LocalTime hora){

        Atendimento atendimento=new Atendimento();
        atendimento.setExplicador(this);
        atendimento.setAluno(aluno);
        atendimento.setDiaAtendimento(data);
        atendimento.setHoraAtendimento(hora);

        for(Atendimento a:this.getAtendimentos()){
            if(a.getDiaAtendimento().compareTo(data)==0 && a.getHoraAtendimento().compareTo(hora)==0) //já tem um atendimento nesta hora
                return false;
        }

        DayOfWeek atendimentoDayWeek= atendimento.getDiaAtendimento().getDayOfWeek();

        for(Disponibilidade d:this.getDisponibilidades()){
            if(atendimentoDayWeek==d.getDiaDaSemana()) { //mesmo dia da semana
                //comparar inicio disponibilidade com inicio atendimento e fim disponibilidade com fim atendimento +1 hora
                if ((d.getHorarioInicio().compareTo(LocalTime.from(atendimento.getHoraAtendimento())) > 0) || (d.getHorarioFim().compareTo(LocalTime.from(atendimento.getHoraAtendimento().plus(1, ChronoUnit.HOURS))) < 0)) {
                    return false; //atendimento adicionado com sucesso
                }
            }
        }
        this.addAtendimento(atendimento);
        aluno.addAtendimento(atendimento);
        return true;
    }



}