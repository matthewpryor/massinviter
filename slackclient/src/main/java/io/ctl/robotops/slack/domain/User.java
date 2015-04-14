package io.ctl.robotops.slack.domain;

import lombok.Data;

import org.apache.commons.lang.StringUtils;

@Data
public class User {

	private final String name;
	private final String email;
	
	public String getFirstName() {
		String[] names = name.split(" ");
		
		if(names.length > 1) {
			return names[0];
		}
		
		return "";
	}
	
	public String getLastName() {
		String[] names = name.split(" ");
		
		if(names.length > 1) {
			return StringUtils.removeStart(name, names[0]).trim();
		}
		
        return names[0];
	}
}
