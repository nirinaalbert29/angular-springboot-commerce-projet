package com.alcode.ecommercespb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alcode.ecommercespb.Entity.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> {

}
