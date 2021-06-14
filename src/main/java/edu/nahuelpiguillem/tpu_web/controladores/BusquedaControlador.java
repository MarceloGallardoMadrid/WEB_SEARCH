/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nahuelpiguillem.tpu_web.controladores;

import edu.nahuelpiguillem.buscador.Buscador;
import edu.nahuelpiguillem.buscador.Documentos;
import edu.nahuelpiguillem.filemanipulation.LectorDocumentos;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author pigui
 */
@Path("search")
public class BusquedaControlador {
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
        StringBuilder sb = new StringBuilder("<p>");
        for(Object o:list){
            sb.append("<br>").append(o);
        }
        sb.append("</p>");
        return Response.ok(sb.toString()).build();
    }
    @GET
    @Path("guardar")
    public Response guardar(){
        LectorDocumentos ld = new LectorDocumentos();
        ld.guardarDocumentoAdd();
       Buscador b = new  Buscador(); 
        List list = b.buscar(new String[] {"trajano"}, 5);
        StringBuilder sb = new StringBuilder("<p>");
        for(Object o:list){
            sb.append("<br>").append(o);
        }
        sb.append("</p>");
        return Response.ok(sb.toString()).build();
        
    }
}
