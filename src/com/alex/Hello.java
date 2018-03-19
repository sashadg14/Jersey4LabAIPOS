package com.alex;

import com.google.gson.Gson;
import entity.Section;
import entity.Subsection;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/sections")
public class Hello{
    Gson gson=new Gson();
    CustomService customService=new CustomService();
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getMessage() {
        return gson.toJson(customService.getSectionsStr());
    }

    @GET
    @Path("{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getConcreateSection(@PathParam("name") String name){
        return gson.toJson(new Section(name,customService.getSubsectionsList(name)));
    }

    @GET
    @Path("{name}/{subsection}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSubection(@PathParam("name") String name,@PathParam("subsection") String subsectionName){
        return gson.toJson(customService.getSubsection(name,subsectionName));
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addSection(@FormParam("section") String section){
        customService.addSection(gson.fromJson(section,Section.class));
    }

    @POST
    @Path("{name}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void addSubsection(@PathParam("name") String name,@FormParam("subsection") String section){
        customService.addSubsection(name,gson.fromJson(section,Subsection.class));
    }

    @PUT
    @Path("{name}/{subsection}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void updateSubection(@PathParam("name") String name,@PathParam("subsection") String subsectionName,@FormParam("subsection") String section){
        customService.updateSubsection(name,gson.fromJson(section,Subsection.class));
    }

    @DELETE
    @Path("{name}/{subsection}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public void deleteSubsection(@PathParam("name") String name,@PathParam("subsection") String subsectionName){
        customService.deleteSubsection(name,subsectionName);
    }
}