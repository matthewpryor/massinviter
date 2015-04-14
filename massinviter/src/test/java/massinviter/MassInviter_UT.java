package massinviter;


import java.util.Arrays;
import java.util.List;

import io.ctl.robotops.massinviter.MassInviter;
import io.ctl.robotops.slack.client.SlackClient;
import io.ctl.robotops.slack.domain.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MassInviter_UT {
	
	@Mock
	SlackClient trelloClient;

	@InjectMocks
	private MassInviter massInviter;
	
	@Test
	public void parseCsv_threeValidLines_threeUsersReturned() throws Exception {
		doNothing().when(trelloClient).inviteUser(any(User.class), any(String.class));
		List<User> users = massInviter.parseCSV("test.csv");
		assertEquals(3,users.size());
		assertEquals("test guy", users.get(0).getName());
		assertEquals("testGuy@test.com", users.get(0).getEmail());
		assertEquals("test guy3", users.get(2).getName());
		assertEquals("testGuy3@test.com", users.get(2).getEmail());
	}
	
	@Test
	public void sendInvites_twoUsers_twoInvitesSent() throws Exception { 
		ArgumentCaptor<User> user = ArgumentCaptor.forClass(User.class);
		massInviter.sendInvites(Arrays.asList(new User("newGuy", "newGuy@test.com"), new User("newGuy2","newGuy2@test.com")), "robotops");
		verify(trelloClient, times(2)).inviteUser(user.capture(),eq("robotops"));
		assertEquals(user.getAllValues().get(0).getName(), "newGuy");
		assertEquals(user.getAllValues().get(1).getName(), "newGuy2");
	}
	
}
