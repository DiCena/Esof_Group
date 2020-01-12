package esof.projeto.services.filters.explicadores;

import esof.projeto.models.*;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ExplicadorFilterServiceTest {

    @Test
    void filter() {
        // dados
        Set<Explicador> explicadores = new HashSet<>();
        Explicador explicador = new Explicador("jose");
        Explicador explicador1 = new Explicador("joao");
        Explicador explicador2 = new Explicador("ana");


        Curso curso = new Curso();
        curso.setNome("informatica");

        Curso curso1 = new Curso();
        curso1.setNome("psicologia");


        Cadeira cadeira = new Cadeira();
        cadeira.setNome("base dados");

        Cadeira cadeira1 = new Cadeira();
        cadeira1.setNome("aed");

        Cadeira cadeira2 = new Cadeira();
        cadeira1.setNome("psicopatia");

        curso.addCadeira(cadeira);
        curso.addCadeira(cadeira1);
        curso1.addCadeira(cadeira2);


        explicador.addCadeira(cadeira);
        explicador1.addCadeira(cadeira1);
        explicador2.addCadeira(cadeira2);

        explicadores.add(explicador);
        explicadores.add(explicador1);
        explicadores.add(explicador2);

        ExplicadorFilterService explicadorFilterService = new ExplicadorFilterService();

        //--
        // procurar pelo nome nose e do curso informatica

        Map<String,String> mapa = new HashMap<String, String>();
        mapa.put("nome" , "jose");
        mapa.put("curso","informatica");

        FilterObject filterObject = new FilterObject(mapa);

        //filterObject.imprimir();



        Set<Explicador> explicadorees = explicadorFilterService.filter(explicadores,filterObject);
        //System.out.println(explicadorees);
        assertEquals(1,explicadorees.size());

        //--
        // procurar pelo curso informatica

        Map<String,String> mapa1 = new HashMap<String, String>();
        mapa1.put("curso","informatica");

        FilterObject filterObject1 = new FilterObject(mapa1);
        //filterObject1.imprimir();

        Set<Explicador> explicadorees1 = explicadorFilterService.filter(explicadores,filterObject1);
        //System.out.println(explicadorees1);
        assertEquals(2,explicadorees1.size());

        //--
        // procurar com atendimento
        Disponibilidade disponibilidade = new Disponibilidade();
        disponibilidade.setDiaDaSemana(DayOfWeek.TUESDAY);
        disponibilidade.setHorarioInicio(LocalTime.NOON);
        disponibilidade.setHorarioFim(LocalTime.MIDNIGHT);

        explicador.addDisponibilidade(disponibilidade);

        //System.out.println(explicador);

        Map<String,String> mapa2 = new HashMap<String, String>();
        mapa2.put("nome" , "jose");
        mapa2.put("curso","informatica");
        mapa2.put("dia","2019-12-31");
        mapa2.put("horaFim","00:00");
        mapa2.put("horaInicio","12:00");


        FilterObject filterObject2 = new FilterObject(mapa2);
        //filterObject2.imprimir();
        Set<Explicador> explicadorees2 = explicadorFilterService.filter(explicadores,filterObject2);
        assertEquals(1,explicadorees2.size());

    }
}