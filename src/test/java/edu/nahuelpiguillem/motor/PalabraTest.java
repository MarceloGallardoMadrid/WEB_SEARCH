package edu.nahuelpiguillem.motor;
import org.junit.Before;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


public class PalabraTest {
    private Palabra instance;

    @Before
    public void setUp(){
        System.out.println("* UtilsJUnit4Test: @Before method");
        instance = new Palabra();
    }

    @org.junit.Test
    public void TestPalabra() {
        instance.agregarDocumento(1, 1);
        instance.agregarDocumento(2, 1);
        instance.agregarDocumento(3, 2);

        //assertArrayEquals(posteo.toArray(), instance.posteo.toArray());
        assertEquals(3, instance.nr);
        assertEquals(2, instance.maxTf);

        instance.agregarDocumento(0, 7);
        instance.agregarDocumento(5, 6);

        System.out.println(instance.toString());
        System.out.println(Arrays.toString(instance.posteo.toArray()));

    }
}
