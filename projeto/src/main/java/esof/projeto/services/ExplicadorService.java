package esof.projeto.services;


import esof.projeto.models.Explicador;
import esof.projeto.repositories.ExplicadorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ExplicadorService {


    private ExplicadorRepo explicadorRepo;

    @Autowired
    public ExplicadorService(ExplicadorRepo explicadorRepo) {
        this.explicadorRepo = explicadorRepo;
    }


    public Set<Explicador> findAll() {
        Set<Explicador> explicadores = new HashSet<>();
            for(Explicador exp : this.explicadorRepo.findAll()) {
                explicadores.add(exp);
            }
            return explicadores;
    }
}
