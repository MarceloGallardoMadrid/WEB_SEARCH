package edu.nahuelpiguillem.motor;

import org.junit.Before;

import static org.junit.Assert.*;

public class LectorTest {
    private Lector instance;

    @Before
    public void setUp(){
         System.out.println("* UtilsJUnit4Test: @Before method");
         instance = new Lector();
    }

    @org.junit.Test
    public void TestLectorArmarVocabulario() {
        //Crea una tabla para todos los documentos
        Controlador controlador = new Controlador();
        String[] documentos = controlador.getDocumentosEnCarpeta("/home/axel/binaryDB/DocumentosTP1");
        for (String documento : documentos) {
            instance.armarVocabulario("/home/axel/binaryDB/DocumentosTP1", documento);
        }
        //assertEquals(13473, instance.vocabulario.size());
    }
}
