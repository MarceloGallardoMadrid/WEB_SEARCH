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
    BDHelper help;
    BDHelperJPA helpjpa;
    public bdaccesspoint(){
        help = new BDHelper();
        helpjpa=new BDHelperJPA();
    }
    //Quiero devolver un conjunto de terminosxdoc segun las palabras que busque, que me digan el doc y el tf
    public ResultSet leerTerminosXDoc(String words[]){
        ResultSet rs=null;
        try{
            ArrayList<Integer> listaId=listaIdPalabras(words);
            if(listaId.isEmpty()){return rs;}
            String sql=cmdBuscarTerminoXDocumento(listaId);
            rs=help.leerDatos(sql);
        }
        catch(SQLException sqlex){
            System.out.println(sqlex.getMessage());
        }
        return rs;

    }
    public void cerrarConexion(ResultSet rs)throws SQLException{
        help.cerrarConexion(rs);
    }
    public int idPalabra(String word){
        int id=-1;
        try{
            String sql = cmdBuscarPalabra(word);
            ResultSet rs = help.leerDatos(sql);
            while(rs.next()){
                id=rs.getInt("idword");
            }
            help.cerrarConexion(rs);
        }
        catch(SQLException sqex){
            System.out.println(sqex.getMessage());
        }
        return id;
    }
    public ResultSet leerTerminoXDoc(String word){
        ResultSet rs=null;
        int id=idPalabra(word);
        if(id==-1){return null;}
        String sql=cmdBuscarTerminoXDocumento(id);
        rs=help.leerDatos(sql);
        return rs;
    }
    public ResultSet leerTerminoXDoc(String word,int limit){
        ResultSet rs=null;
        int id=idPalabra(word);
        if(id==-1){return null;}
        String sql=cmdBuscarTerminoXDocumento(id,limit);
        rs=help.leerDatos(sql);
        return rs;
    }
    public List<terminoxdocumento> leerTerminoXDocJPA(String word,int limit){
        EntityManager em=helpjpa.connect();
        Palabra p=idPalabraJPA(word);
        if(p.getIdword()==-1){return new LinkedList<terminoxdocumento>();}
        long id=p.getIdword();
        Query q;
        q = em.createQuery("select txd from terminoxdocumento txd where txd.idT=:id order by txd.tf desc");
        q.setParameter("id",id);
       
        List<terminoxdocumento> list= q.setMaxResults(limit).getResultList();
        helpjpa.disconnect();
        return list;
    }
    public ArrayList<Integer> listaIdPalabras(String words[])throws SQLException{
        ArrayList<Integer> list = new ArrayList<>();
        if(words.length==0){
            return list;
        }
        String sql=cmdBuscarIdWord(words);
        ResultSet rs=help.leerDatos(sql);
        while(rs.next()){
            int id=rs.getInt("idword");
            list.add(id);
        }
        help.cerrarConexion(rs);
        return list;
    }
    public Palabra idPalabraJPA(String word){
        EntityManager em=helpjpa.connect();        
        Query q = em.createQuery("select p FROM Palabra p where p.nombre=:nombre");
        q.setParameter("nombre",word);
        List<Palabra> l=q.setMaxResults(1).getResultList();
        if(l.isEmpty()){
            return new Palabra(-1,"nada");
        }
        else{
            return l.get(0);
        }
    }
    private String cmdBuscarTerminoXDocumento(ArrayList<Integer> listaId){
        String cmd="select idT,idD,td  from terminoxdocumento where ";
        cmd+=" idT="+listaId.get(0);
        int cont=0;
        for(Integer i : listaId){
            if(cont>0){
                cmd+=" or idT="+i;
            }
            cont++;
        }
        cmd+="order by td desc";
        return cmd;
    }
    private String cmdBuscarTerminoXDocumento(int id){
        String cmd="select idT,idD,tf  from terminoxdocumento where idT="+id;

        cmd+=" order by tf desc";
        return cmd;
    }
    private String cmdBuscarTerminoXDocumento(int id,int limit){
        String cmd="select idT,idD,tf  from terminoxdocumento where idT="+id;

        cmd+=" order by tf desc limit "+limit;
        return cmd;
    }
    private String cmdBuscarIdWord(String words[]){
        String cmd="select idword from termino where";
        int n=words.length;
        cmd+=" nombre='"+words[0]+"' ";
        for(int i=1;i<n;i++){
            cmd+=" or nombre='"+words[i]+"'";
        }
        return cmd;
    }
    private String cmdBuscarPalabra(String word){
        String cmd="select idword from termino where nombre like '"+word+"' limit 1";

        return cmd;
    }
    
}
