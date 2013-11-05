package webservices.ch01;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Comment here about the class
 * User: Fizal
 * Date: 11/5/13
 * Time: 12:16 AM
 */
@Path("/helloworldresources")
public class HelloWorldResources {
    @GET
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/plain")
    public String getPlain() {
        return "Hello World!!!";
    }
}