/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nahuelpiguillem.filemanipulation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.stream.Stream;
import java.util.LinkedList;
import edu.nahuelpiguillem.dbentities.*;
import java.sql.SQLException;
/**
 *
 * @author pigui
 */
public class LectorDocumentos {
    BDHelper help;
    public LectorDocumentos(){
       help= new BDHelper();
    }
    public void verNombres()throws FileNotFoundException{
	LinkedList<String> rutas= new LinkedList<>();
        File d = new File("../mifiles");
        //File d = new File("../../mifiles");
        Stream.of(d.listFiles((arch,nom)->nom.endsWith(".txt")))
            .filter(p->p.isFile() && !p.isHidden())
            .forEach(doc->rutas.addFirst(doc.toString()));
        rutas.forEach(ruta -> {
            System.out.println(findNombre(ruta));
        });
    }
    public void verDoc()throws FileNotFoundException{
        Documento doc= new Documento(findNombre("../mifiles/t1.txt"),"../mifiles/t1.txt");
        doc.setWords(0);
        Scanner sc;
        sc = new Scanner(new BufferedReader(new FileReader("../mifiles/t1.txt")));
        sc.useDelimiter("[«»“�?·—’=* .,\\r\\n\\[\\]'\\(\\)\\-\":;0-9]");
        while(sc.hasNext()){
            String palabra=sc.next();
            if(!palabra.isEmpty()){
                palabra=palabra.toLowerCase();
                
                doc.addPalabra(palabra);
            }
        }
        System.out.println(doc.longToString());
    }
    public void guardarDocumentoAdd(){
        System.out.println("Add Document");
        LinkedList<String> rutas= new LinkedList<>();
        LinkedList<Documento> listDoc= new LinkedList<>();
        GestorVocabulario gv = new GestorVocabulario();
        int stepBatch=2;
        int totalDocs=0;
        int cont=0;
        try{
            System.out.println("dentro del catch");
            int charsRuta=3;
            File d=new File("add");
            //File d = new File("../../Files");
            File[] files=d.listFiles();
            System.out.println(files);
            for(int i=0;i<files.length;i++){
                File f=files[i];
                if(f.getName().endsWith(".txt"))
                {
                    if(f.isFile()&&!f.isHidden()){
                        rutas.addFirst(f.toString());
                    }
                }
            }
/*            Stream.of(d.listFiles((arch,nom)->nom.endsWith(".txt")))
                .filter(p->p.isFile() && !p.isHidden())
                .forEach(doc->rutas.addFirst(doc.toString()));
*/            int docs=rutas.size();
            System.out.println("Se deben procesar "+docs+" documentos");
            long t1=System.currentTimeMillis();
            for(String ruta : rutas){
                cont++;
                leerRuta(ruta,listDoc,charsRuta+1);
                if(cont==stepBatch){
                    gv.guardarLoteDocumentosJPABatch(listDoc);
                    totalDocs+=cont;
                    cont=0;
                    
                    listDoc=new LinkedList<>();
                    System.out.println("Se procesaron "+totalDocs+" documentos");
                }
                if(totalDocs==100){
                    System.out.println("Se procesaron 100 documetnos");
                    break;
                }
            }
            gv.guardarLoteDocumentosJPABatch(listDoc);
            long t2 = System.currentTimeMillis();
            long tt=t2-t1;
            System.out.println("Se tardo "+tt+" milisegundos en procesar 100 docs");
        }
        catch(FileNotFoundException sqex){
            System.out.println(sqex.getMessage());
        }
    }
    public void guardarDocumentosJPABatch(){
        System.out.println("JPA BATCH");
        LinkedList<String> rutas= new LinkedList<>();
        LinkedList<Documento> listDoc= new LinkedList<>();
        GestorVocabulario gv = new GestorVocabulario();
        int stepBatch=2;
        int totalDocs=0;
        int cont=0;
        try{
            String rutaDir="../Files";
            int charsRuta=rutaDir.length()+1;
            File d=new File("../Files");
            //File d = new File("../../Files");
            Stream.of(d.listFiles((arch,nom)->nom.endsWith(".txt")))
                .filter(p->p.isFile() && !p.isHidden())
                .forEach(doc->rutas.addFirst(doc.toString()));
            int docs=rutas.size();
            System.out.println("Se deben procesar "+docs+" documentos");
            long t1=System.currentTimeMillis();
            for(String ruta : rutas){
                cont++;
                leerRuta(ruta,listDoc,charsRuta);
                if(cont==stepBatch){
                    gv.guardarLoteDocumentosJPABatch(listDoc);
                    totalDocs+=cont;
                    cont=0;
                    
                    listDoc=new LinkedList<>();
                    System.out.println("Se procesaron "+totalDocs+" documentos");
                }
                if(totalDocs==100){
                    System.out.println("Se procesaron 100 documetnos");
                    break;
                }
            }
            gv.guardarLoteDocumentosJPABatch(listDoc);
            long t2 = System.currentTimeMillis();
            long tt=t2-t1;
            System.out.println("Se tardo "+tt+" milisegundos en procesar 100 docs");
        }
        catch(FileNotFoundException sqex){
            System.out.println(sqex.getMessage());
        }
    }
    public void guardarDocumentosJPA(){
        System.out.println("JPA");
        LinkedList<String> rutas= new LinkedList<>();
        LinkedList<Documento> listDoc= new LinkedList<>();
        GestorVocabulario gv = new GestorVocabulario();
        int stepBatch=5;
        int totalDocs=0;
        int cont=0;
        try{
            File d=new File("mifiles");
            //File d = new File("../../Files");
            Stream.of(d.listFiles((arch,nom)->nom.endsWith(".txt")))
                .filter(p->p.isFile() && !p.isHidden())
                .forEach(doc->rutas.addFirst(doc.toString()));
            int docs=rutas.size();
            System.out.println("Se deben procesar "+docs+" documentos");
            
            for(String ruta : rutas){
                cont++;
                leerRuta(ruta,listDoc);
                if(cont==stepBatch){
                    gv.guardarDocumentosJPA(listDoc);
                    totalDocs+=cont;
                    cont=0;
                    
                    listDoc=new LinkedList<>();
                    System.out.println("Se procesaron "+totalDocs+" documentos");
                }
            }
            gv.guardarDocumentosJPA(listDoc);
            
        }
        catch(FileNotFoundException sqex){
            System.out.println(sqex.getMessage());
        }
    }
    public void guardarDocumentosBatch() {
        LinkedList<String> rutas= new LinkedList<>();
        LinkedList<Documento> listDoc= new LinkedList<>();
        GestorVocabulario gv = new GestorVocabulario();
        int stepBatch=2;
        int totalDocs=0;
        int cont=0;
        try{
            File d=new File("mifiles2");
            //File d = new File("../../Files");
            Stream.of(d.listFiles((arch,nom)->nom.endsWith(".txt")))
                .filter(p->p.isFile() && !p.isHidden())
                .forEach(doc->rutas.addFirst(doc.toString()));
            int docs=rutas.size();
            System.out.println("Se deben procesar "+docs+" documentos");
            
            for(String ruta : rutas){
                cont++;
                leerRuta(ruta,listDoc);
                if(cont==stepBatch){
                    gv.guardarLoteDocumentosBatch(listDoc);
                    totalDocs+=cont;
                    cont=0;
                    
                    listDoc=new LinkedList<>();
                    System.out.println("Se procesaron "+totalDocs+" documentos");
                }
            }
            gv.guardarLoteDocumentosBatch(listDoc);
            
        }
        catch(SQLException | FileNotFoundException sqex){
            System.out.println(sqex.getMessage());
        }
    }
    
    public LinkedList<Documento> leerDocumentos() throws FileNotFoundException{
        LinkedList<String> rutas= new LinkedList<>();
        LinkedList<Documento> listDoc= new LinkedList<>();
        File d = new File("../mifiles2");
        //File d = new File("../mifiles");
        //File d = new File("../../mifiles");
        Stream.of(d.listFiles((arch,nom)->nom.endsWith(".txt")))
            .filter(p->p.isFile() && !p.isHidden())
            .forEach(doc->rutas.addFirst(doc.toString()));
        int cont=0;
	for(String ruta : rutas){
           cont++;
           leerRuta(ruta,listDoc);
           if(cont==50){
               cont=0;
           }
        }
        return listDoc;       
    }
    public void guardarDocumentos(){
        GestorVocabulario gv = new GestorVocabulario();
        try{
            LinkedList<Documento> listDoc=leerDocumentos();
            Documento d = listDoc.get(0);
                gv.guardarDocumento(d);
            
        }
        catch(FileNotFoundException | SQLException ex){
            System.out.println(ex.getMessage());
        }       
    }
    private void leerRuta(String ruta,LinkedList<Documento> listDoc,int offset)throws FileNotFoundException{
        Documento doc= new Documento(findNombre(ruta,offset),ruta);
        doc.setWords(0);
        Scanner sc = new Scanner(new BufferedReader(new FileReader(ruta)));
        sc.useDelimiter("[«»“�?·—’=* .,\\r\\n\\[\\]'\\(\\)\\-\":;0-9]");
        while(sc.hasNext()){            
            String palabra=sc.next();
            if(!palabra.isEmpty()){
                doc.sum();
                palabra=palabra.toLowerCase();
                
                doc.addPalabra(palabra);
            }
        }
        listDoc.addLast(doc);
    }
    private void leerRuta(String ruta,LinkedList<Documento> listDoc)throws FileNotFoundException{
        Documento doc= new Documento(findNombre(ruta),ruta);
        doc.setWords(0);
        Scanner sc = new Scanner(new BufferedReader(new FileReader(ruta)));
        sc.useDelimiter("[«»“�?·—’=* .,\\r\\n\\[\\]'\\(\\)\\-\":;0-9]");
        while(sc.hasNext()){            
            String palabra=sc.next();
            if(!palabra.isEmpty()){
                doc.sum();
                palabra=palabra.toLowerCase();
                
                doc.addPalabra(palabra);
            }
        }
        listDoc.addLast(doc);
    }
    private String findNombre(String ruta,int offset){
                char[] ch=ruta.toCharArray();
                String nombre="";
                for(int i=offset;i<ch.length;i++){
                    nombre+=ch[i];
                }
		return nombre;
	}
    private String findNombre(String ruta){
                char[] ch=ruta.toCharArray();
                String nombre="";
                for(int i=11;i<ch.length;i++){
                    nombre+=ch[i];
                }
		return nombre;
    }
}
    

