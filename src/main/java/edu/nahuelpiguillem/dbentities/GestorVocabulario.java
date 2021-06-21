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
    long iddocs;
    long idwords;
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
    






    
    /*Bloque de codigo para limpiar la base de datos*/
    /*Bloque de codigo del registro de datos utilizando JPA*/

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
