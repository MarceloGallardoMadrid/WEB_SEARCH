/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nahuelpiguillem.dbentities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author pigui
 */
public class DBPalabra {
    BDHelperJPA helpjpa;
    public DBPalabra(){
        //help= new BDHelper();
        helpjpa=new BDHelperJPA();
    }
    public void addPalabraJPABatch(Palabra p, BDHelperJPA helpjpa){
        helpjpa.persist(p);
    }
    public List<Palabra> leetPalabraJPA(){
        EntityManager em = helpjpa.connect();
        return em.createQuery("select p from Palabra p").getResultList();
    }
    public long leerUltimoInsertadoJPA(){
        EntityManager em =helpjpa.connect();
        long i= (long)em.createQuery("select count(p) from Palabra p ").getResultList().get(0);
        helpjpa.disconnect();
        return i;
    }
    
}
