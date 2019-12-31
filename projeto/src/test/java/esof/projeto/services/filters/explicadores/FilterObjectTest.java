package esof.projeto.services.filters.explicadores;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class FilterObjectTest {

    @Test
    void imprimirFilterObject() {
        Map<String,String> mapa = new HashMap<String, String>();
        mapa.put("nome" , "jose");
        mapa.put("curso","informatica");
        mapa.put("dia","2019-12-31");
        mapa.put("horaFim","16:00");
        mapa.put("horaInicio","14:00");

        FilterObject filterObject = new FilterObject(mapa);

        filterObject.imprimir();
    }
}