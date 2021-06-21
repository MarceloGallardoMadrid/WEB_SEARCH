/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nahuelpiguillem.tpu_web.controladores;

import edu.nahuelpiguillem.buscador.Buscador;
import edu.nahuelpiguillem.buscador.Documentos;
import edu.nahuelpiguillem.dbentities.DBDocumento;
import edu.nahuelpiguillem.filemanipulation.LectorDocumentos;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

/**
 *
 * @author pigui
 */
@Path("search")
public class BusquedaControlador {
    @Inject ServletContext context;
    @GET
    @Path("dirWork")
    public Response mostrarDirectorioTrabajo(){
        return Response.ok(System.getProperty("user.dir")).build();
    }
    
    @GET
    @Path("test")
    public Response test(){
        return Response.ok("ok").build();
    }
    @GET
    @Path("buscar/{lista}/{R}")
    //@Produces(MediaType.APPLICATION_JSON)
    public Response buscar(@PathParam("lista") String lista,@PathParam("R") int R){
        String[] palabras=lista.split("[.]");
        Buscador b = new  Buscador(); 
        List list = b.buscar(palabras, R);
        DBDocumento dbd = new DBDocumento();
        List<String> nombres=dbd.leerNombresJPA(list);
        StringBuilder sb = new StringBuilder("<p>");
        for(String n:nombres){
            sb.append("<br>").append(n);
        }
        sb.append("</p>");
        return Response.ok(sb.toString()).build();
    }
    @GET
    @Path("guardar")
    public Response guardar(){
        LectorDocumentos ld = new LectorDocumentos();
        int ans=ld.guardarDocumentoAdd();
        return Response.ok("Se guardaron "+ans+" documentos").build();
        
    }
    @GET
    @Path("leer/{nombre}")
    public Response leer(@PathParam("nombre") String nombre){
        

    File f= new File("./docs/"+nombre);
    

        

    return Response.ok(f).build();
        
        
    }

}
