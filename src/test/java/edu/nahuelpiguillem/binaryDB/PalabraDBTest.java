package edu.nahuelpiguillem.binaryDB;

import edu.nahuelpiguillem.motor.Palabra;
import org.junit.Before;

import static org.junit.Assert.*;

public class PalabraDBTest {
    @Before
    public void setUp() {
        System.out.println("* UtilsJUnit4Test: @Before method");
    }

    @org.junit.Test
    public void TestBasicFunctions(){
        Palabra p = new Palabra();
        p.agregarDocumento(1, 1);
        p.agregarDocumento(2, 5);
        p.agregarDocumento(3, 2);
        PalabraDB.store("../test/store", p);
        Palabra pb = PalabraDB.retrieve("../test/store");

        assertEquals(3, pb.nr);
        assertEquals(5, pb.maxTf);
        assertArrayEquals(p.posteo.toArray(), pb.posteo.toArray());

        p = new Palabra();
        p.agregarDocumento(7, 9);

        PalabraDB.merge(p, pb);
        assertEquals(4, pb.nr);
        assertEquals(9, pb.maxTf);
        System.out.println(pb.posteo);
    }

    @org.junit.Test
    public void TestSave(){
        PalabraDB.remove("../test/palabra");

        Palabra p = new Palabra();
        p.agregarDocumento(1, 1);
        p.agregarDocumento(2, 5);
        p.agregarDocumento(3, 2);

        //Guardar cuando el archivo no existe
        PalabraDB.save("../test/palabra", p);
        Palabra pb = PalabraDB.retrieve("../test/palabra");
        assertEquals(3, pb.nr);
        assertEquals(5, pb.maxTf);

        p = new Palabra();
        p.agregarDocumento(7, 9);

        PalabraDB.save("../test/palabra", p);
        pb = PalabraDB.retrieve("../test/palabra");
        assertEquals(4, pb.nr);
        assertEquals(9, pb.maxTf);
    }

}
