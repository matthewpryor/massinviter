package io.ctl.robotops.trello.client;

import io.ctl.robotops.trello.domain.ThrottleExceededException;
import io.ctl.robotops.trello.domain.User;

import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;

public class TrelloClient {

	private final String APPLICATION_KEY = "326967d3b61efb27c0181dd76ac4aa84";
	private final String TOKEN = "20575c367ce03ac62f8546b6955356d4edd2b3c5dca4dc8e0d84c4b0497c236a";

	private Client client;
	private String endpoint;
	
	public TrelloClient(String endpoint) {
		this.endpoint = endpoint;		
		client = ClientBuilder.newClient();
		client.register(JacksonFeature.class);
		LoggingFilter loggingFilter = new LoggingFilter(Logger.getLogger("TrelloClient"), true);
		client.register(loggingFilter);
	}
	
	public void inviteUser(User user, String organization) {
		Response response = client.target(endpoint).path("1").path("organizations").path(organization)
				.path("members")
				.queryParam("key", APPLICATION_KEY)
				.queryParam("token", TOKEN)
				.request(MediaType.APPLICATION_JSON).put(Entity.json(user));
		
		String responseBody = response.readEntity(String.class);

		if(response.getStatus() == 403 && "Member already invited\n".equals(responseBody)) {
			return;			
		} else if(response.getStatus() == 429) {
			throw new ThrottleExceededException();
		} else if(response.getStatus() >= 300) {
			throw new IllegalStateException("Received response code: " + response.getStatus() + " Response body: " + responseBody);
		}
	}

}
