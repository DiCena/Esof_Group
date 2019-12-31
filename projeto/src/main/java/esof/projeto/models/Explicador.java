package esof.projeto.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@NoArgsConstructor
@JsonIgnoreProperties({"id"})
public class Explicador extends BaseModel {

    private String nome;

    public Explicador(String nome) {
        this.nome = nome;
    }

    @OneToMany(cascade =  {CascadeType.ALL},orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonManagedReference(value = "explicador-idioma")
    private Set<Idiomas> idiomas = new HashSet<>();

    @OneToMany(mappedBy = "explicador", cascade = {CascadeType.ALL},orphanRemoval = true)
    //@ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonManagedReference(value = "explicador-atendimento")
    //@Getter(AccessLevel.NONE)
    private Set<Atendimento> atendimentos = new HashSet<>();

    @ManyToMany(mappedBy = "explicadores", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private Set<Cadeira> cadeiras = new HashSet<>();

    @OneToMany(mappedBy = "explicador",  cascade = {CascadeType.ALL},orphanRemoval = true)
    @JsonManagedReference
    private Set<Disponibilidade> disponibilidades = new HashSet<>();

    //public Set<Atendimento> getAtendimentos() {
    //    return Collections.unmodifiableSet(atendimentos);
   // }

    public void addAtendimento(Atendimento atendimento) {
            atendimentos.add(atendimento);
            atendimento.setExplicador(this);
    }

    public void removeAtendimento(Atendimento atendimento){
        this.atendimentos.remove(atendimento);
        atendimento.setExplicador(null);
        if(atendimento.getAluno()!=null) {
            atendimento.getAluno().getAtendimentos().remove(atendimento);
        }
        atendimento.setAluno(null);
    }

    public void removeAllAtendimentos() {
        for(Atendimento atendimento : this.atendimentos) {
            removeAtendimento(atendimento);
        }
    }

    public void addAtendimentos(Set<Atendimento> atendimentos) {
        for(Atendimento atendimento : atendimentos) {
            addAtendimento(atendimento);
        }
    }


    public void mudarAtendimentos(Set<Atendimento> atendimentos) {
        this.atendimentos.clear();
        for(Atendimento a : atendimentos) {
            addAtendimento(a);
        }
    }

    public void addCadeira(Cadeira cadeira) {
            cadeiras.add(cadeira);
            cadeira.getExplicadores().add(this);
    }

    public void removeCadeira(Cadeira cadeira) {
        this.cadeiras.remove(cadeira);
        cadeira.getExplicadores().remove(this);
    }

    public void addDisponibilidade(Disponibilidade disponibilidade) {
        disponibilidades.add(disponibilidade);
        disponibilidade.setExplicador(this);
    }

    public void removeDisponibilidade(Disponibilidade disponibilidade) {
        this.disponibilidades.remove(disponibilidade);
        disponibilidade.setExplicador(null);
    }

    public void addIdioma(Idiomas idioma) {
        idiomas.add(idioma);
    }

    public void removeIdioma(Idiomas idioma) {
        this.idiomas.remove(idioma);
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




    public void removeAllCadeiras() {
        for(Cadeira cadeira : this.cadeiras) {
            this.removeCadeira(cadeira);
        }
    }

    @ToString.Include
    public Long id(){
        return this.getId();
    }

    public void addCadeiras(Set<Cadeira> cadeiras) {
        for(Cadeira cadeira: cadeiras) {
            addCadeira(cadeira);
        }
    }

    public void removeAllDisponibilidades() {
        for(Disponibilidade disponibilidade: this.disponibilidades) {
            removeDisponibilidade(disponibilidade);
        }
    }

    public void addDisponibilidades(Set<Disponibilidade> disponibilidades) {
        for(Disponibilidade disponibilidade : disponibilidades) {
            this.addDisponibilidade(disponibilidade);
        }
    }

    public void removeAllIdiomas() {
        for(Idiomas idiomas : this.idiomas) {
            this.removeIdioma(idiomas);
        }
    }

    public void addIdiomas(Set<Idiomas> idiomas) {
        for(Idiomas idiomas1 : idiomas) {
            addIdioma(idiomas1);
        }
    }

}