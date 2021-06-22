package edu.nahuelpiguillem.buscador;
import edu.nahuelpiguillem.motor.*;
import edu.nahuelpiguillem.binaryDB.DocumentosDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
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
*/

public class Buscador {
    String archivoSerializacion = "DB/vocabulario";
    Escritor escritor = new Escritor();
    boolean bin = false;

    public Buscador() {
    }

    public Buscador(boolean bin) {
        this.bin = bin;
    }

    public List buscar(String[] palabras, int R) {
        Documentos documentos = new Documentos();
        //Recupera de base de dato un objeto vocabulario con las palabras encontradas en la bd
        //Vocabulario vocabulario = escritor.leerIndicePorDocumento(palabras, archivoSerializacion);
        //Vocabulario vocabulario=access.crearVocabulario(palabras);
        //Vocabulario vocabulario = Vocabulario.recuperar(palabras);
        Vocabulario vocabulario;
        if (bin) {
            vocabulario = Vocabulario.recuperar(palabras);
        } else {
            AccessPoint access= new AccessPoint();
            vocabulario = access.crearVocabularioJPA(palabras,R);
        }
        for (String s : palabras) {
            if (vocabulario.vocabulario.containsKey(s)) {
                //Si existe la palabra la obtiene
                Palabra palabra = vocabulario.vocabulario.get(s);
                documentos.agregarPalabra(palabra, R);
                //System.out.println(palabra);
            }
        }
        //System.out.println(documentos.documentosOrdenados(R));
        return documentos.documentosOrdenados(R);
    }

    public ArrayList<DocumentoRespuesta> buscarb(String[] palabras, int R){
        List<Map.Entry<Integer,Float>> l = buscar(palabras, R);
        ArrayList<DocumentoRespuesta> ld = new ArrayList<>();
        ArrayList<String> nombreDoc = DocumentosDB.retrieve();
        for (Map.Entry<Integer,Float> entry: l) {
            //Agrega cada elemento como un objeto de respuesta
            ld.add(new DocumentoRespuesta(entry.getKey(), nombreDoc.get(entry.getKey()), entry.getValue()));
        }
        return ld;
    }
}
