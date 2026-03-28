package com.maxwell.taskmanager.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * Returns all users stored in the database.
	 *
	 * @return list of users
	 */
	public List<UserDTO> findAll() {
		List<User> list = repo.findAll();

		List<UserDTO> dtolist = list.stream().map(UserDTO::new).toList();

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

		// 🔐 encrypt password before saving
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRoles(dto.getRoles() != null ? UserRole.valueOf(dto.getRoles().toUpperCase()) : UserRole.USER);

		repo.save(user);
		return new UserDTO(user);
	}

	public UserDTO findById(String id) {

		var auth = SecurityContextHolder.getContext().getAuthentication();

		String email = auth.getName();
		String role = auth.getAuthorities().iterator().next().getAuthority();

		User loggedUser = repo.findByEmail(email).orElseThrow(() -> new RuntimeException("Logged user not found"));

		if (role.equals("ROLE_ADMIN")) {
			User target = repo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
			return new UserDTO(target);
		}

		if (!loggedUser.getId().equals(id)) {
			throw new RuntimeException("Access denied");
		}

		User target = repo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

		return new UserDTO(target);
	}
}
