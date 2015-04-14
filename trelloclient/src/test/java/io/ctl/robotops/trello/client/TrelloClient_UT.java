package io.ctl.robotops.trello.client;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import io.ctl.robotops.trello.domain.User;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;



public class TrelloClient_UT {

	@ClassRule
	public static WireMockClassRule wireMockRule = new WireMockClassRule(0);
	@Rule
	public WireMockClassRule instanceRule = wireMockRule;

	private TrelloClient trelloClient = new TrelloClient("http://localhost:" + wireMockRule.port());

	@Ignore
	@Test
	public void testInvite() {
		User user = new User("Matthew Pryor1", "mapryor1991@yahoo.com");
		trelloClient.inviteUser(user, "robotops");
	}

	@Test
	public void inviteUser() throws InterruptedException {
		stubFor(put(urlMatching("/1/organizations/robotops/members.*"))
				.willReturn(aResponse()
						.withStatus(200)));
		
		trelloClient.inviteUser(new User("testGuy","testGuy@test.com"), "robotops");
		
		verify(putRequestedFor(urlMatching("/1/organizations/robotops/members.*"))
				.withRequestBody(containing("testGuy")));
	}
}
