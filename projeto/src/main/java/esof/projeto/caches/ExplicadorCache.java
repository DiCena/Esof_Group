package esof.projeto.caches;

import esof.projeto.models.Explicador;

import java.util.HashSet;
import java.util.Optional;
import java.util.Stack;


/**
 * Cache: implementa logica de MRU ( Most Recent Used )
 * Existe apenas uma instancia da cache ( singleton )
 * Novos explicadores sao adicionados na cache em primeiro lugar e antigos sao
 * empurrados para posicoes abaixo.
 * Explicadores adicionados ja existentes sao atualizados de lugar.
 * Maximo numero de explicadores na cache por defeito = 5.
 */
public class ExplicadorCache {

    private static ExplicadorCache instance = null;

    private static int max;

    private Stack<Explicador> MRU;

    private ExplicadorCache(){
        this.MRU = new Stack<Explicador>();
        max = 5;
    }

    public static ExplicadorCache getInstance(){
        return instance==null? new ExplicadorCache() : instance;
    }

    /**
     * Procura explicador por nome na stack MRU.
     * @param nome nome do explicador a procurar em MRU.
     * @return Explicador
     */
    public Optional<Explicador> consultarCache(String nome) {
        for(Explicador explicador : this.MRU)
            if(explicador.getNome().equals(nome))
                return Optional.of(explicador);
        return Optional.empty();
    }


    /**
     * Adiciona um explicador na stack MRU.
     * Caso tenha atingido o tamanho max, elemina-se o explicador
     * menos usado.
     * Caso o explicador ja estivesse na cache, atualiza o valor
     * @param explicador a adicionar na cache
     */
    public void addExplicador(Explicador explicador) {
        if(consultarCache(explicador.getNome()).isPresent())this.MRU.removeElement(explicador); // Atualiza a posicao do explicador procurado
        else if(this.MRU.size() == max) this.MRU.remove(max-1);
        this.MRU.add(0,explicador);
    }

    /**
     * Imprime a cache
     */
    public void imprimirCache() {
        for( int i = 0 ; i < this.MRU.size(); i++) {
            Explicador explicador = this.MRU.elementAt(i);
            if(explicador != null) System.out.println("pos. " + i + " nome." + explicador.getNome() + "\n");
        }
    }

    /**
     * Limpa a stack MRU
     */
    public void limpar(){
        this.MRU.clear();
    }

    /**
     * Procura index de um certo explicador na stack
     * @param explicador a procurar index na stack
     * @return index da posicao do explicador na stack
     */
    public int index(Explicador explicador){
        return this.MRU.indexOf(explicador);
    }


    /**
     * Tamanho da cache
     * @return tamanho da cache
     */
    public int tamanho() {
        return this.MRU.size();
    }

    /**
     * Altera o tamanho maximo da stack
     * @param maximo novo maximo da cache
     */
    public void mudarMax(int maximo){
        max = maximo;
    }
}
