package edu.nahuelpiguillem.motor;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Iterator;

public class Vocabulario implements Serializable {
    public Hashtable<String, Palabra> vocabulario = new Hashtable<>();

    public void agregarDocumentoAVocabulario(Integer id_documento, Hashtable<String, Integer> vocabularioDocumento) {
        //Agrega un documento al vocabulario por su id
        Iterator<String> iterator = vocabularioDocumento.keySet().iterator();
        String NombrePalabra;
        while (iterator.hasNext()) {
            NombrePalabra = iterator.next();
            //Si el vocabulario contiene la palabra a agregar
            if (vocabulario.containsKey(NombrePalabra)) {
                //Agregar la palabra buscando su tf en vocabularioDocumento
                vocabulario.get(NombrePalabra).agregarDocumento(id_documento, vocabularioDocumento.get(NombrePalabra));
            }
            else {
                //Crea una nueva palabra, agrega el documento y la agrega al vocabulario
                Palabra palabra = new Palabra();
                palabra.agregarDocumento(id_documento, vocabularioDocumento.get(NombrePalabra));
                vocabulario.put(NombrePalabra, palabra);
            }
        }
    }

    public void guardar() {
        //Guardar el indice inverso ya generado
        System.out.println("Guardando documento en BD");

    }
}
