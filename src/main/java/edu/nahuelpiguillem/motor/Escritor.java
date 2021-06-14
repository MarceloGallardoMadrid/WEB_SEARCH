package edu.nahuelpiguillem.motor;

import java.io.*;
import java.util.Hashtable;

public class Escritor {
    public void guardarIndicePorDocumento(Vocabulario vocabulario, String documento) {
        try {
            System.out.println("Serializando documentos:");
            FileOutputStream fileOut = new FileOutputStream(documento);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(vocabulario);
            out.close();
            fileOut.close();
            System.out.println("Informacion guardada en: " + documento);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public Vocabulario leerIndicePorDocumento(String[] palabras, String documento){
        Vocabulario vocabulario;
        try {
            FileInputStream fileIn = new FileInputStream(documento);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            vocabulario = (Vocabulario) in.readObject();
            in.close();
            fileIn.close();
            return vocabulario;
        } catch (IOException i) {
            i.printStackTrace();
            return null;
        } catch (ClassNotFoundException c) {
            System.out.println("No se encontro la clase vocabulario");
            c.printStackTrace();
            return null;
        }
    }
}
