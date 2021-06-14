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
    BDHelper help;
    BDHelperJPA helpjpa;
    public DBPalabra(){
        //help= new BDHelper();
        helpjpa=new BDHelperJPA();
    }
    public void addPalabra(Palabra p)throws SQLException{
        String cmd="INSERT INTO termino (nombre,MaxTF,iddoc,cantDoc,idword) values('"+p.getNombre()+"',0,0,0,"+p.getIdword()+")";
        help.modificarRegistro(cmd);
    }
    public void addPalabraJPABatch(Palabra p, BDHelperJPA helpjpa){
        helpjpa.persist(p);
    }
    public void addPalabraJPA(Palabra p){
        EntityManager em = helpjpa.connect();
        EntityTransaction t= em.getTransaction();
        t.begin();
        em.persist(p);
        t.commit();
        helpjpa.disconnect();
    }
    public List<Palabra> leetPalabraJPA(){
        EntityManager em = helpjpa.connect();
        return em.createQuery("select p from Palabra p").getResultList();
    }
    public String addPalabraBatch(Palabra p,int iddoc){
        return "INSERT INTO termino (nombre,MaxTF,iddoc,cantDoc,idword) values('"+p.getNombre()+"',"+p.getMaxtf()+","+iddoc+",0,"+p.getIdword()+")";
    }
    public void addDoc(Palabra p)throws SQLException{
        String cmd="UPDATE termino SET cantDoc=cantDoc+1 WHERE idword="+p.getIdword();
        help.modificarRegistro(cmd);
    }
    public void modificarMaxTf(Palabra p,int tf,int iddoc)throws SQLException{
        String cmd= "UPDATE termino SET MaxTF="+tf+",iddoc="+iddoc+" WHERE idword="+p.getIdword();
        help.modificarRegistro(cmd);
    }
    public String cmdmodificarMaxTf(Palabra p,int tf,int iddoc){
        return "UPDATE termino SET MaxTF="+tf+",iddoc="+iddoc+" WHERE idword="+p.getIdword();
    }
    public String addDocBatch(Palabra p){
        return "UPDATE termino SET cantDoc=cantDoc+1 WHERE idword="+p.getIdword();
    }
    public void eliminarPalabra(Palabra p)throws SQLException{
        String cmd="DELETE FROM termino WHERE idword="+p.getIdword();
        help.modificarRegistro(cmd);
    }
    public void eliminarPalabra(Integer i)throws SQLException{
        String cmd="DELETE FROM termino WHERE idTermino="+i;
        help.modificarRegistro(cmd);
    }
    public String cmdeliminarPalabra(Integer i){
        return "DELETE FROM termino WHERE idword="+i;
    }
    public int lastIdAlter(){
        return help.leerLastIdAlter();
    }
    public LinkedList<Integer> leerIdTermino()throws SQLException{
        String cmd ="select idword from termino";
        LinkedList<Integer> list = new LinkedList<>();
        ResultSet rs = help.leerDatos(cmd);
        
        while(rs.next()){
            Integer n=rs.getInt("idword");
            list.addLast(n);
            
        }
        help.cerrarConexion(rs);
        return list;
        
    }
    public int leerLastId(){
        int id=-1;
        
        try{
            ResultSet rs = help.leerLastId("idTermino","termino");
            id=matId(rs);
            help.cerrarConexion(rs);
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        return id;
    }
    public int leerUltimoIdInsertado(){
        return help.leerUltimoIdTabla("termino","idword");
    }
    public int leerUltimoInsertadoJPA(){
        EntityManager em =helpjpa.connect();
        Palabra p= (Palabra)em.createQuery("select p from Palabra p order by p.idword desc").getResultList().get(0);
        helpjpa.disconnect();
        return p.getIdword();
    }
    private int matId(ResultSet rs) throws SQLException{
        int id=-1;
 //       System.out.println(rs.getAsciiStream(1));
        while(rs.next()){
            id=rs.getInt("id");       
        }
        return id;
    }
    
}
