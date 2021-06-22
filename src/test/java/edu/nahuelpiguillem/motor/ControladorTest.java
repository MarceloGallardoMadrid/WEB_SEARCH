package edu.nahuelpiguillem.motor;

import edu.nahuelpiguillem.binaryDB.DocumentosDB;
import edu.nahuelpiguillem.binaryDB.PalabraDB;
import edu.nahuelpiguillem.filemanipulation.LectorDocumentos;
import org.junit.Before;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ControladorTest {
    private Controlador instance;

    @Before
    public void setUp() {
        System.out.println("* UtilsJUnit4Test: @Before method");
        instance = new Controlador();
    }
    @org.junit.Test
    public void TestGetDocumentosEnCarpeta() {
        String[] documentos = instance.getDocumentosEnCarpeta("/home/axel/binaryDB/DocumentosTP1");
        assertEquals(593, documentos.length);
        assertEquals(593, instance.documentosALeer.size());
        //Evalua que el primer elemento de los documentos exista en los documentos a leer con el id 0
        assertEquals(Integer.valueOf(0), instance.documentosALeer.get(documentos[0]));
        assertEquals(Integer.valueOf(20), instance.documentosALeer.get(documentos[20]));

        //System.out.println(Arrays.toString(documentos));
    }

    @org.junit.Test
    public void TestArmarIndiceInverso() {
        PalabraDB.clean();
        System.out.println("Limpio");
        instance.armarIndiceInverso(500);
        System.out.println("Finalizado");
        System.out.println(instance.vocabulario.vocabulario.size());
        assert instance.vocabulario.vocabulario.size() > 3000;
    }

    @org.junit.Test
    public void TestClean() {
        PalabraDB.clean();
        System.out.println("Limpio");
    }

    @org.junit.Test
    public void TestArmarIndicePorDocumento() {
        instance.armarIndicePorDocumento();
        System.out.println("Finalizado");
    }

    //Guardar binarios
    @org.junit.Test
    public void TestGuardarIndiceInversoBinario() {
        PalabraDB.clean();
        DocumentosDB.remove();
        instance.guardarIndiceInversoBinario(600);
    }

    //Guardar base de datos
    @org.junit.Test
    public void TestGuardarIndiceInversoDB(){
        LectorDocumentos ld = new LectorDocumentos();
        ld.guardarDocumentoAdd();
    }
}
