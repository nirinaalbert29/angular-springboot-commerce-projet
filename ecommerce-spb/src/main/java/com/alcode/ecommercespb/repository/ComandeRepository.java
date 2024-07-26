package com.alcode.ecommercespb.repository;

import java.util.List; 

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.alcode.ecommercespb.Entity.Comande;

@Repository
public interface ComandeRepository extends JpaRepository<Comande, Long>{
	
	List<Comande> findByUserId(int userId);
	
	/**
	@Query("UPDATE Comande c SET c.statut_com ='approuvé' WHERE c.id=:comandeId")
	public void approuverComande(@Param("comandeId") Long comandeId);
	**/
}
