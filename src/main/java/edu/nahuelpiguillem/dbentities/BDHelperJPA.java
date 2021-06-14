/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nahuelpiguillem.dbentities;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 *
 * @author pigui
 */
public class BDHelperJPA {
    EntityManager em;
    EntityManagerFactory emf;
    EntityTransaction et;
    int batchSize=50;
    int contador;
    public BDHelperJPA(){
        contador=0;
    }
    public EntityManager connect(){
        emf=Persistence.createEntityManagerFactory("BusquedaPU");
        em=emf.createEntityManager();
        return em;
        
    }
    public void disconnect(){
        if(et!=null && et.isActive()){
            et.commit();
        }
        emf.close();
        em.close();
    }
    public void commit(EntityTransaction et){
        et.commit();
    }
    public void persist(Object o){
        em.persist(o);
        contador++;
        if(contador!=0 && contador%batchSize==0){
            
            flush(em.getTransaction());
        }
    }
    public void flush(EntityTransaction et){
        commit(et);
        et.begin();
    }
    public void getTransaction(){
        et=em.getTransaction();
    }
    public void beginTransaction(){
        connect();
        getTransaction();
        et.begin();
    }
    public EntityTransaction getET(){
        return et;
    }
}
