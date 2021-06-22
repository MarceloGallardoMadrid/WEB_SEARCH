package edu.nahuelpiguillem.motor;

import java.util.Objects;

public class DocumentoPosteo implements Comparable<DocumentoPosteo> {
    private int id;
    private int tf;

    public DocumentoPosteo(int id, int tf) {
        this.id = id;
        this.tf = tf;
    }

    public int getTf() {
        return tf;
    }

    public void setTf(int tf) {
        this.tf = tf;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public int compareTo(DocumentoPosteo d) {
        return this.tf - d.tf;
    }

    @Override
    public String toString() {
        return "Documento{" +
                "id=" + id +
                ", tf=" + tf +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentoPosteo documentoPosteo = (DocumentoPosteo) o;
        return id == documentoPosteo.id &&
                tf == documentoPosteo.tf;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tf);
    }
}
