package edu.nahuelpiguillem.motor;

import java.io.File;
import java.util.Hashtable;
import java.util.Iterator;

public class Controlador {
    String nombreCarpetaDocumentos = "DocumentosTP1";
    public Hashtable<String, Integer> documentosALeer = new Hashtable<>();
    String archivoSerializacion = "DB/vocabulario";

    String[] documentosEnCarpeta;

    Lector lector = new Lector();
    Escritor escritor = new Escritor();

    public Vocabulario vocabulario = new Vocabulario();
    public void iniciar() {
        //Inicia el controlador obteniendo todas sus variables
        //Variables necesarias:
        //  Listado de documentos en bd (ya leidos)
        //  Listado de documentos en la carpeta de documentos OK
        //  Listado de documentos para leer (para iterar)
        //  Listado de documentos para eliminar (para iterar)
        //  Listado de palabras en bd con si id
        //  Listado de palabras leídas generando su id para no consultar la bd

    }

    public String[] getDocumentosEnCarpeta(String nombreCarpeta) {
        File carpeta = new File(nombreCarpeta);
        String[] documentos = carpeta.list();
        if (documentos != null) {
            for (int i = 0; i < documentos.length; i++) {
                documentosALeer.put(documentos[i], i);
            }
        }
        return documentos;
    }

    //Manejo del indice inverso
    public void armarIndiceInverso(int cantDocumentos) {
        //Genera el indice de la forma en que se debera guardar y acceder en la busqueda
        //Este meto se utiliza para serializar archivos para hacer pruebas con el buscador
        documentosEnCarpeta = getDocumentosEnCarpeta(nombreCarpetaDocumentos);
//        for (int i = 0; i < documentosEnCarpeta.length; i++) {
        for (int i = 0; i < cantDocumentos; i++) {
            System.out.println("Documento" + "(" + (i + 1) + "): " + documentosEnCarpeta[i]);
            vocabulario.agregarDocumentoAVocabulario(documentosALeer.get(documentosEnCarpeta[i]),
                    lector.armarVocabulario(nombreCarpetaDocumentos, documentosEnCarpeta[i]));
        }
        //Modificar este metodo por el guardado en bd
        escritor.guardarIndicePorDocumento(vocabulario, archivoSerializacion);
    }

    public void armarIndicePorDocumento() {
        //Genera un indice por documento de la forma en que es leido
        documentosEnCarpeta = getDocumentosEnCarpeta(nombreCarpetaDocumentos);
        System.out.println("Generando indice de documentos...");
        for (int i = 0; i < documentosEnCarpeta.length; i++) {
            System.out.print("Documento" + "(" + (i + 1) + "): ");
            System.out.println(documentosEnCarpeta[i]);
            Hashtable<String, Integer> vocabularioDocumento =
                    lector.armarVocabulario(nombreCarpetaDocumentos, documentosEnCarpeta[i]);
        }
    }

    public void guardarIndicePorDocumento(Hashtable<String, Integer> vocabularioDocumento) {
        //Guarda el indice de cada documento a leer
    }
}
