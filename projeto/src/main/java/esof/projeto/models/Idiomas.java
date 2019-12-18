package esof.projeto.models;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Idiomas extends BaseModel{

    private static final String PORTUGUES="Portugues";
    private static final String INGLES="Ingles";
    private static final String FRANCES="Frances";
    private static final String ESPANHOL="Espanhol";

    @Getter
    @Column(unique = true)
    private String name;

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

