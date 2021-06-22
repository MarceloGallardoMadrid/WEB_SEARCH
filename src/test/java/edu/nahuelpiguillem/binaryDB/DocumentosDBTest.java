package edu.nahuelpiguillem.binaryDB;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class DocumentosDBTest {
    @org.junit.Test
    public void TestBasicFunctions(){
        DocumentosDB.remove();
        ArrayList<String> documentos = new ArrayList<>();
        documentos.add("a");
        documentos.add("b");
        documentos.add("c");

        DocumentosDB.store(documentos);
        System.out.println(documentos);

        assertEquals(documentos, DocumentosDB.retrieve());
    }

    @org.junit.Test
    public void TestMerge() {
        ArrayList<String> documentos = new ArrayList<>();
        documentos.add("a");
        documentos.add("b");
        documentos.add("c");

        ArrayList<String> documentos2 = new ArrayList<>();
        documentos2.add("a");
        documentos2.add("d");
        documentos2.add("e");

        ArrayList<String> documentos3 = new ArrayList<>();
        documentos3.add("a");
        documentos3.add("b");
        documentos3.add("c");
        documentos3.add("d");
        documentos3.add("e");

        DocumentosDB.merge(documentos2, documentos);
        assertEquals(documentos3, documentos);
    }

    @org.junit.Test
    public void TestSave(){
        DocumentosDB.remove();

        ArrayList<String> documentos = new ArrayList<>();
        documentos.add("a");
        documentos.add("b");
        documentos.add("c");

        DocumentosDB.save(documentos);
        assertEquals(documentos, DocumentosDB.retrieve());

        ArrayList<String> documentos2 = new ArrayList<>();
        documentos2.add("a");
        documentos2.add("d");
        documentos2.add("e");

        DocumentosDB.save(documentos2);

        ArrayList<String> documentos3 = new ArrayList<>();
        documentos3.add("a");
        documentos3.add("b");
        documentos3.add("c");
        documentos3.add("d");
        documentos3.add("e");

        assertEquals(documentos3, DocumentosDB.retrieve());
    }
}
