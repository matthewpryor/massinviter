package io.ctl.robotops.slack.client;

import io.ctl.robotops.slack.domain.User;

import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;

public class SlackClient {

	private final String TOKEN = "xoxs-4435190920-4435190940-4457476191-38eadff794";

	private Client client;
	private String endpoint;
	
	public SlackClient(String endpoint) {
		this.endpoint = endpoint;		
		client = ClientBuilder.newClient();
		client.register(JacksonFeature.class);
		LoggingFilter loggingFilter = new LoggingFilter(Logger.getLogger("SlackClient"), true);
		client.register(loggingFilter);
	}
	
	public void inviteUser(User user, String organization) {
		MultivaluedHashMap<String, String> formData = new MultivaluedHashMap<String, String>();
		
		formData.putSingle("email", user.getEmail());
		formData.putSingle("channels", organization);
		formData.putSingle("first_name", user.getFirstName());
		formData.putSingle("last_name", user.getLastName());
		formData.putSingle("token", TOKEN);
		formData.putSingle("set_active", "true");
		formData.putSingle("_attempts", "1"); 
		
		Response response = client.target(endpoint)
				.path("api")
				.path("users.admin.invite")
				.queryParam("t", System.currentTimeMillis() / 1000)
				.request(MediaType.APPLICATION_FORM_URLENCODED)
				.post(Entity.form(formData));
		
		String responseBody = response.readEntity(String.class);

		if(response.getStatus() >= 300) {
			throw new IllegalStateException("Received response code: " + response.getStatus() + " Response body: " + responseBody);
		}
	}
}
