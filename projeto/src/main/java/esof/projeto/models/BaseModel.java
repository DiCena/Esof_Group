package esof.projeto.models;

import lombok.EqualsAndHashCode;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


//Modelo pai de todos os outros, permites generalizar os filtros
@MappedSuperclass
@EqualsAndHashCode
public class BaseModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;


    public Long getId() {
        return this.Id;
    }
}
