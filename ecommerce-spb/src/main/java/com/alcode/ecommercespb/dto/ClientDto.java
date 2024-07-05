package com.alcode.ecommercespb.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public class ClientDto {

	@NotBlank(message = "le nom est obligatoire")
	private String nom;
	
	@NotBlank(message = "l'adresse est obligatoire")
	private String adresse;
	
	@NotBlank(message = "l'eamil est obligatoire")
	@Email(message = "adresse eamil invalide")
	private String email;
	
	@NotBlank(message = "le date de naisance est obligatoire")
	private String date_naiss;
	
	@NotBlank(message = "le cin est obligatoire")
	private Long cin;
	
	public ClientDto() {
		// TODO Auto-generated constructor stub
	}

	public ClientDto(@NotBlank(message = "le nom est obligatoire") String nom,
			@NotBlank(message = "l'adresse est obligatoire") String adresse,
			@NotBlank(message = "l'eamil est obligatoire") @Email(message = "adresse eamil invalide") String email,
			@NotBlank(message = "le date de naisance est obligatoire") String date_naiss,
			@NotBlank(message = "le cin est obligatoire") Long cin) {
		super();
		this.nom = nom;
		this.adresse = adresse;
		this.email = email;
		this.date_naiss = date_naiss;
		this.cin = cin;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDate_naiss() {
		return date_naiss;
	}

	public void setDate_naiss(String date_naiss) {
		this.date_naiss = date_naiss;
	}

	public Long getCin() {
		return cin;
	}

	public void setCin(Long cin) {
		this.cin = cin;
	}
	
	
	
	
}
