package edu.nahuelpiguillem.buscador;
import edu.nahuelpiguillem.motor.*;

import java.util.List;

public class Buscador {
    String archivoSerializacion = "DB/vocabulario";
    Escritor escritor = new Escritor();
	AccessPoint access= new AccessPoint();
    public List buscar(String[] palabras, int R) {
        Documentos documentos = new Documentos();
        //Recupera de base de dato un objeto vocabulario con las palabras encontradas en la bd
        //Vocabulario vocabulario = escritor.leerIndicePorDocumento(palabras, archivoSerializacion);
        //Vocabulario vocabulario=access.crearVocabulario(palabras);
	Vocabulario vocabulario=access.crearVocabularioJPA(palabras,R);
        for (String s : palabras) {
            if (vocabulario.vocabulario.containsKey(s)) {
                //Si existe la palabra la obtiene
                Palabra palabra = vocabulario.vocabulario.get(s);
                documentos.agregarPalabra(palabra, R);
                System.out.println(palabra);
            }
        }

        System.out.println(documentos.documentosOrdenados(R));
        return documentos.documentosOrdenados(R);
    }
}
