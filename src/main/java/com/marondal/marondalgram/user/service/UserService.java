package com.marondal.marondalgram.user.service;

import org.springframework.stereotype.Service;

import com.marondal.marondalgram.common.hash.MD5HashingEncoder;
import com.marondal.marondalgram.user.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public int addUser(
			String loginId
			, String password
			, String name
			, String email) {
		
		String encryptPassword = MD5HashingEncoder.encode(password);
		
		return userRepository.insertUser(loginId, encryptPassword, name, email);
		
	}

}
