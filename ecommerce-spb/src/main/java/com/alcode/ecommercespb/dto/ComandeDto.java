package com.alcode.ecommercespb.dto;

import java.time.LocalDateTime;

import com.alcode.ecommercespb.Entity.Client;
import com.alcode.ecommercespb.Entity.Produit;

import jakarta.validation.constraints.NotBlank;


public class ComandeDto {

	@NotBlank(message = "le quantité comandé est obligatoire")
	private int qte_com;
	
	@NotBlank(message = "le date de comande est obligatoire")
	private LocalDateTime date_com;
	
	@NotBlank(message = "le statut de comande est obligatoire")
	private String statut_com;
	
	@NotBlank(message = "l'identifiant de l'user est obligatoire")
	private int user_id;
	
	@NotBlank(message = "l'identifiant de produit est obligatoire")
	private Long produit_id;
	
	
	public ComandeDto() {
		// TODO Auto-generated constructor stub
	}


	public ComandeDto(@NotBlank(message = "le quantité comandé est obligatoire") int qte_com,
			@NotBlank(message = "le date de comande est obligatoire") LocalDateTime date_com,
			@NotBlank(message = "le statut de comande est obligatoire") String statut_com,
			@NotBlank(message = "l'identifiant de client est obligatoire") int user_id,
			@NotBlank(message = "l'identifiant de produit est obligatoire") Long produit_id) {
		super();
		this.qte_com = qte_com;
		this.date_com = date_com;
		this.statut_com = statut_com;
		this.user_id = user_id;
		this.produit_id = produit_id;
	}
	

	public int getQte_com() {
		return qte_com;
	}


	public void setQte_com(int qte_com) {
		this.qte_com = qte_com;
	}


	public LocalDateTime getDate_com() {
		return date_com;
	}


	public void setDate_com(LocalDateTime date_com) {
		this.date_com = date_com;
	}


	public String getStatut_com() {
		return statut_com;
	}


	public void setStatut_com(String statut_com) {
		this.statut_com = statut_com;
	}


	public int getUser_id() {
		return user_id;
	}


	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}


	public Long getProduit_id() {
		return produit_id;
	}

	public void setProduit_id(Long produit_id) {
		this.produit_id = produit_id;
	}


	
	
	
}
