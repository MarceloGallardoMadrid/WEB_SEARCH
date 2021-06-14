package edu.nahuelpiguillem.dbentities;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
//Por ahora sin vocabulario.Solo leo de un documento y guardo palabras
//Si es posible  un bulk
public class Vocabulario{
    HashMap<String,Palabra> vocabulario;
    int contPalabras;
    public Vocabulario(){
        vocabulario=new HashMap<>();
        contPalabras=0;
    }
    public void add(String w){
	if(!vocabulario.containsKey(w)){
                contPalabras++;
		Palabra p = new Palabra(contPalabras,w);
                p.sumMaxTf();
		vocabulario.put(w,p);
	}
	else{
                Palabra p=vocabulario.get(w);
		p.sumMaxTf();
	}
    }
    public void add(String w,int tf){
	if(!vocabulario.containsKey(w)){
            contPalabras++;
            Palabra p = new Palabra(contPalabras,w,tf);
            vocabulario.put(w,p);
	}
	else{
                Palabra p=vocabulario.get(w);
		p.sumMaxTf(tf);
	}
    }
    public Palabra get(String w)
    {
        Palabra p=null;
        p=vocabulario.get(w);
        return p;
    }
    public boolean contains(String w){
        return vocabulario.containsKey(w);
    }
    public int size(){
        return vocabulario.size();
    }
    public Iterator iterator(){
        return vocabulario.entrySet().iterator();
    }

    @Override
    public String toString(){
        Iterator it=vocabulario.entrySet().iterator();
        StringBuilder sb = new StringBuilder("[");
        while(it.hasNext()){
            Entry<Palabra,Integer> e =(Entry<Palabra,Integer>) it.next();
            sb.append("{").append(e.getKey().getNombre()).append(",").append(e.getValue()).append("}\n");
        }
        sb.append("]");
        return sb.toString();
    }
    
}