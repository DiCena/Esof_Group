package esof.projeto.services;

import esof.projeto.models.Idiomas;
import esof.projeto.repositories.IdiomaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Para ao adicionar o mesmo idioma em explicadores diferentes este não ser repetido na bd
@Service
public class IdiomaService {

    private IdiomaRepo idiomaRepo;

    @Autowired
    public IdiomaService(IdiomaRepo idiomaRepo) {
        this.idiomaRepo = idiomaRepo;
    }

    public Idiomas espanhol(){
        return this.idiomaRepo.findByName(Idiomas.idiomaEspanhol().getName());
    }
    public Idiomas portugues(){
        return this.idiomaRepo.findByName(Idiomas.idiomaPortugues().getName());
    }
    public Idiomas ingles(){
        return this.idiomaRepo.findByName(Idiomas.idiomaIngles().getName());
    }
    public Idiomas frances(){
        return this.idiomaRepo.findByName(Idiomas.idiomaFrances().getName());
    }
}
