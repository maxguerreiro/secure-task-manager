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
import com.maxwell.taskmanager.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {

    private final TaskService taskService;

	@Autowired
	private UserRepository repo;

	@Autowired
	private PasswordEncoder passwordEncoder;

    UserService(TaskService taskService) {
        this.taskService = taskService;
    }

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

	
	/**
	 * Retrieves a user by its ID with access control validation.
	 *
	 * This method ensures that:
	 * - Users with ADMIN role can access any user data.
	 * - Regular users (USER role) can only access their own data.
	 *
	 * The currently authenticated user is obtained from the SecurityContext,
	 * and their identity is validated against the requested user ID.
	 *
	 * @param id the ID of the user to be retrieved
	 * @return a UserDTO containing the user's public data
	 *
	 * @throws RuntimeException if:
	 * - the authenticated user is not found in the database
	 * - the target user does not exist
	 * - the authenticated user does not have permission to access the requested data
	 */
	public UserDTO findById(String id) {

		var auth = SecurityContextHolder.getContext().getAuthentication();

		String email = auth.getName();
		String role = auth.getAuthorities().iterator().next().getAuthority();

		User loggedUser = repo.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(id));

		if (role.equals("ROLE_ADMIN")) {
			User target = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
			return new UserDTO(target);
		}

		if (!loggedUser.getId().equals(id)) {
			throw new RuntimeException("Access denied");
		}

		User target = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));

		return new UserDTO(target);
	}

	public UserDTO update(String id, UserCreateDTO dto) {
		
		var auth = SecurityContextHolder.getContext().getAuthentication();
		
		String email = auth.getName();
		String role = auth.getAuthorities().iterator().next().getAuthority();
		
		User loggedUser = repo.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User not found"));
		
		User target = repo.findById(id)
				.orElseThrow(() -> new RuntimeException("Target not found"));
		
		boolean isAdmin = role.equals("ROLE_ADMIN");
		boolean isOwner = loggedUser.getId().equals(id);
		
		if (!isAdmin && !isOwner) {
			throw new RuntimeException("Access dinied");
		}
		
		target.setUsername(dto.getUserName());
		target.setEmail(dto.getEmail());
		
		if (dto.getPassword() != null) {
			target.setPassword(passwordEncoder.encode(dto.getPassword()));
		}
		
		return new UserDTO(repo.save(target));
	}
	
	public void delete(String id) {
		var auth = SecurityContextHolder.getContext().getAuthentication();
		
		String email = auth.getName();
		String role = auth.getAuthorities().iterator().next().getAuthority();
		
		System.out.printf("EMAIL: " + email);
		System.out.println("ROLE: " + role);
		
		User loggedUser = repo.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User not found"));
		User target = repo.findById(id)
				.orElseThrow(() -> new RuntimeException("Target not found"));
		
		boolean isAdmin = role.equals("ROLE_ADMIN");
		boolean isOwner = loggedUser.getId().equals(id);
		
		if (!isAdmin && !isOwner) {
			throw new RuntimeException("Access denied");
		}
		
		repo.delete(target);
	}
}
