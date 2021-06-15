/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nahuelpiguillem.dbentities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author pigui
 */
@Entity
@Table(name="terminoxdocumento")
public class terminoxdocumento implements Serializable {
/*    @Id
    @GeneratedValue(strategy=GenerationType.AUTO,generator="native")
    @GenericGenerator(name="native",strategy="native")
    int idtxd;
    
*/  @Id 
    long idT;
  @Id
    long idD;
    
    int tf;

    public terminoxdocumento() {
    }

    public terminoxdocumento(int idtxd, long idT, long idD, int tf) {
        //this.idtxd = idtxd;
        this.idT = idT;
        this.idD = idD;
        this.tf = tf;
    }

    public terminoxdocumento(long idT, long idD, int tf) {
        this.idT = idT;
        this.idD = idD;
        this.tf = tf;
    }

/*    public int getIdtxd() {
        return idtxd;
    }
*/
    public long getIdT() {
        return idT;
    }

    public long getIdD() {
        return idD;
    }

    public int getTf() {
        return tf;
    }

/*    public void setIdtxd(int idtxd) {
        this.idtxd = idtxd;
    }
*/
    public void setIdT(long idT) {
        this.idT = idT;
    }

    public void setIdD(long idD) {
        this.idD = idD;
    }

    public void setTf(int tf) {
        this.tf = tf;
    }
    
    
    
}
