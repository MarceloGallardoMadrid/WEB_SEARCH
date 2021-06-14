package edu.nahuelpiguillem.motor;

import java.io.*;
import java.util.Arrays;
import edu.nahuelpiguillem.buscador.Buscador;
import edu.nahuelpiguillem.buscador.Documentos;
import edu.nahuelpiguillem.dbentities.DBTerminoXDocumento;
import edu.nahuelpiguillem.dbentities.GestorVocabulario;
import edu.nahuelpiguillem.dbentities.terminoxdocumento;
import java.util.List;
import edu.nahuelpiguillem.filemanipulation.*;
import static java.util.Collections.list;
import java.util.Iterator;
public class Test {
    public static void main(String[] args) {
        System.out.println("Soy la clase test");
        //Test de un vocabulario
//        Lector vocabulario = new Lector("doc");
//        vocabulario.agregarPalabra("hola");
//        vocabulario.agregarPalabra("chau");
//        vocabulario.agregarPalabra("hola");
//
//        System.out.println(vocabulario.toString());
//
//        vocabulario.armarVocabulario("0ddc809a.txt");
////        vocabulario.armarVocabulario("0ddcl10.txt");
////        vocabulario.armarVocabulario("00ws110.txt");
//        System.out.println(vocabulario.toString());
//        System.out.println("cantidad de palabras: " + vocabulario.vocabulario.size());

        //Controlador
//    Controlador controlador = new Controlador();
//        System.out.println(Arrays.toString(controlador.getDocumentosEnCarpeta("DocumentosTP1")));

        addDoc();
        //buscarPalabras(new String[] {"armas"});
        //guardarBD();
    }


    private static void buscarPalabras(String[] lista){
        Buscador b = new  Buscador();
        List<Documentos> list;
        list = b.buscar(lista, 10);
        for(Object o : list){
            System.out.println(o);
        }
    }
    private static void buscarPalabras(){
        Buscador b = new  Buscador();
        List<Documentos> list;
        list = b.buscar(new String[] {"a","no","oscura"}, 10);
        for(Object o : list){
            System.out.println(o);
        }
            
    }
    private static void addDoc(){
        LectorDocumentos ld = new LectorDocumentos();
        ld.guardarDocumentoAdd();
        System.out.println("Termine");
        Buscador b = new  Buscador();
        List<Documentos> list;
        list = b.buscar(new String[] {"geheiligten"}, 10);
        for(Object o : list){
            System.out.println(o);
        }
    }
    private static void guardarBD(){
        LectorDocumentos ld = new LectorDocumentos();
        ld.guardarDocumentosJPABatch();
	System.out.println("Termine jeje");
    }
    private static void limpiarBD(){
        GestorVocabulario  gv = new GestorVocabulario();
        gv.limpiarBdBatch();
        System.out.println("Termine jeje");
    }
}
