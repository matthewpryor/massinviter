package io.ctlts.robotops.config;

import io.ctl.robotops.massinviter.MassInviter;
import io.ctl.robotops.slack.client.SlackClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	
	@Bean
	public MassInviter massInviter() {
		return new MassInviter();
	}
	
	@Bean
	public SlackClient trelloClient() {
		return new SlackClient("https://api.trello.com"); 
	}
}