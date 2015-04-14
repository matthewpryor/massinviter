package io.ctl.robotops.massinviter;

import io.ctl.robotops.slack.domain.User;
import io.ctlts.robotops.config.AppConfig;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {

	public static void main(String[] args) throws Exception {
		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		MassInviter obj = (MassInviter) context.getBean("massInviter");
		List<User> userList = obj.parseCSV("test.csv");
		obj.sendInvites(userList, "robotops");
	}
	
}
