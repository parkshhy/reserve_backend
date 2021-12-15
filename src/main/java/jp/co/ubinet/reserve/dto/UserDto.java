package jp.co.ubinet.reserve.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Getter
@Setter
@ToString
public class UserDto extends User{

	private String userId;
	private String name;

	   public UserDto(String username, String password, String name, Collection<? extends GrantedAuthority> authorities) {
		   	super(username, password, authorities);
	        this.userId = username;
	        this.name = name;
	    }
}
