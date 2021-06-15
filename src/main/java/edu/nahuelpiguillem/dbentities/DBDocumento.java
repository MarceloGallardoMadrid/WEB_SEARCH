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
import java.util.Map;
import java.util.Map.Entry;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author pigui
 */
public class DBDocumento {
    BDHelper help;
    BDHelperJPA helpjpa;
    public DBDocumento(){
        //help = new BDHelper();
        helpjpa=new BDHelperJPA();
    }
    public void addDoc(Documento d)throws SQLException{
        String cmd="INSERT INTO documento (nombre,words,iddoc) values('"+d.getNombre()+"',"+d.getWords()+","+d.getIddoc()+")";
        help.modificarRegistro(cmd);
    }
    public Documento leerDocumentoJPA(int id){
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
    public String addDocBatch(Documento d){
        return "INSERT INTO documento (nombre,words,iddoc) values('"+d.getNombre()+"',"+d.getWords()+","+d.getIddoc()+")";
    }
    public void addWord(Documento d)throws SQLException{
        String cmd="UPDATE documento SET words=words+1 WHERE iddoc="+d.getIddoc();
        help.modificarRegistro(cmd);
    }
    public void deleteDoc(Documento d)throws SQLException{
        String cmd="DELETE FROM documento WHERE iddoc="+d.getIddoc();
        help.modificarRegistro(cmd);
    }
    public void deleteDoc(Integer i)throws SQLException{
        String cmd="DELETE FROM documento WHERE id="+i;
        help.modificarRegistro(cmd);
    }
    public String cmddeleteDoc(Integer i){
        return "DELETE FROM documento WHERE iddoc="+i;
    } 
    public LinkedList<Integer> leerIdDocumento()throws SQLException{
        String cmd ="select iddoc from documento";
        LinkedList<Integer> list = new LinkedList<>();
        ResultSet rs = help.leerDatos(cmd);
        
        while(rs.next()){
            Integer n=rs.getInt("iddoc");
            list.addLast(n);
            
        }
        help.cerrarConexion(rs);
        return list;
        
    }
    public int leerUltimoIdInsertado(){
        return help.leerUltimoIdTabla("documento", "iddoc");
    }
    public long leerUltimoIdInsertadoJPA(){
        EntityManager em =helpjpa.connect();
        long i= (long)em.createQuery("select count(d) from Documento d").getResultList().get(0);
        System.out.println(i);
        helpjpa.disconnect();
        return i;
    }
    public int lastId(){
       int id=-1;
        
        try{
            ResultSet rs = help.leerLastId("id","documento");
            id=matId(rs);
            help.cerrarConexion(rs);
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        return id;
    }
    public int lastIdAlter(){
        return help.leerLastIdAlter();
    }
    private int matId(ResultSet rs) throws SQLException{
        int id=-1;
        while(rs.next()){
            id=rs.getInt("id");       
        }
        return id;
    }

    public List<String> leerNombresJPA(List list) {
        List<String> nombres = new LinkedList<>();
        for(Object o:list){
            Map.Entry<Integer,Float> e=(Map.Entry<Integer,Float>)o;
            Documento d = leerDocumentoJPA(e.getKey());
            nombres.add(d.nombre+" = "+e.getValue());       
        }
        return nombres;
    }
}
