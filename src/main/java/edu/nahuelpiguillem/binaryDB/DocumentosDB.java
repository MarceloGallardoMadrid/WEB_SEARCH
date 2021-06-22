package edu.nahuelpiguillem.binaryDB;

import java.io.*;
import java.util.ArrayList;

public class DocumentosDB {
    private static String path = "/home/axel/binaryDB/documentos";

    static public void store(ArrayList<String> documentos){
        //Guarda los datos de una palabra
        FileOutputStream fos = null;
        DataOutputStream salida = null;
        try {
            fos = new FileOutputStream(path);
            salida = new DataOutputStream(fos);

            //Itera la lista de todos los documentos
            for (String doc: documentos) {
                //Escribe el nombre del documento junto con un delimitador para identificarlo
                salida.writeChars(doc + "/");
            }

        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (salida != null) {
                    salida.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static public ArrayList<String> retrieve(){
        FileInputStream fis = null;
        DataInputStream entrada = null;
        ArrayList<String> documentos = new ArrayList<>();
        try {
            fis = new FileInputStream(path);
            entrada = new DataInputStream(fis);

            //Lee letra por letra el flujo de entrada recuperando los nombres de los documentos
            StringBuilder nombre = new StringBuilder();
            while (true){
                char letra = entrada.readChar();
                if (letra == '/') {
                    documentos.add(nombre.toString());
                    nombre = new StringBuilder();
                } else {
                    nombre.append(letra);
                }
            }

        } catch (EOFException e) {
            //System.out.println("Fin de fichero");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (entrada != null) {
                    entrada.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        //Devuelve los documentos como array
        return documentos;
    }


    static public ArrayList<String> merge(ArrayList<String> d, ArrayList<String> db){
        for (String doc: d) {
            if (!db.contains(doc)) {
                db.add(doc);
            }
        }
        return db;
    }


    static public ArrayList<String> save(ArrayList<String> documentos){
        File bin = new File(path);

        try {
            //Si da true el archivo no existia y se acaba de crear
            if(bin.createNewFile()){
                store(documentos);
                return documentos;
            } else {
                //Si el archivo existe, obtiene la palabra
                ArrayList<String> db = retrieve();
                //La junta con su version en memoria
                merge(documentos, db);
                //Almacena la version final
                store(db);
                return db;
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    static public boolean remove() {
        File bin = new File(path);
        return bin.delete();
    }

}
