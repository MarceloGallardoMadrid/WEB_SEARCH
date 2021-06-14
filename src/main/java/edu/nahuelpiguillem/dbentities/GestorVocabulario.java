/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nahuelpiguillem.dbentities;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

/**
 *
 * @author pigui
 */
public class GestorVocabulario {
    DBDocumento dbd;
    DBPalabra dbp;
    DBTerminoXDocumento dbtxd;
    Vocabulario v;
    int iddocs;
    int idwords;
    long batchs;
    public GestorVocabulario(){
        dbd= new DBDocumento();
        dbp=new DBPalabra();
        dbtxd= new DBTerminoXDocumento();
        v = new Vocabulario();
        iddocs=dbd.leerUltimoIdInsertadoJPA();
        idwords=dbp.leerUltimoInsertadoJPA();
        batchs=0;
        leerVocabulario();
    }
    
    public void guardarDocumentosJPA(LinkedList<Documento> docs){
        System.out.println("Estas en guardarDocumentos JPA");
        for(Documento d: docs){
            guardarDocumentoJPA(d);
        }
    }
    
    /*Bloque de codigo de registro de datos utilizando JDBC*/
    public int[] guardarLoteDocumentosBatch(LinkedList<Documento> docs)throws SQLException{
        BDHelper help= new BDHelper();
        help.newBatch();
        for(Documento d : docs){
            guardarDocumentoBatch(d,help);
        }
        return help.runBatch();
    }
    public void guardarDocumentoBatch(Documento d,BDHelper help)throws SQLException{
        iddocs++;
        d.setIddoc(iddocs);
        help.addBatch(dbd.addDocBatch(d));
        batchs++;
        Iterator it=d.getV().iterator();
        int cont=0;
        while(it.hasNext()){
            cont++;
            Entry<String,Palabra> e=(Entry<String,Palabra>)it.next();
            String w=e.getKey();
            Palabra p=e.getValue();
            actualizarVocabularioBatch(w,p,help,d.getIddoc());
            agregarTerminoXDocumentoBatch(d,w,p.getMaxtf(),help);
            if(500==cont){
                cont=0;
                help.runBatch();
                help.newBatch();
            }
            if(batchs%10000==0){
                System.out.println("Estas en guardarDocumentos Batch");
                System.out.println("Ya van "+batchs+"insertados");
            }
        }
    }
    public void guardarDocumento(Documento d)throws SQLException{
        iddocs++;
        d.setIddoc(iddocs);
        
        try
        {dbd.addDoc(d);
        }
        catch(SQLException ex){
            System.out.println("salio mal el doc");
        }
        
        Iterator it = d.getV().iterator();
        while(it.hasNext())
        {
            Entry<String,Palabra> e=(Entry<String,Palabra>)it.next();
            String w=e.getKey();
            Palabra p=e.getValue();
            actualizarVocabulario(w,p,d);
            agregarTerminoXDocumento(d,w,p.getMaxtf());
        }

        
    }
    private void actualizarVocabulario(String w, Palabra p,Documento d){
        try{
        
            if(!v.contains(w)){
                v.add(w,p.getMaxtf());
                Palabra pv=v.get(w);
                dbp.addPalabra(pv);
                dbp.addDoc(pv);
            }
            else{
                Palabra pv=v.get(w);
                if(pv.getMaxtf()<p.getMaxtf()){
                    pv.setMaxtf(p.getMaxtf());
                    dbp.modificarMaxTf(pv, pv.getMaxtf(), d.getIddoc());
                }
                dbp.addDoc(pv);
            }
        }
       catch(SQLException ex){
               System.out.println("salio mal en vocabulario");
               System.out.println(ex.getMessage());
            }        
        }
    private void actualizarVocabularioBatch(String w,Palabra p, BDHelper help,int iddoc){
        try{
          if(!v.contains(w)){
            v.add(w);
            Palabra pv=v.get(w);              
            help.addBatch(dbp.addPalabraBatch(pv,iddoc));
            batchs++;
            help.addBatch(dbp.addDocBatch(pv));
            batchs++;
          }
          else{
               Palabra pv=v.get(w);
               if(pv.getMaxtf()<p.getMaxtf())
               {
                  pv.setMaxtf(p.getMaxtf());
                  help.addBatch(dbp.cmdmodificarMaxTf(pv,pv.getMaxtf(), iddoc));
                  batchs++;
                  help.addBatch(dbp.addDocBatch(pv));
                  batchs++;
               }
          }
         }
         catch(SQLException ex){
             System.out.println(ex.getMessage());
         } 
    }
    private void agregarTerminoXDocumentoBatch(Documento d,String w,int tf,BDHelper help){
        try{
            Palabra pv=v.get(w);
            help.addBatch(dbtxd.addTermXDocBatch(d, pv, tf));
            batchs++;
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    private void agregarTerminoXDocumento(Documento d,String w, int tf){
        try{
            Palabra pv=v.get(w);
            dbtxd.addTermXDoc(d, pv, tf);
        }
        catch(SQLException ex){
            System.out.println("salio mal agregar terminox doci");
            System.out.println(ex.getMessage());
        }
    }

    
    /*Bloque de codigo para limpiar la base de datos*/
    public void limpiarBd(){
        limpiarTxD();
        limpiarD();
        limpiarP();
    }
    public void limpiarBdBatch(){
        BDHelper help=new BDHelper();
        
        try{
            help.newBatch();
            LinkedList<String> cmds=cmdlimpiarTxD();
            int cb=0;
            for(String cmd : cmds){
                cb++;
                help.addBatch(cmd);
                if(cb==5000){
                    help.runBatch();
                    help.newBatch();
                    cb=0;
                }
            }
            help.runBatch();
            System.out.println("Elimine todos los txd");
            help.newBatch();
            cmds=cmdlimpiarT();
            cb=0;
            for(String cmd : cmds){
                cb++;
                help.addBatch(cmd);
                if(cb==5000){
                    help.runBatch();
                    help.newBatch();
                    cb=0;
                }
            }
            help.runBatch();
            System.out.println("Elimine todos los t");
            help.newBatch();
            cmds=cmdlimpiarD();
            cb=0;
            for(String cmd : cmds){
                cb++;
                help.addBatch(cmd);
                if(cb==10000){
                    help.runBatch();
                    help.newBatch();
                    cb=0;
                }
            }
            help.runBatch();
            System.out.println("Elimine todos los Documento");
            
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    private void limpiarTxD(){
        try{
            LinkedList<Integer> id= dbtxd.leerIdTerminoXDocumento();
            for(Integer i : id){
                dbtxd.eliminarTxD(i);
            }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }          
    }
    private LinkedList<String> cmdlimpiarTxD(){
        LinkedList<String> cmds= new LinkedList<>();
        try{
            LinkedList<Integer> id= dbtxd.leerIdTerminoXDocumento();
            id.forEach(i -> {
                cmds.addLast(dbtxd.cmdeliminarTxD(i));
            });
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        } 
        System.out.println("Termine de leer txd");
        return cmds;
    }
    private LinkedList<String> cmdlimpiarT(){
        LinkedList<String> cmds= new LinkedList<>();
        try{
            LinkedList<Integer> id= dbp.leerIdTermino();
            for(Integer i : id){
                cmds.addLast(dbp.cmdeliminarPalabra(i));
            }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        System.out.println("Termine de leer termin");
        return cmds;
    }
    private LinkedList<String> cmdlimpiarD(){

        LinkedList<String> cmds= new LinkedList<>();
        try{
            LinkedList<Integer> id= dbd.leerIdDocumento();
            for(Integer i : id){
                cmds.addLast(dbd.cmddeleteDoc(i));
            }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
        System.out.println("Termine de leer Doc");
        return cmds;        
    }
    private void limpiarD(){
         try{
            LinkedList<Integer> id= dbd.leerIdDocumento();
            for(Integer i : id){
                dbd.deleteDoc(i);
            }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }          
           
    }
    private void limpiarP(){
         try{
            LinkedList<Integer> id= dbp.leerIdTermino();
            for(Integer i : id){
                dbp.eliminarPalabra(i);
            }
        }
        catch(SQLException ex){
            System.out.println(ex.getMessage());
        }          
           
    }
    /*Bloque de codigo del registro de datos utilizando JPA*/
    private void guardarDocumentoJPA(Documento d) {
        iddocs++;
        d.setIddoc(iddocs);
        
        dbd.addDocJPA(d);
        Iterator it = d.getV().iterator();
        while(it.hasNext())
        {
            Entry<String,Palabra> e=(Entry<String,Palabra>)it.next();
            String w=e.getKey();
            Palabra p=e.getValue();
            actualizarVocabularioJPA(w,p,d);
            agregarTerminoXDocumento(d,w,p.getMaxtf());
        }  
    }
    public void actualizarVocabularioJPA(String w,Palabra p, Documento d){
        if(!v.contains(w)){
            v.add(w,p.getMaxtf());
            Palabra pv=v.get(w);
           dbp.addPalabraJPA(pv);        
        }
        else{
            Palabra pv=v.get(w);
            if(pv.getMaxtf()<p.getMaxtf()){
                pv.setMaxtf(p.getMaxtf());
            }
        } 
    }
    public void agregarTerminoXDocumentoJPA(Documento d,String w, int tf){
            Palabra pv=v.get(w);
            dbtxd.addTermXDocJPA(d, pv, tf);

    }

    public void guardarLoteDocumentosJPABatch(LinkedList<Documento> docs){
        BDHelperJPA helpjpa= new BDHelperJPA();
        helpjpa.beginTransaction();
        for(Documento d:docs){
            guardarDocumentoJPABatch(d,helpjpa);
        }
        helpjpa.disconnect();
    }
    public void guardarDocumentoJPABatch(Documento d,BDHelperJPA helpjpa){
        iddocs++;
        d.setIddoc(iddocs);
        
        dbd.addDocJPABatch(d,helpjpa);
        Iterator it = d.getV().iterator();
        while(it.hasNext())
        {
            Entry<String,Palabra> e=(Entry<String,Palabra>)it.next();
            String w=e.getKey();
            Palabra p=e.getValue();
            actualizarVocabularioJPABatch(w,p,d,helpjpa);
            agregarTerminoXDocumento(d,w,p.getMaxtf(),helpjpa);
        } 
    }
    public void actualizarVocabularioJPABatch(String w,Palabra p, Documento d,BDHelperJPA helpjpa){
        if(!v.contains(w)){
            v.add(w,p.getMaxtf());
            Palabra pv=v.get(w);
           dbp.addPalabraJPABatch(pv,helpjpa);        
        }
        else{
            Palabra pv=v.get(w);
            if(pv.getMaxtf()<p.getMaxtf()){
                pv.setMaxtf(p.getMaxtf());
            }
        } 
    }
    public void agregarTerminoXDocumento(Documento d,String w,int tf,BDHelperJPA helpjpa){
            Palabra pv=v.get(w);
            dbtxd.addTermXDocJPABatch(d, pv, tf,helpjpa);
    }
    private void leerVocabulario(){
        List<Palabra> lista=dbp.leetPalabraJPA();
        for(Palabra p : lista){
            v.add(p.nombre);
        }
        
    }
    
}
