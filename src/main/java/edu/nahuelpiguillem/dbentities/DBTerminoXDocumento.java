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
import javax.persistence.Query;

/**
 *
 * @author pigui
 */
public class DBTerminoXDocumento {
    BDHelper help;
    BDHelperJPA helpjpa;
    public DBTerminoXDocumento(){
        //help= new BDHelper();
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
        terminoxdocumento dxt=new terminoxdocumento(p.getIdword(), d.getIddoc(),tf);
        helpjpa.persist(dxt);
    }
    public List leerTodostxd(){
        EntityManager em = helpjpa.connect();
        List list = em.createQuery("select txd from terminoxdocumento txd ").getResultList();
        helpjpa.disconnect();
       return list;
    }
    public void addTermXDoc(Documento d,Palabra p, int tf)throws SQLException{
        String cmd="INSERT INTO terminoxdocumento (idT,idD,tf) values("+p.getIdword()+","+d.getIddoc()+","+tf+")";
        help.modificarRegistro(cmd);      
    }
    public String addTermXDocBatch(Documento d,Palabra p,int tf){
        return "INSERT INTO terminoxdocumento (idT,idD,tf) values("+p.getIdword()+","+d.getIddoc()+","+tf+")";
    }
    public void addTermXDoc(Documento d,Palabra p)throws SQLException{
        String cmd="INSERT INTO terminoxdocumento (idT,idD,tf) values("+p.getIdword()+","+d.getIddoc()+",0)";
        help.modificarRegistro(cmd);
    }
    public void addFrec(Documento d,Palabra p)throws SQLException{
        String cmd="UPDATE terminoxdocumento SET tf=tf+1 WHERE idT="+p.getIdword()+" and idD="+d.getIddoc();
        help.modificarRegistro(cmd);
    }
    public void eliminarTxD(Documento d,Palabra p)throws SQLException{
        String cmd="DELETE FROM terminoxdocumento WHERE idT="+p.getIdword()+" and idD="+d.getIddoc();
        help.modificarRegistro(cmd);       
    }
    public void eliminarTxD(Integer i)throws SQLException{
        String cmd="DELETE FROM terminoxdocumento WHERE idtxd="+i;
        help.modificarRegistro(cmd);       
    }
    public String cmdeliminarTxD(Integer i){
        return "DELETE FROM terminoxdocumento WHERE idtxd="+i;
    }
    
    public LinkedList<Integer> leerIdTerminoXDocumento()throws SQLException{
        String cmd ="select idtxd from terminoxdocumento";
        LinkedList<Integer> list = new LinkedList<>();
        ResultSet rs = help.leerDatos(cmd);
        
        while(rs.next()){
            Integer n=rs.getInt("idtxd");
            list.addLast(n);
            
        }
        help.cerrarConexion(rs);
        return list;
        
    }
    public List<terminoxdocumento> leerTerminoXdocumento(){
        EntityManager em=helpjpa.connect();
        Query q = em.createQuery("select txd from terminoxdocumento txd");
        return q.getResultList();
    }
}
