package com.maxwell.taskmanager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.maxwell.taskmanager.domain.User;
import com.maxwell.taskmanager.domain.enums.UserRole;
import com.maxwell.taskmanager.dtos.UserCreateDTO;
import com.maxwell.taskmanager.dtos.UserDTO;
import com.maxwell.taskmanager.repositories.UserRepository;

@Service
public class UserService {

	
	@Autowired
	private UserRepository repo;
	
	/**
     * Returns all users stored in the database.
     *
     * @return list of users
     */
	public List<UserDTO> findAll() {
		List<User> list = repo.findAll();
		
		List<UserDTO> dtolist = list.stream()
				.map(UserDTO::new)
				.toList();
		
		return dtolist;
	}
	
	/**
	 * Inserts a new user into the database.
	 *
	 * @param user user object to be saved
	 * @return saved user
	 */
	public UserDTO newUser(UserCreateDTO dto) {
		User user = new User();
		user.setUsername(dto.getUserName());
		user.setEmail(dto.getEmail());
		user.setPassword(dto.getPassword());
		user.setRoles(UserRole.valueOf(dto.getRoles().toUpperCase()));;
		
		repo.save(user);
		return new UserDTO(user);
	}
}
