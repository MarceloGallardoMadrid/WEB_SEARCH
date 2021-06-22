package edu.nahuelpiguillem.binaryDB;

import edu.nahuelpiguillem.motor.*;
//import motor.DocumentoPosteo;
//import motor.Palabra;

import java.io.*;
import java.util.Iterator;

public class PalabraDB {
    private static String path = "/home/axel/binaryDB/palabras/";

    static public void store(String nombre, Palabra p){
        //Guarda los datos de una palabra
        FileOutputStream fos = null;
        DataOutputStream salida = null;
        try {
            fos = new FileOutputStream(path + nombre);
            salida = new DataOutputStream(fos);
            //nr
            salida.writeInt(p.nr);
            //max.tf
            salida.writeInt(p.maxTf);

            Iterator<DocumentoPosteo> it = p.posteo.iterator();
            while (it.hasNext()){
                //Guarda cada documento de la lista de posteo
                DocumentoPosteo d = it.next();
                salida.writeInt(d.getId());
                salida.writeInt(d.getTf());
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

    static public Palabra retrieve(String nombre){
        FileInputStream fis = null;
        DataInputStream entrada = null;
        Palabra p = null;
        try {
            fis = new FileInputStream(path + nombre);
            entrada = new DataInputStream(fis);
            p = new Palabra(entrada.readInt(), entrada.readInt());

            while (true){
                //Guarda cada documento de la lista de posteo
                p.posteo.add(new DocumentoPosteo(entrada.readInt(), entrada.readInt()));
            }

        } catch (EOFException e) {
            //System.out.println("Fin de fichero");
        } catch (IOException e) {
            //System.out.println(e.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (entrada != null) {
                    entrada.close();
                }
            } catch (IOException e) {
                //System.out.println(e.getMessage());
            }
        }
        return p;
    }
    /*
    Junta la palabra en memoria con la palabra guardada para
    crear la version actualizada de la palabra
    */
    static public Palabra merge(Palabra p, Palabra pb){
        //Suma la cantidad de documentos de las palabras
        pb.nr += p.nr;
        //Guarda el tf mas alto
        pb.maxTf = Math.max(p.maxTf, pb.maxTf);
        //Agrega todos los elementos a la lista de posteo ordenados
        pb.posteo.addAll(p.posteo);
        return pb;
    }

    static public Palabra save(String nombre, Palabra p){
        File bin = new File(path + nombre);

        try {
            //Si da true el archivo no existia y se acaba de crear
            if(bin.createNewFile()){
                store(nombre, p);
                return p;
            } else {
                //Si el archivo existe, obtiene la palabra
                Palabra pb = retrieve(nombre);
                //La junta con su version en memoria
                merge(p, pb);
                //Almacena la version final
                store(nombre, pb);
                return pb;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    static public boolean remove(String nombre) {
        File bin = new File(path + nombre);
        return bin.delete();
    }

    static public boolean clean() {
        File bin = new File(path);
        String[] documentos = bin.list();
        for (String s: documentos) {
            File currentFile = new File(bin.getPath(),s);
            currentFile.delete();
        }
        return true;
    }


}
