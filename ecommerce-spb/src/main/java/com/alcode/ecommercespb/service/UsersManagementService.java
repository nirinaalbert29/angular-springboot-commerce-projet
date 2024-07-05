package com.alcode.ecommercespb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alcode.ecommercespb.Entity.OurUsers;
import com.alcode.ecommercespb.dto.ReqRes;
import com.alcode.ecommercespb.repository.UsersRepo;

@Service
public class UsersManagementService {
	
	@Autowired
	private UsersRepo usersRepo;
	
	@Autowired
	private JWTUtils jwtUtils;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailService emailService;
	
	public ReqRes register(ReqRes registrationRequest) {
		ReqRes resp = new ReqRes();
		
		try {
			
			OurUsers ourUser = new OurUsers();
			ourUser.setEmail(registrationRequest.getEmail());
			ourUser.setCity(registrationRequest.getCity());
			ourUser.setRole(registrationRequest.getRole());
			ourUser.setName(registrationRequest.getName());
			ourUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
			
			OurUsers ourUserResult = usersRepo.save(ourUser);
			if(ourUserResult.getId() > 0) {
				resp.setOurUsers(ourUserResult);
				resp.setMessage("User saved succesfully");
				resp.setStatutsCode(200);
				
				// Envoi d'un email de confirmation
				emailService.sendSimpleMessage(registrationRequest.getEmail(), "Email verification", "This is a verificatio email from E-commerce app");
			}
			
		} catch (Exception e) {
			resp.setStatutsCode(500);
			resp.setError(e.getMessage());
		}
		
		return resp;
	}
	
	public ReqRes login(ReqRes loginRequest) {
		ReqRes response = new ReqRes();
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), 
					loginRequest.getPassword()));
			var user = usersRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
			var jwt = jwtUtils.generateToken(user);
			var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
			response.setStatutsCode(200);
			response.setToken(jwt);
			response.setRole(user.getRole());
			response.setRefreshToken(refreshToken);
			response.setExpirationTime("24Hrs");
			response.setMessage("Successfully logged in");
		} catch (Exception e) {
			response.setStatutsCode(500);
			response.setMessage(e.getMessage());
		}
		
		return response;
	}
	
	public ReqRes refreshToken(ReqRes refreshTokenRequest) {
		ReqRes response = new ReqRes();
		
		try {
			String ourEmail = jwtUtils.extractUsername(refreshTokenRequest.getToken());
			OurUsers users = usersRepo.findByEmail(ourEmail).orElseThrow();
			if(jwtUtils.isTokenValid(refreshTokenRequest.getToken(), users)) {
				var jwt  = jwtUtils.generateToken(users);
				response.setStatutsCode(200);
				response.setToken(jwt);
				response.setRefreshToken(refreshTokenRequest.getToken());
				response.setExpirationTime("24Hrs");
				response.setMessage("successfully refresh token");
			}
			response.setStatutsCode(200);
			return response;
			
		} catch (Exception e) {
			response.setStatutsCode(500);
			response.setError(e.getMessage());
			return response;
		}
	}
	
	public ReqRes getAllUsers() {
		ReqRes reqRes = new ReqRes();
		
		try {
			List<OurUsers> result = usersRepo.findAll();
			if(!result.isEmpty()) {
				reqRes.setOurUsersList(result);
				reqRes.setStatutsCode(200);
				reqRes.setMessage("successful");
			}
			else {
				reqRes.setStatutsCode(404);
				reqRes.setMessage("No users found");
			}
			return reqRes;
		} catch (Exception e) {
			reqRes.setStatutsCode(500);
			reqRes.setError(e.getMessage());
			return reqRes;
		}
	}
	
	public ReqRes getUserById(Integer id) {
		ReqRes reqRes = new ReqRes();
		
		try {
			OurUsers usersById  = usersRepo.findById(id).orElseThrow(()->new RuntimeException("User not found"));
			reqRes.setOurUsers(usersById);
			reqRes.setStatutsCode(200);
			reqRes.setMessage("User with id '" + id +"' found successfully");
		} catch (Exception e) {
			reqRes.setStatutsCode(500);
			reqRes.setError("Error occurred: " +e.getMessage());
		}
		return reqRes;
	}
	
	public ReqRes deleteUser(Integer userId) {
		ReqRes reqRes = new ReqRes();
		try {
			Optional<OurUsers> userOptional = usersRepo.findById(userId);
			if(userOptional.isPresent()) {
				usersRepo.deleteById(userId);
				reqRes.setStatutsCode(200);
				reqRes.setMessage("User delete successfully");
			}
			else {
				reqRes.setStatutsCode(404);
				reqRes.setMessage("User not found for deletion");
			}
				
		} catch (Exception e) {
			reqRes.setStatutsCode(500);
			reqRes.setError("Error occurred for deleting user: " +e.getMessage());
		
		}
		return reqRes;
	}
	
	public ReqRes updateUser(Integer userId,OurUsers updatedUser) {
		ReqRes reqRes  = new ReqRes();
		
		try {
			Optional<OurUsers> userOptional = usersRepo.findById(userId);
			if(userOptional.isPresent()) {
				OurUsers existingUser = userOptional.get();
				existingUser.setEmail(updatedUser.getEmail());
				existingUser.setName(updatedUser.getName());
				existingUser.setCity(updatedUser.getCity());
				existingUser.setRole(updatedUser.getRole());
				
				//Check if password is present in the request
				if(updatedUser.getPassword() !=null && !updatedUser.getPassword().isEmpty()) {
					//encode the password and update it
					existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
					
				}
				
				OurUsers savedUser = usersRepo.save(existingUser);
				reqRes.setOurUsers(savedUser);
				reqRes.setStatutsCode(200);
				reqRes.setMessage("User updated successfully");
				
				
			}else {
				reqRes.setStatutsCode(404);
				reqRes.setMessage("User not found for update");
			}
		} catch (Exception e) {
			reqRes.setStatutsCode(500);
			reqRes.setError("Error occurred for updating user: " +e.getMessage());
		}
		
		return reqRes;
	}
	
	public ReqRes getMyInfo(String email) {
		ReqRes reqRes = new ReqRes();
		try {
			Optional<OurUsers> userOptional = usersRepo.findByEmail(email);
			if(userOptional.isPresent()) {
				reqRes.setOurUsers(userOptional.get());
				reqRes.setStatutsCode(200);
				reqRes.setMessage("Successful");
			}else {
				reqRes.setStatutsCode(404);
				reqRes.setMessage("User not found get more info");
				
			}
		} catch (Exception e) {
			reqRes.setStatutsCode(500);
			reqRes.setError("Error occurred for getting user info: " +e.getMessage());
		
		}
		return reqRes;
	}
	
	
	
	public ReqRes getUserByToken(String token) {
        ReqRes response = new ReqRes();
        try {
            String userEmail = jwtUtils.extractUsername(token);
            Optional<OurUsers> userOptional = usersRepo.findByEmail(userEmail);
            if (userOptional.isPresent()) {
                response.setOurUsers(userOptional.get());
                response.setStatutsCode(200);
                response.setMessage("User retrieved successfully by token");
            } else {
                response.setStatutsCode(404);
                response.setMessage("User not found for token");
            }
        } catch (Exception e) {
            response.setStatutsCode(500);
            response.setError("Error occurred for getting user by token: " + e.getMessage());
        }
        return response;
    }
}
