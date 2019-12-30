package esof.projeto.caches;

import esof.projeto.models.Explicador;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ExplicadorCacheTest {

    @Test
    void consultarCache() {
        ExplicadorCache cache = ExplicadorCache.getInstance();
        Explicador explicador = new Explicador("ana");
        Explicador explicador1 = new Explicador("jose");
        Explicador explicador2 = new Explicador("joao");

        cache.addExplicador(explicador);
        cache.addExplicador(explicador1);
        cache.addExplicador(explicador2);

        // existem
        Optional<Explicador> optional = cache.consultarCache(explicador.getNome());
        Optional<Explicador> optional1 = cache.consultarCache(explicador1.getNome());
        Optional<Explicador> optional2= cache.consultarCache(explicador2.getNome());
        // nao existe
        Optional<Explicador> optional3= cache.consultarCache("rolotes");


        assertTrue(optional.isPresent());
        assertTrue(optional1.isPresent());
        assertTrue(optional2.isPresent());
        assertFalse(optional3.isPresent());

        //cache.imprimirCache();
        cache.limpar();
    }

    @Test
    void addExplicador() {
        ExplicadorCache cache = ExplicadorCache.getInstance();

        // Adicionar 3 explicadores distintos
        Explicador explicador = new Explicador("ana");
        Explicador explicador1 = new Explicador("jose");
        Explicador explicador2 = new Explicador("joao");

        cache.addExplicador(explicador);
        cache.addExplicador(explicador1);
        cache.addExplicador(explicador2);

        //cache.imprimirCache();

        assertEquals(3,cache.tamanho());

        // -----

        // Adicionar explicador repetido e refrescar lugar

        Explicador explicador3 = new Explicador("jose");

        cache.addExplicador(explicador3);

        //cache.imprimirCache();


        assertEquals(3,cache.tamanho());    // Tem na mesma 3 explicadores
        assertEquals(0,cache.index(explicador3));   // Agora esta no topo da cache

        // -----

        // Testar o max de cache

        Explicador explicador4 = new Explicador("joaquim");
        Explicador explicador5 = new Explicador("silvia");
        Explicador explicador6 = new Explicador("jorge");
        Explicador explicador7 = new Explicador("aida");

        cache.addExplicador(explicador4);
        cache.addExplicador(explicador5);
        cache.addExplicador(explicador6);
        cache.addExplicador(explicador7);

        assertEquals(5,cache.tamanho());

        //cache.imprimirCache();
        cache.limpar();
    }

    @Test
    void mudarMax(){
        ExplicadorCache cache = ExplicadorCache.getInstance();
        Explicador explicador = new Explicador("ana");
        Explicador explicador1 = new Explicador("jose");
        Explicador explicador2 = new Explicador("joao");
        Explicador explicador4 = new Explicador("joaquim");
        Explicador explicador5 = new Explicador("silvia");

        cache.addExplicador(explicador);
        cache.addExplicador(explicador1);
        cache.addExplicador(explicador2);
        cache.addExplicador(explicador4);
        cache.addExplicador(explicador5);

        assertEquals(5,cache.tamanho());

        cache.mudarMax(7);

        Explicador explicador6 = new Explicador("jorge");
        Explicador explicador7 = new Explicador("aida");

        cache.addExplicador(explicador6);
        cache.addExplicador(explicador7);


        assertEquals(7,cache.tamanho());

        //cache.imprimirCache();
        cache.limpar();
    }

}