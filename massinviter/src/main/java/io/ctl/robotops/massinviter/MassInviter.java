package io.ctl.robotops.massinviter;

import io.ctl.robotops.slack.client.SlackClient;
import io.ctl.robotops.slack.domain.User;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;

public class MassInviter {
	
	@Autowired
	private SlackClient trelloClient;
	
	public void sendInvites(List<User> userList, String organization) throws Exception {
		for(User user : userList) {
			trelloClient.inviteUser(user, organization);						
		}
	}
	
	public List<User> parseCSV(String fileName) throws Exception {
		InputStream is = MassInviter.class.getResourceAsStream("/" + fileName);
		Reader in = new InputStreamReader(is);
		
		List<User> users = new ArrayList<User>();
		
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
		for(CSVRecord record : records) {
			String fullName = record.get(0);
			String email = record.get(1);
			users.add(new User(fullName, email));
		}
		
		return users;
	}
	
}
