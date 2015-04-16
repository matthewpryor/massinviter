package io.ctl.robotops.github.client;

import io.ctl.robotops.github.domain.ThrottleExceededException;
import io.ctl.robotops.github.domain.User;

import java.util.logging.Logger;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;

public class GithubClient {

	private final String TOKEN = "Bearer 65fd9c4661a4147988b5e3222a617d05e00accf0";

	private Client client;
	private String endpoint;
	
	public GithubClient(String endpoint) {
		this.endpoint = endpoint;		
		client = ClientBuilder.newClient();
		client.register(JacksonFeature.class);
		LoggingFilter loggingFilter = new LoggingFilter(Logger.getLogger("TrelloClient"), true);
		client.register(loggingFilter);
	}
	
	public void inviteUser(User user, String organization) {
		Response response = client.target(endpoint).path("orgs").path(organization).path("memberships")
				.path(user.getName())
				.request()
				.header("Accept", "application/vnd.github.moondragon+json")
				.header("Authorization", TOKEN)
				.put(Entity.json("{\"role\":\"member\"}"));
		
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
