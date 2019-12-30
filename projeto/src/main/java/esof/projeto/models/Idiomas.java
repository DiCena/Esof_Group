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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Data
@Entity
@JsonIgnoreProperties({"id"})
@NoArgsConstructor
public class Idiomas extends BaseModel{

    private static final String PORTUGUES="Portugues";
    private static final String INGLES="Ingles";
    private static final String FRANCES="Frances";
    private static final String ESPANHOL="Espanhol";

    private String name;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonBackReference(value = "explicador-idioma")
    private Explicador explicador;

    private Idiomas(String name){
        this.name=name;
    }

    public static Idiomas idiomaPortugues(){
        return new Idiomas(PORTUGUES);
    }

    public static Idiomas idiomaEspanhol(){
        return new Idiomas(ESPANHOL);
    }

    public static Idiomas idiomaFrances(){
        return new Idiomas(FRANCES);
    }

    public static Idiomas idiomaIngles(){
        return new Idiomas(INGLES);
    }

}

