package edu.nahuelpiguillem.buscador;

import edu.nahuelpiguillem.motor.DocumentoPosteo;
import edu.nahuelpiguillem.motor.Palabra;

import java.util.*;

/*public class Documentos {
   public Hashtable<Integer, Float> documentos = new Hashtable<>();

    public void agregarPalabra(Palabra palabra, int R) {
        //Se iteran los R primeros valores tf de la lista de posteo en orden decendente
        int count = 0;
        for (int tf: palabra.posteo.keySet()) {
            if (count == R) break;
            count ++;
            //Para cada valor de tf se recorren sus documentos
            for (int doc: palabra.posteo.get(tf)) {
                //Si el documento existe incrementa su apariciones en 1
                if (documentos.containsKey(doc)){
                    documentos.replace(doc, documentos.get(doc) +
                            ((float) tf / ((float) palabra.maxTf * (float) palabra.getNr())));
                }
                //Si el documento no existe lo agrega con un valor 1
                else {
                    documentos.put(doc, ((float) tf / ((float) palabra.maxTf * (float) palabra.getNr())));
                }
            }
        }
    }

    //Se define un set el cual esta ordenado por sus valores de manera descendente
    static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
                new Comparator<Map.Entry<K,V>>() {
                    @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                        int res = e2.getValue().compareTo(e1.getValue());
                        return res != 0 ? res : 1;
                    }
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

    //Devuelve la lista de los R elementos con mas palabras ordenados de forma descendente
    public List documentosOrdenados(int R){
        List<Documentos> ordenados = new ArrayList(entriesSortedByValues(this.documentos));
        if (ordenados.size() < R) {
            return ordenados;
        }
        else {
            return ordenados.subList(0, R);
        }
        
    }
}*/

public class Documentos {
    public Hashtable<Integer, Float> documentos = new Hashtable<>();

    public void agregarPalabra(Palabra palabra, int R) {
        //Se iteran los R primeros valores tf de la lista de posteo en orden decendente
        /*for (int tf: palabra.posteo.keySet()) {
            if (count == R) break;
            count ++;
            //Para cada valor de tf se recorren sus documentos
            for (int doc: palabra.posteo.get(tf)) {
                //Si el documento existe incrementa su apariciones en 1
                if (documentos.containsKey(doc)){
                    documentos.replace(doc, documentos.get(doc) +
                            ((float) tf / ((float) palabra.maxTf * (float) palabra.getNr())));
                }
                //Si el documento no existe lo agrega con un valor 1
                else {
                    documentos.put(doc, ((float) tf / ((float) palabra.maxTf * (float) palabra.getNr())));
                }
            }
        }*/
        int count = 0;
        for (DocumentoPosteo dp: palabra.posteo) {
            if (count == R) break;
            count++;
            float value = ((float) dp.getTf() / ((float) palabra.maxTf * (float) palabra.getNr()));

            if (documentos.containsKey(dp.getId())){
                documentos.replace(dp.getId(), documentos.get(dp.getId()) + value);
            } else {
                //Si el documento no existe lo agrega con un valor inicial
                documentos.put(dp.getId(), value);
            }
        }
    }

    //Se define un set el cual esta ordenado por sus valores de manera descendente
    static <K,V extends Comparable<? super V>> SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) {
        SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
                new Comparator<Map.Entry<K,V>>() {
                    @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                        int res = e2.getValue().compareTo(e1.getValue());
                        return res != 0 ? res : 1;
                    }
                }
        );
        sortedEntries.addAll(map.entrySet());
        return sortedEntries;
    }

    //Devuelve la lista de los R elementos con mas palabras ordenados de forma descendente
    public List<Map.Entry<Integer,Float>> documentosOrdenados(int R){
        List<Map.Entry<Integer,Float>> ordenados = new ArrayList(entriesSortedByValues(this.documentos));
        if (ordenados.size() < R) {
            return ordenados;
        }
        else {
            return ordenados.subList(0, R);
        }

    }
}


