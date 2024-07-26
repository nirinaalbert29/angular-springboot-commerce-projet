package com.alcode.ecommercespb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alcode.ecommercespb.Entity.Comande;
import com.alcode.ecommercespb.dto.ComandeDto;
import com.alcode.ecommercespb.dto.ReqRes;
import com.alcode.ecommercespb.service.ComandeServiceImpl;
import com.alcode.ecommercespb.service.UsersManagementService;

@RestController
@CrossOrigin
public class ComandeController {
	
	@Autowired
	private ComandeServiceImpl comandeService;
	
	@Autowired
	private UsersManagementService usersManagementService;

	@GetMapping("admin/comande")
	public List<Comande> getAllComande(){
		return comandeService.getAllComande();
	}
	
	@GetMapping("adminuser/comande/{id}")
	public Comande getComandeById(@PathVariable Long id) {
		return comandeService.getComandeById(id);
	}
	
	@GetMapping("user/comande")
	public List<Comande> getComandeByUserId() {
		Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		ReqRes response = usersManagementService.getMyInfo(email);
		int userId = response.getOurUsers().getId();
		return comandeService.getComandeByUserId(userId);
	}
	
	@PostMapping("user/comande")
	public Comande saveComande(@RequestBody ComandeDto comandeDto){
		Comande com = comandeService.saveComande(comandeDto);
		
		return com;
	}
	
	@PutMapping("adminuser/comande/{id}")
	public Comande updateComande(@PathVariable Long id,@RequestBody ComandeDto comandeDto) {
		return comandeService.updateComande(id, comandeDto);
	}
	
	@DeleteMapping("user/comande/{id}")
	public void deleteComandeById(@PathVariable Long id) throws Exception {
		comandeService.deleteComande(id);
	}
	
	@PutMapping("admin/comande/approuver/{id}")
	public Comande approuverCom(@PathVariable Long id) throws Exception {
		return comandeService.approuverComande(id);
	}
	@PutMapping("admin/comande/refuser/{id}")
	public Comande refuserCom(@PathVariable Long id) throws Exception {
		return comandeService.RefuserComande(id);
	}
}
