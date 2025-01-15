package com.hexaware.QuitQApplication.serviceImpl;

import org.springframework.stereotype.Service;

import com.hexaware.QuitQApplication.model.User;
import com.hexaware.QuitQApplication.repository.UserRepository;
import com.hexaware.QuitQApplication.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	public UserRepository userRepo;

	public UserServiceImpl(UserRepository userRepo) {
		super();
		this.userRepo = userRepo;
	}

	@Override
	public User saveUserDetails(User user) {
		return this.userRepo.save(user);
	}
	
	@Override
	public void deleteByUsername(String username) {
		userRepo.deleteById(userRepo.findByUsername(username).get().getUserId());
	}

}
