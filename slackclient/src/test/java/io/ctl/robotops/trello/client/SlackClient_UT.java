package io.ctl.robotops.trello.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import io.ctl.robotops.slack.client.SlackClient;
import io.ctl.robotops.slack.domain.User;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;

public class SlackClient_UT {

	@ClassRule
	public static WireMockClassRule wireMockRule = new WireMockClassRule(0);
	@Rule
	public WireMockClassRule instanceRule = wireMockRule;

	private SlackClient slackClient = new SlackClient("http://localhost:" + wireMockRule.port());
	//private SlackClient slackClient = new SlackClient("https://ctltest.slack.com");

	@Test
	@Ignore
	public void testInvite() {
		User user = new User("Taylor Blome", "taylor.blome@centurylink.com");
		slackClient.inviteUser(user, "C04CT5M08");
	}

	@Test
	public void inviteUser() throws InterruptedException {
		stubFor(post(urlMatching("/api/users.admin.invite.*"))
				.willReturn(aResponse()
						.withStatus(200)));
		
		slackClient.inviteUser(new User("test guy","testGuy@test.com"), "robotops");
		
		verify(postRequestedFor(urlMatching("/api/users.admin.invite.*"))
				.withRequestBody(containing("first_name=test"))
				.withRequestBody(containing("last_name=guy")));
	}
}
