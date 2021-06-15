/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nahuelpiguillem.motor;
import edu.nahuelpiguillem.bdaccess.bdaccesspoint;
import edu.nahuelpiguillem.dbentities.terminoxdocumento;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author pigui
 */
public class PalabraFactory {
    bdaccesspoint access;
    public PalabraFactory(){
        access= new bdaccesspoint();
    }
    public Palabra fabricarPalabraJPA(String word,int limit){
        Palabra p;
        List<terminoxdocumento> list=access.leerTerminoXDocJPA(word,limit);
        p=materializarPalabraJPA(list);
        return p;
    }
    
    public Palabra fabricarPalabra(String word,int limit){
        Palabra p= new Palabra();
        ResultSet rs=access.leerTerminoXDoc(word,limit);
        if(rs==null){return p;}
        try{
            p=materializarPalabra(rs);
            access.cerrarConexion(rs);
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        return p;
        
    }
   public Palabra fabricarPalabra(String word){
        Palabra p= new Palabra();
        
        
        try{
            ResultSet rs=access.leerTerminoXDoc(word);
            if(rs==null){return p;}
            
            p=materializarPalabra(rs);
            access.cerrarConexion(rs);
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

        return p;
        
    }
   private Palabra materializarPalabraJPA(List<terminoxdocumento> list){
       Palabra p= new Palabra();
       for(terminoxdocumento txd : list){
           int tf=txd.getTf();
           int idD=(int) txd.getIdD();
           p.agregarDocumento(idD,tf);
       }
       return p;
   }
    private Palabra materializarPalabra(ResultSet rs)throws SQLException{
        Palabra p= new Palabra();
        while(rs.next()){
            int tf=rs.getInt("tf");
            int iddoc=rs.getInt("idD");
            p.agregarDocumento(iddoc, tf);
        }
        return p;
    }
}
