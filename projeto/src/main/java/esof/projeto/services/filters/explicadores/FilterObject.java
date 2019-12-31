package esof.projeto.services.filters.explicadores;


import lombok.Data;
import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

@Data
public class FilterObject {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    private String cursoName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dia;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime horaFim;

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime horaInicio;

    private String nome;



    public FilterObject(String cursoName, LocalDate dia , LocalTime horaFim , LocalTime horaInicio , String name){
        this.cursoName=cursoName;
        this.dia=dia;
        this.horaFim=horaFim;
        this.horaInicio=horaInicio;
        this.nome=name;
    }

    public FilterObject(){
    }

    /**
     * Procura nos parametros de pesquisa do explicador
     * e converte-o num objeto
     * @param searchParams parametros de pesquisa
     */
    public FilterObject(Map<String,String> searchParams) {
        this();
        this.nome = searchParams.get("nome");
        this.cursoName = searchParams.get("curso");
        String dia = searchParams.get("dia");
        String horaFim = searchParams.get("horaFim");
        String horaInicio = searchParams.get("horaInicio");
        LocalDate day = null;
        LocalTime fim = null;
        LocalTime inicio = null;
        try{
            if(dia != null) day = LocalDate.parse(dia);
            if(horaFim != null) fim = LocalTime.parse(horaFim);
            if(horaInicio != null) inicio = LocalTime.parse(horaInicio);
        }catch (Exception e) {
            this.logger.error(e.getMessage());
        }
        this.dia = day;
        this.horaFim = fim;
        this.horaInicio =inicio;
    }

    public void imprimir() {
        System.out.println(this.cursoName + " " + this.nome + " " + this.horaFim + " " + this.dia + " " + this.horaInicio + "\n");
    }





}
