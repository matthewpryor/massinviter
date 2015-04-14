package io.ctl.robotops.trello.domain;

import static org.junit.Assert.assertEquals;
import io.ctl.robotops.slack.domain.User;

import org.junit.Test;

public class User_UT {

	@Test
	public void getNames_UserWithOneName_NameIsBlank() {
		User oneName = new User("bob", "bob@domain.x");
		assertEquals("", oneName.getFirstName());
		assertEquals("bob", oneName.getLastName());
	}
	
	@Test
	public void getNames_UserWithFirstAndLastName_Success() {
		User oneName = new User("bob smith", "bob@domain.x");
		assertEquals("bob", oneName.getFirstName());
		assertEquals("smith", oneName.getLastName());
	}
	
	@Test
	public void getNames_MoreThanTwoNames_Success() {
		User oneName = new User("bob smith xyz", "bob@domain.x");
		assertEquals("bob", oneName.getFirstName());
		assertEquals("smith xyz", oneName.getLastName());
	}
}
