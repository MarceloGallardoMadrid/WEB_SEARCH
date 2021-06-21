/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nahuelpiguillem.dbentities;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author pigui
 */
public class DBTerminoXDocumento {
    BDHelperJPA helpjpa;
    public DBTerminoXDocumento(){
        helpjpa=new BDHelperJPA(); 
    }
    public void addTermXDocJPA(Documento d,Palabra p, int tf){
        EntityManager em=helpjpa.connect();
        terminoxdocumento dxt=new terminoxdocumento(d.getIddoc(),p.getIdword(),tf);
        EntityTransaction t= em.getTransaction();
        t.begin();
        em.persist(dxt);
        
        t.commit();
        helpjpa.disconnect();
    }
    public void addTermXDocJPABatch(Documento d,Palabra p, int tf,BDHelperJPA helpjpa){
        //terminoxdocumento dxt=new terminoxdocumento(d.getIddoc(),p.getIdword(),tf);
        //helpjpa.persist(dxt);
    }
    public List leerTodostxd(){
        EntityManager em = helpjpa.connect();
        List list = em.createQuery("select txd from terminoxdocumento txd ").getResultList();
        helpjpa.disconnect();
       return list;
    }
    public List<terminoxdocumento> leerTerminoXdocumento(){
        EntityManager em=helpjpa.connect();
        Query q = em.createQuery("select txd from terminoxdocumento txd");
        return q.getResultList();
    }
}
