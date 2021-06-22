package edu.nahuelpiguillem.motor;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import edu.nahuelpiguillem.binaryDB.*;

/*
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
*/

public class Controlador {
    String nombreCarpetaDocumentos = "/mnt/B666916866912A5F/Axel/Utn/5/DLC/TP_DLC_Binaries/DocumentosTP1";
    public Hashtable<String, Integer> documentosALeer = new Hashtable<>();

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
        System.out.println(documentos);
        if (documentos != null) {
            for (int i = 0; i < documentos.length; i++) {
                documentosALeer.put(documentos[i], i);
            }
        }
        return documentos;
    }

    public void guardarIndiceInversoBinario(int batchDocumentos){
        guardarIndiceInversoBinario(batchDocumentos, 0);
    }

    public void guardarIndiceInversoBinario(int batchDocumentos, int cantDocumentos){
        //Obtener lista de archivos
        File carpeta = new File(nombreCarpetaDocumentos);
        String[] docCarpeta = carpeta.list();
        System.out.println(Arrays.toString(docCarpeta));
        if (docCarpeta == null) {
            System.out.println("no se encontraron documentos para leer");
            return;
        }
        ArrayList<String> docLeidos = DocumentosDB.retrieve();
        int inicio = docLeidos.size();
        DocumentosDB.merge(new ArrayList<String>(Arrays.asList(docCarpeta)) , docLeidos);
        if (inicio == docLeidos.size()) {
            System.out.println("No existen nuevos documentos para cargar");
            return;
        }
        int fin;
        if (cantDocumentos == 0){
            fin = docLeidos.size();
        } else {
            fin = Math.min(docLeidos.size(), inicio + cantDocumentos);
        }
        System.out.println("Cantidad de documentos a guardar: " + (fin - inicio));

        System.out.println("Armando indice en memoria");
        //Recorre todos los documentos a partir de los documentos no leidos

        ArrayList<String> docGuardar = new ArrayList<>();

        for (int i = inicio; i <  fin; i++) {
            System.out.println("Documento" + "(" + (i + 1) + "): " + docLeidos.get(i));
            docGuardar.add(docLeidos.get(i));
            vocabulario.agregarDocumentoAVocabulario(i,
                    lector.armarVocabulario(nombreCarpetaDocumentos, docLeidos.get(i)));
            //Con sulta si ya pasaron x documentos siendo x el batch de documentos
            if((i - (inicio - 1)) % batchDocumentos == 0) {
                System.out.println("Guardando datos en batch");
                DocumentosDB.save(docGuardar);
                docGuardar = new ArrayList<>();

                //Guarda el vocabulario en memoria
                vocabulario.guardar();
                //Borra la informacion
                vocabulario = new Vocabulario();
                System.out.println("Armando indice en memoria");
            }
        }
        System.out.println("Guardando datos en batch");
        DocumentosDB.save(docGuardar);
        docGuardar = new ArrayList<>();
        //Guarda el vocabulario del ultimo batch
        vocabulario.guardar();
        //Guarda los ultimos documentos
        DocumentosDB.save(docGuardar);
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
        System.out.println(vocabulario);
        //Modificar este metodo por el guardado en bd
        //escritor.guardarIndicePorDocumento(vocabulario, archivoSerializacion);
        vocabulario.guardar();
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
