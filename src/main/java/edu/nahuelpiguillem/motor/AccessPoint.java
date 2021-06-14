/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nahuelpiguillem.motor;


/**
 *
 * @author pigui
 */
public class AccessPoint {
    VocabularioFactory vf;
    public AccessPoint(){
        vf= new VocabularioFactory();
    }
    public Vocabulario crearVocabularioJPA(String palabras[],int limit){
        Vocabulario v= vf.fabricarVocabularioJPA(palabras,limit);
        return v;
    }
    public Vocabulario crearVocabulario(String palabras[]){
        Vocabulario v= vf.fabricarVocabulario(palabras);
        return v;
    }

}
