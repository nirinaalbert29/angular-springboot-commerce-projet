package com.alcode.ecommercespb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.alcode.ecommercespb.Entity.OurUsers;
import com.alcode.ecommercespb.dto.ReqRes;
import com.alcode.ecommercespb.service.EmailService;
import com.alcode.ecommercespb.service.UsersManagementService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UserManagementController {

	@Autowired
	private UsersManagementService usersManagementService;
	
	@Autowired
    private EmailService emailService;

    @GetMapping("/auth/test-email")
    public String testEmail() {
        emailService.sendSimpleMessage("nirinaalexalbert29@gmail.com", "Test Email", "This is a test email.");
        return "Email sent";
    }
    
	@PostMapping("/auth/register")
	public ResponseEntity<ReqRes> register(@RequestBody ReqRes reg){
		return ResponseEntity.ok(usersManagementService.register(reg));
	}
	
	@PostMapping("/auth/login")
	public ResponseEntity<ReqRes> login(@RequestBody ReqRes reg){
		return ResponseEntity.ok(usersManagementService.login(reg));
	}
	
	@PostMapping("/auth/refresh")
	public ResponseEntity<ReqRes> refreshToken(@RequestBody ReqRes req){
		return ResponseEntity.ok(usersManagementService.refreshToken(req));
	}
	
	@GetMapping("/admin/get-all-users")
	public ResponseEntity<ReqRes> getAllUsers(){
		return ResponseEntity.ok(usersManagementService.getAllUsers());
	}
	
	@GetMapping("/adminuser/get-user/{userId}")
	public ResponseEntity<ReqRes> getUserById(@PathVariable Integer userId){
		return ResponseEntity.ok(usersManagementService.getUserById(userId));
	}
	
	@PutMapping("/adminuser/update/{userId}")
	public ResponseEntity<ReqRes> updateUser(@PathVariable Integer userId,@RequestBody OurUsers reqres){
		return ResponseEntity.ok(usersManagementService.updateUser(userId, reqres));
	}
	
	@DeleteMapping("/admin/delete/{userId}")
	public ResponseEntity<ReqRes> deleteUser(@PathVariable Integer userId){
		return ResponseEntity.ok(usersManagementService.deleteUser(userId));
	}
	
	@GetMapping("/adminuser/get-profile")
	public ResponseEntity<ReqRes> getMyProfile(){
		Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		ReqRes response = usersManagementService.getMyInfo(email);
		return ResponseEntity.status(response.getStatutsCode()).body(response);
	}
	
	@GetMapping("/adminuser/get-user")
    public ResponseEntity<ReqRes> getUserByToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").replace("Bearer ", "");
        // Ici, vous pouvez appeler votre service pour récupérer l'utilisateur par le token JWT
        ReqRes response = usersManagementService.getUserByToken(token);
        return ResponseEntity.status(response.getStatutsCode()).body(response);
    }
	
}
