package jp.co.ubinet.reserve.service;

import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.ubinet.reserve.dto.UserDto;
import jp.co.ubinet.reserve.entity.User;
import jp.co.ubinet.reserve.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service

public class CustomUserDetailsService implements UserDetailsService{
	
	@Resource
	private UserRepository userRepository;
	
	
	@Override
    @Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		  Optional<User> result = userRepository.findByUserEmail(username);
	        if (!result.isPresent()) {
	            throw new UsernameNotFoundException("아이디와 비밀번호를 확인해 주세요");
	        }


	        User user = result.get();

	        List<SimpleGrantedAuthority> roleAuthoritys = new ArrayList<>();
	        roleAuthoritys.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getType()));

	        UserDto userDto = new UserDto(
	                  user.getUserEmail()
	                , user.getUserPassword()
	                , user.getUserName()
	                , roleAuthoritys
	        );

	        return userDto;
	
	}
}
