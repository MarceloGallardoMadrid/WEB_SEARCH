package edu.nahuelpiguillem.dbentities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="documento")
public class Documento implements Serializable{
    @Id
    @Column(name="iddoc")
    long iddoc;
    String nombre;
    int words;
    @Transient
    String ruta;
    @Transient
    Vocabulario v;
    public Documento() {
        v=new Vocabulario();
    }
    public Documento(String nombre,String ruta){
        this();
        this.nombre=nombre;
        this.ruta=ruta;
    }

    public Documento(long id, String nombre, int words, String ruta) {
        
        this.iddoc = id;
        this.nombre = nombre;
        this.words = words;
        this.ruta = ruta;
    }
    public void addPalabra(String w){
        v.add(w);
    }
    public void setIddoc(long id) {
        this.iddoc = id;
    }
    public boolean contains(String w){
        return this.v.contains(w);
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setWords(int words) {
        this.words = words;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Documento(int id, String nombre) {
        this.iddoc = id;
        this.nombre = nombre;
    }

    public long getIddoc() {
        return iddoc;
    }

    public String getNombre() {
        return nombre;
    }

    public int getWords() {
        return words;
    }

    public String getRuta() {
        return ruta;
    }
    public void sum(){
        words++;
    }
    public Vocabulario getV() {
        return v;
    }


    @Override
    public String toString() {
        return "Documento{" + "id=" + iddoc + ", nombre=" + nombre + ", words=" + words + ", ruta=" + ruta + ",voc="+v.size()+'}';
    }
    public String longToString(){
        StringBuilder sb= new  StringBuilder("Documento{" + "id=" + iddoc + ", nombre=" + nombre + ", words=" + words + ", ruta=" + ruta + ",voc="+v.size()+"}\n");
        sb.append(v.toString());
        return sb.toString();
    }
   
    
}