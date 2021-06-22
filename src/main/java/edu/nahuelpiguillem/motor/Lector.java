package edu.nahuelpiguillem.motor;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Hashtable;
import java.util.Scanner;

/*
public class Lector {
    public Hashtable<String,Integer> armarVocabulario(String carpeta, String documento){
        //Abrir el documento seleccionado
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(carpeta +  "/" + documento)));
            scanner.useDelimiter("[\\W\\s]");
        }
        catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
            return null;
        }
        //Iterar el archivo obteniendo palabras
        String palabra;
        Hashtable<String, Integer> vocabulario = new Hashtable<>();
//        while (scanner.hasNext()) {
//            palabra = scanner.next();
//            if (!palabra.isBlank()) {
//                if (vocabulario.containsKey(palabra)){
//                    vocabulario.put(palabra, vocabulario.get(palabra) + 1);
//                }
//                else{
//                    // Sino crear la entrada con tf 1
//                    vocabulario.put(palabra, 1);
//                }
//            }
//        }
        return vocabulario;
    }
}
*/
public class Lector {
    public Hashtable<String,Integer> armarVocabulario(String carpeta, String documento){
        //Abrir el documento seleccionado
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(carpeta +  "/" + documento)));
            scanner.useDelimiter("[\\W\\s]");
        }
        catch (FileNotFoundException e) {
            System.out.println("Archivo no encontrado");
            return null;
        }
        //Iterar el archivo obteniendo palabras
        String palabra;
        Hashtable<String, Integer> vocabulario = new Hashtable<>();
        while (scanner.hasNext()) {
            palabra = scanner.next().toLowerCase();
            if (!palabra.isBlank()) {
                if (vocabulario.containsKey(palabra)){
                    vocabulario.put(palabra, vocabulario.get(palabra) + 1);
                }
                else{
                    // Sino crear la entrada con tf 1
                    vocabulario.put(palabra, 1);
                }
            }
        }
        return vocabulario;
    }
}
