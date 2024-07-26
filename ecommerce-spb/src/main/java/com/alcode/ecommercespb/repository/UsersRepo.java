package com.alcode.ecommercespb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alcode.ecommercespb.Entity.OurUsers;

@Repository
public interface UsersRepo extends JpaRepository<OurUsers, Integer>{

	Optional<OurUsers> findByEmail(String email);
}
