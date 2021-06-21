/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nahuelpiguillem.bdaccess;
import edu.nahuelpiguillem.dbentities.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
/**
 *
 * @author pigui
 */
public class bdaccesspoint {
    //Lo que hace esta clase es traerte, todos los documentos, con sus palabras que tengan esta palabra
    //que tengan estas palabras tambien.
    //Traer el documento que tenga maxtf de esa palabra
    BDHelperJPA helpjpa;
    public bdaccesspoint(){
        helpjpa=new BDHelperJPA();
    }


    public List<terminoxdocumento> leerTerminoXDocJPA(String word,int limit){
        System.out.println("leerTerminoXDOCJPA");
        EntityManager em=helpjpa.connect();
        Palabra p=idPalabraJPA(word);
        System.out.println(p);
        //if(p.getIdword()==-1){return new LinkedList<terminoxdocumento>();}
        long id=p.getIdword();
        System.out.println(id);
        Query q;
        q = em.createQuery("select txd from terminoxdocumento txd where txd.idT=:id order by txd.tf desc");
        q.setParameter("id",id);
       
        List<terminoxdocumento> list= q.getResultList();
        System.out.println(list.size());
        for(terminoxdocumento txt : list)
        {
            System.out.println(txt);
        }
        helpjpa.disconnect();
        return list;
    }
    public Palabra idPalabraJPA(String word){
        EntityManager em=helpjpa.connect();  
        System.out.println("Palabra por id");
        Query q = em.createQuery("select p FROM Palabra p where p.nombre like :nombre");
        q.setParameter("nombre",word);
        List l=q.setMaxResults(1).getResultList();
        helpjpa.disconnect();
        if(l.isEmpty()){
            return new Palabra(-1,"nada");
        }
        else{
            return (Palabra)l.get(0);
        }
        
    }
    
}
