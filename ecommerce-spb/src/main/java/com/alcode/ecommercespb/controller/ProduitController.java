package com.alcode.ecommercespb.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.alcode.ecommercespb.Entity.Produit;
import com.alcode.ecommercespb.dto.ProduitDto;
import com.alcode.ecommercespb.service.ProduitServiceImpl;

@RestController
@CrossOrigin
public class ProduitController {
	
	@Autowired
	private ProduitServiceImpl produitServiceImpl;
	
	@GetMapping("adminuser/produit")
	public List<Produit> getAllProduits(){
		return produitServiceImpl.getAllProduit();
	}
	
	@GetMapping("adminuser/produit/{id}")
	public Produit getProduitById(@PathVariable Long id) {
		return produitServiceImpl.findProduitById(id);
	}
	
	@PostMapping("admin/produit")
    public ResponseEntity<?> createProduit(@ModelAttribute ProduitDto produitDto) {
        try {
            Produit produit = produitServiceImpl.saveProduit(produitDto);
            return ResponseEntity.ok(produit);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Invalid data: " + e.getMessage());
        }
    }
	
	@PutMapping("admin/produit/{id}")
    public ResponseEntity<?> updateProduit(@PathVariable Long id, @ModelAttribute ProduitDto produitDto) {
        try {
            Produit produit = produitServiceImpl.updateProduit(id, produitDto);
            return ResponseEntity.ok(produit);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Invalid data: " + e.getMessage());
        }
    }
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("admin/produit/{id}")
	public void deleteProduit(@PathVariable Long id) throws Exception {
		produitServiceImpl.deleteClient(id);
	}
	
	
	
	

}
