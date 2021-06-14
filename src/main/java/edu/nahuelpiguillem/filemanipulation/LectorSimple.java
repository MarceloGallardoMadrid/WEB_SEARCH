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

/**
 *
 * @author pigui
 */
public class LectorSimple {
   
    public LectorSimple(){
       
    }
    public void leerArchivo() throws FileNotFoundException{
        
        File d = new File("../mifiles");
        //File d = new File("../../mifiles");
        Stream.of(d.listFiles((arch,nom)->nom.endsWith(".txt")))
            .filter(p->p.isFile() && !p.isHidden())
            .forEach(System.out::println);
        
        System.out.println();
        Scanner sc;
        sc = new Scanner(new BufferedReader(new FileReader("../mifiles/0ddc809a.txt")));
        sc.useDelimiter("[«»“”·—’=* .,\\r\\n\\[\\]'\\(\\)\\-\":;0-9]");
        while(sc.hasNext()){
            String palabra=sc.next();
            if(!palabra.isEmpty()){
                System.out.println(palabra);
            }
        }
    }
}
