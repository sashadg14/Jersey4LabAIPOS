package client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entity.Section;
import entity.Subsection;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ServiseConnector {
    ClientConfig config;
    javax.ws.rs.client.Client client;
    WebTarget target ;
    Gson gson;
    public ServiseConnector(){
        config = new ClientConfig();
        client = ClientBuilder.newClient(config);
        target = client.target(getBaseURI());
        gson=new Gson();
    }

    public Subsection getSubsection(String sectionName, String subsectionName) {
        String response = target.path("rest").
                path("sections").
                path(sectionName).
                path(subsectionName).
                request().
                accept(MediaType.APPLICATION_JSON).
                get(String.class);
        return gson.fromJson(response,Subsection.class);
    }

    public List<String> getSections() {
        String response = target.path("rest").
                path("sections").
                request().
                accept(MediaType.APPLICATION_JSON).
                get(String.class);
        return gson.fromJson(response, new TypeToken<List<String>>(){}.getType());
    }
    public Section getSection(String name) {
        String response = target.path("rest").
                path("sections").
                path(name).
                request().
                accept(MediaType.APPLICATION_JSON).
                get(String.class);
        return gson.fromJson(response,Section.class);
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/").build();
    }
    public void deleteSubsection(String sectionName, String subsectionName) {
        Response response = target.path("rest").
                path("sections").
                path(sectionName).
                path(subsectionName).
                request().
                delete();
        System.out.println(response.toString());
    }

    public void updateSubsection(String sectionName, String subsectionName, String newInfo) {
        Form form=new Form();
        form.param("subsection",gson.toJson(new Subsection(subsectionName,newInfo)));
        //System.out.println(gson.toJson(new Subsection(subsectionName,newInfo)));
        Response response = target.path("rest").
                path("sections").
                path(sectionName).
                path(subsectionName).
                request().
                put(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED),Response.class);
        System.out.println(response.toString());
    }

    public void addSubsection(String sectionName, String subsection) {
        Form form=new Form();
        form.param("subsection",gson.toJson(new Subsection(subsection,"")));
        Response response = target.path("rest").
                path("sections").
                path(sectionName).
                request().
                post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED),Response.class);
    }

    public void addSection(String section) {
        Form form=new Form();
        form.param("section",gson.toJson(new Section(section,new ArrayList<>())));
        Response response = target.
                path("rest").
                path("sections").
                request().
                post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED),Response.class);

    }
    
}
