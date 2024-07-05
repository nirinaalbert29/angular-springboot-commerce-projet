package com.alcode.ecommercespb.service;

import java.io.IOException;
import java.util.List;

import com.alcode.ecommercespb.Entity.Produit;
import com.alcode.ecommercespb.dto.ProduitDto;

public interface ProduitService {
	
	List<Produit> getAllProduit();
	
	Produit findProduitById(Long id);
	
	Produit saveProduit(ProduitDto produitDto) throws IOException;
	
	Produit updateProduit(Long id,ProduitDto produit) throws IOException;
	
	void deleteClient(Long id) throws Exception;
}
