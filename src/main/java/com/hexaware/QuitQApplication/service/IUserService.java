package com.hexaware.QuitQApplication.service;

import com.hexaware.QuitQApplication.model.User;

public interface IUserService {
	public User saveUserDetails(User user);
	void deleteByUsername(String username);
}
