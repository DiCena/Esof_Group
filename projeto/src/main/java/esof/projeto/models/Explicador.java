package esof.projeto.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
public class Explicador extends BaseModel {

    @ManyToMany(mappedBy = "explicadores",cascade = CascadeType.PERSIST)
    private Set<Idiomas> idiomas;

    @OneToMany(mappedBy = "explicador",cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private Set<Atendimento> atendimentos;

    @ManyToMany(mappedBy = "explicadores",cascade = CascadeType.PERSIST)
    private Set<Cadeira> cadeiras;

    @OneToMany(mappedBy = "explicador",cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private Set<Disponibilidade> dia_disponivel;

    public void marcarDisponibilidade() {
    }

    public void desmarcarAtendimento() {
    }

    public Set<Atendimento> verCalendarioSemanal() {
        return null;
    }

    public void AtualizarIdiomas() {
    }

}