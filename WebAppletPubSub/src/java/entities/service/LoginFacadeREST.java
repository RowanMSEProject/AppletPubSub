/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entities.service;

import entities.Login;
import entities.Userroles;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.JOptionPane;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import producer.ProduceSender;

/**
 *
 * @author mse
 */
@Stateless
@Path("entities.login")
public class LoginFacadeREST extends AbstractFacade<Login> {
    @PersistenceContext(unitName = "WebAppletPubSubPU")
    private EntityManager em;

    public LoginFacadeREST() {
        super(Login.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Login entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Login entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Login find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Login> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Login> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @POST
    @Path("/create")
    @Consumes({"application/x-www-form-urlencoded", "application/xml", "application/json"})
    public void createUser(@FormParam("firstName") String firstName, 
                           @FormParam("lastName") String lastName,
                           @FormParam("username") String username,
                           @FormParam("password") String password,
                           @FormParam("role") String role) {
        if (checkPassword(password)) {
            List<Login> users = em.createNamedQuery("Login.findAll").getResultList();
            int id = users.get(users.size() - 1).getUserid();
            id++;
            Login newUser = new Login(id, username, password, firstName, lastName);
            int roleID = Integer.parseInt(role);
            Userroles newRole = new Userroles(roleID);
            newUser.setRoleid(newRole);
            super.create(newUser);
            try {
                ProduceSender ps=new ProduceSender();
                ps.publish();
                
            } catch (JMSException ex) {
                Logger.getLogger(LoginFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NamingException ex) {
                Logger.getLogger(LoginFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(LoginFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else {
            throw new WebApplicationException(Response.status(400).entity("Password must contain capital letter and number and be 8 characters long <a href=\"https://localhost:8100/JerseyRestApplication/createUserForm.html\">Create User</a>").build());
           
        }
    }
    
    private boolean checkPassword(String password) {
        boolean hasUppercase = !password.equals(password.toLowerCase());
        boolean hasLowercase = !password.equals(password.toUpperCase());
        boolean isAtLeast8   = password.length() >= 8;//Checks for at least 8 characters
        boolean hasDigit   = false;
        for(int i = 0; i < password.length(); i++) {
            if(Character.isDigit(password.charAt(i))) {
                hasDigit = true;
                break;
            }
        }
    return hasUppercase && hasLowercase && isAtLeast8 && hasDigit;
    }
    
    
    @GET
    @Path("/users")
    @Consumes({"application/x-www-form-urlencoded", "application/xml", "application/json"})
    public String getUsers(){
        final String format = "%s\t\t%s\n";
        
        String answer=String.format(format, "Username", "Password");
        answer += "============================\n";
        
        List<Login> users = em.createNamedQuery("Login.findAll").getResultList();
        for(Login u:users){
            answer+=String.format(format, u.getUsername(),u.getPassword());
        }
        return answer;
    }
}

