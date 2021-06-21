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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
/**
 *
 * @author pigui
 */
public class LectorDocumentos {
   
    public LectorDocumentos(){
       
    }

    private void copiarArchivo(File f) throws IOException{
        Path fuente = Paths.get("./add/"+f.getName());
        Path destino=Paths.get("./docs/"+f.getName());
        Files.copy(fuente, destino, StandardCopyOption.REPLACE_EXISTING);
    }
    public int guardarDocumentoAdd(){
        int ans=0;
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
            for(int i=0;i<files.length;i++){
                File f=files[i];
                
                if(f.getName().endsWith(".txt"))
                {
                    if(f.isFile()&&!f.isHidden()){
                        try{
                            copiarArchivo(f);
                            ans++;
                        }
                        catch(IOException fex){
                           System.out.println(fex.getMessage());
                        }
                        rutas.addFirst(f.toString());
                    }
                }
            }
         
            int docs=rutas.size();
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
            }
            gv.guardarLoteDocumentosJPABatch(listDoc);
            long t2 = System.currentTimeMillis();
            long tt=t2-t1;
            System.out.println("Se tardo "+tt+" milisegundos en procesar 500 docs");
        }
        catch(FileNotFoundException sqex){
            System.out.println(sqex.getMessage());
            ans=-1;
        }
        return ans;
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
    

