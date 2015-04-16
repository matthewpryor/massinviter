package io.ctl.robotops.github.client;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.putRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import io.ctl.robotops.github.domain.User;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import com.github.tomakehurst.wiremock.junit.WireMockClassRule;



public class GithubClient_UT {

	@ClassRule
	public static WireMockClassRule wireMockRule = new WireMockClassRule(0);
	@Rule
	public WireMockClassRule instanceRule = wireMockRule;

	private GithubClient githubClient = new GithubClient("http://localhost:" + wireMockRule.port());

	@Ignore
	@Test
	public void testInvite() {
		User user = new User("mpryor", "mapryor1991@yahoo.com");
		githubClient.inviteUser(user, "ctltest");
	}

	@Test
	public void inviteUser() throws InterruptedException {
		stubFor(put(urlMatching("/1/organizations/robotops/members.*"))
				.willReturn(aResponse()
						.withStatus(200)));
		
		githubClient.inviteUser(new User("testGuy","testGuy@test.com"), "robotops");
		
		verify(putRequestedFor(urlMatching("/1/organizations/robotops/members.*"))
				.withRequestBody(containing("testGuy")));
	}
}
