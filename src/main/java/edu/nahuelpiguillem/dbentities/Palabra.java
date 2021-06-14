package edu.nahuelpiguillem.dbentities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="termino")
public class Palabra implements Serializable{
    @Id
    @Column(name="idword")
    int idword;
    String  nombre;
    @Column(name="MaxTf")
    int maxtf;
    int iddoc;
    int cantdoc;
    public Palabra(){
        
    }

    @Override
    public String toString() {
        return "Palabra{" + "id=" + idword + ", nombre=" + nombre + ", maxtf=" + maxtf + ", iddoc=" + iddoc + ", cantdoc=" + cantdoc + '}';
    }
    public Palabra(String nombre){
        this.nombre=nombre;
        cantdoc=0;
        maxtf=0;
    }
    public Palabra(int id, String nombre) {
        this.idword = id;
        this.nombre = nombre;
        cantdoc=0;
        maxtf=0;
    }
    public Palabra(int id,String nombre,int maxTf){
        this.idword=id;
        this.nombre=nombre;
        this.maxtf=maxTf;
    }
    public Palabra(int id, String nombre, int maxtf, int iddoc, int cantdoc) {
        this.idword = id;
        this.nombre = nombre;
        this.maxtf = maxtf;
        this.iddoc = iddoc;
        this.cantdoc = cantdoc;
    }

    public void setIdword(int id) {
        this.idword = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMaxtf(int maxtf) {
        this.maxtf = maxtf;
    }

    public void setIddoc(int iddoc) {
        this.iddoc = iddoc;
    }

    public void setCantdoc(int cantdoc) {
        this.cantdoc = cantdoc;
    }
    public void sumDoc(){
        cantdoc++;
    }
    public int getIdword() {
        return idword;
    }

    public String getNombre() {
        return nombre;
    }

    public int getMaxtf() {
        return maxtf;
    }

    public int getIddoc() {
        return iddoc;
    }

    public int getCantdoc() {
        return cantdoc;
    }
    public void sumMaxTf(){
        maxtf++;
    }
    public void sumMaxTf(int tf){
        maxtf+=tf;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Palabra other = (Palabra) obj;
        //return this.id==other.id;
        //return Objects.equals(this.id, other.id);
        return this.nombre.compareTo(other.nombre)==0;
    }
    
   
    private int hashi(){
        int ans=51;
        for(int i=0;i<this.nombre.length()-1;i++){
            ans=51*ans+this.nombre.charAt(i);
        }
        return ans;
    }
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + hashi();
        return hash;
    }
    

    
}