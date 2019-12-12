package esof.projeto.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;



@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
public class Idiomas extends BaseModel{

    private enum idioma {
        PORTUGUES , INGLES , FRANCES , ESPANHOL;
    }

    @ManyToMany
    private Set<Explicador> explicadores=new HashSet<>();


}
