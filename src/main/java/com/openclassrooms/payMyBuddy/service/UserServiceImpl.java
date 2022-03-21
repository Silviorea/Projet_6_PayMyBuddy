package com.openclassrooms.payMyBuddy.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.payMyBuddy.model.Roles;
import com.openclassrooms.payMyBuddy.model.User;
import com.openclassrooms.payMyBuddy.repository.UserRepository;
import com.openclassrooms.payMyBuddy.repository.dto.UserDTO;

@Service
public class UserServiceImpl implements UserService
{
	@Autowired
	private UserRepository userRepository;

// Lazy pour une injection parraisseuse afin d'éviter la boucle d'injection.
	@Lazy
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	// METHODE PERMETANT DE RECUPERER LE USER QUI S'EST LOG AVEC SON MAIL ET SON MDP
		public User findLogUser()
		{
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			String logUserEmail = auth.getName();
			User logUser = userRepository.findByEmail(logUserEmail);
			return logUser;
		}
	
	
	public User save(UserDTO userDTO)
	{
		User newUser = new User(userDTO.getFirstName(), 
				userDTO.getLastName(),
				userDTO.getEmail(), 
				passwordEncoder.encode(userDTO.getPassword()),
				Roles.USER
				);
		return userRepository.save(newUser);
	}
	

	
	
	public User udpateAccount(User userNewAccount, float amount)
	{
		userNewAccount.setAccount(userNewAccount.getAccount() + amount);
		return userRepository.save(userNewAccount);
	}
	
	
	public User addContact(User user, String email)
	{
		user.getContact().add(userRepository.findByEmail(email));
		return userRepository.save(user);
	}
	
	
	
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		User user = userRepository.findByEmail(username);
		
		if (user == null)
		{
			throw new UsernameNotFoundException("Invalid username or invalid Password");
		}
// On a besoin de la classe User de Spring security et non celle de l'Entité donc il faut 
//	lui indiquer que c'est celle la qu'on veut en indiquant le lien complet avec le package 
		return new org.springframework.security.core.userdetails.User
				(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRole()));
	}
	
// ? pour dire que la collection correspond à toutes les classes filles de GrantedAuthority
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Roles role)
	{
		List<SimpleGrantedAuthority> authorithies = new ArrayList<>();
	    authorithies.add(new SimpleGrantedAuthority(role.name()));
		return authorithies;
		
	}

	

	

	




	




	
	

}
