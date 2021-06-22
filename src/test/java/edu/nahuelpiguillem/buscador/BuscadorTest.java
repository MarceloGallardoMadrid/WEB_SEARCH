package edu.nahuelpiguillem.buscador;

import edu.nahuelpiguillem.motor.Escritor;
import edu.nahuelpiguillem.motor.Vocabulario;
import org.junit.Before;

import static org.junit.Assert.*;

public class BuscadorTest {
    private Buscador instance;

    @Before
    public void setUp() {
        System.out.println("* UtilsJUnit4Test: @Before method");
        instance = new Buscador();
    }
    @org.junit.Test
    public void recuperarTest(){
        Vocabulario v = Vocabulario.recuperar(new String[]{"cat", "dog", "asdfghjk"});
        assertTrue(v.vocabulario.containsKey("cat"));
        assertTrue(v.vocabulario.containsKey("dog"));
        assertFalse(v.vocabulario.containsKey("asdfghjk"));
        System.out.println(v.vocabulario);
    }



    @org.junit.Test
    public void InitialTest(){
        String archivoSerializacion = "DB/vocabulario";
        Escritor escritor = new Escritor();
        Vocabulario vocabulario = escritor.leerIndicePorDocumento(null, archivoSerializacion);
        System.out.println(vocabulario.vocabulario.size());
        assertTrue(vocabulario.vocabulario.size() > 10000);
    }



    @org.junit.Test
    public void buscarTest(){
        System.out.println(instance.buscar(new String[]{"a", "the", "at", "adseawd"}, 5));
        /*System.out.println(instance.buscar(new String[]{"a", "the", "at", "adseawd"}, 5));
        System.out.println(instance.buscar(new String[]{"a", "the", "at", "adseawd"}, 7));
        System.out.println(instance.buscar(new String[]{"a", "the", "at", "adseawd"}, 300));*/
    }

    @org.junit.Test
    public void buscarbTest(){
        instance = new Buscador(true);
        //System.out.println(instance.buscarb(new String[]{"myster"}, 5));
        System.out.println(instance.buscarb(new String[]{"a", "the", "at", "adseawd"}, 5));
//        System.out.println(instance.buscarb(new String[]{"a", "the", "at", "adseawd"}, 7));
//        System.out.println(instance.buscarb(new String[]{"a", "the", "at", "adseawd"}, 300));

        //instance.buscar(new String[]{"a", "the", "at", "adseawd"}, 5);
        //instance.buscar(new String[]{"a", "the", "at", "adseawd"}, 7);
        //Valor de R mayor al de la lista
        //instance.buscar(new String[]{"a", "the", "at", "adseawd"}, 300);
    }
}
