/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nahuelpiguillem.dbentities;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author pigui
 */
public class DBDocumento {
    BDHelperJPA helpjpa;
    public DBDocumento(){
        helpjpa=new BDHelperJPA();
    }
    public Documento leerDocumentoJPA(long id){
        EntityManager em=helpjpa.connect();
        Documento d =em.find(Documento.class,id);
        helpjpa.disconnect();
        return d;
    }
    public void addDocJPA(Documento d){
        EntityManager em =helpjpa.connect();
        EntityTransaction t= em.getTransaction();
        t.begin();
        em.persist(d);
        t.commit();
        helpjpa.disconnect();
    }
    public void addDocJPABatch(Documento d,BDHelperJPA helpjpa){
        helpjpa.persist(d);
    } 
    public long leerUltimoIdInsertadoJPA(){
        EntityManager em =helpjpa.connect();
        long i= (long)em.createQuery("select count(d) from Documento d").getResultList().get(0);
        System.out.println(i);
        helpjpa.disconnect();
        return i;
    }

    public List<String> leerNombresJPA(List list) {
        List<String> nombres = new LinkedList<>();
        for(Object o:list){
            Map.Entry<Integer,Float> e=(Map.Entry<Integer,Float>)o;
            Documento d =  leerDocumentoJPA((long) e.getKey());
            if (d!=null) {
                nombres.add(d.nombre+" = "+e.getValue() + ": "  + e.getKey());       
            } else {
                nombres.add("error: " + e.getKey());       
}
        }
        return nombres;
    }
}
