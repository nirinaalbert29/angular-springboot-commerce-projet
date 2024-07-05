package com.alcode.ecommercespb.service;

import java.util.List;

import com.alcode.ecommercespb.Entity.Client;
import com.alcode.ecommercespb.Entity.Comande;
import com.alcode.ecommercespb.Entity.OurUsers;
import com.alcode.ecommercespb.dto.ComandeDto;

public interface ComandeService {

	List<Comande> getAllComande();
	
	Comande getComandeById(Long id);
	
	List<Comande> getComandeByUserId(int userId);
	
	Comande saveComande(ComandeDto comandeDto);
	
	Comande updateComande(Long id,ComandeDto comandeDto);
	
	void deleteComande(Long id) throws Exception;

	Comande approuverComande(Long comandeId) throws Exception;

	Comande RefuserComande(Long comandeId) throws Exception;
	
	
}
