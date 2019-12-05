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

    /*
    Como vai ser representar na bd um exp com mais que um idioma?
     */
    private Set<String> idiomas;

    @OneToMany(mappedBy = "atendimento",cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private Set<Atendimento> atendimentos;

    @ManyToMany(mappedBy = "cadeirasEnsinadas")
    private Set<Cadeira> cadeiras;

    @OneToMany(mappedBy = "dia_disponivel",cascade = CascadeType.PERSIST)
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