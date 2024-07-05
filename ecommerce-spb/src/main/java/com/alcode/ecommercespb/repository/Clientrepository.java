package com.alcode.ecommercespb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alcode.ecommercespb.Entity.Client;

@Repository
public interface Clientrepository extends JpaRepository<Client, Long>{
	Client findByEmail(String email);
}
