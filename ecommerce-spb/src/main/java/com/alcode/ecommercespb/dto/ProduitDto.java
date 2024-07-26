package com.alcode.ecommercespb.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;

public class ProduitDto {
	
	@NotBlank(message = "le code produit est obligatoire")
	private String code_pro;
	
	@NotBlank(message = "le nom de produit est obligatoire")
	private String nom_pro;
	
	@NotBlank(message = "le categorie de produit est obligatoire")
	private String categorie;
	
	@NotBlank(message = "la description est obligatoire")
	private String description;
	
	@NotBlank(message = "le prix de produit est obligatoire")
	private double prix_pro;
	
	@NotBlank(message = "l'image est obligatoire")
	@Lob
	private MultipartFile  image_pro;
	
	public ProduitDto() {
		// TODO Auto-generated constructor stub
	}

	public ProduitDto(@NotBlank(message = "le code produit est obligatoire") String code_pro,
			@NotBlank(message = "le nom de produit est obligatoire") String nom_pro,
			@NotBlank(message = "le categorie de produit est obligatoire") String categorie,
			@NotBlank(message = "la description est obligatoire") String description,
			@NotBlank(message = "le prix de produit est obligatoire") double prix_pro,
			@NotBlank(message = "l'image est obligatoire") MultipartFile image_pro) {
		super();
		this.code_pro = code_pro;
		this.nom_pro = nom_pro;
		this.categorie = categorie;
		this.description = description;
		this.prix_pro = prix_pro;
		this.image_pro = image_pro;
	}

	public String getCode_pro() {
		return code_pro;
	}

	public void setCode_pro(String code_pro) {
		this.code_pro = code_pro;
	}

	public String getNom_pro() {
		return nom_pro;
	}

	public void setNom_pro(String nom_pro) {
		this.nom_pro = nom_pro;
	}

	public String getCategorie() {
		return categorie;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrix_pro() {
		return prix_pro;
	}

	public void setPrix_pro(double prix_pro) {
		this.prix_pro = prix_pro;
	}

	public MultipartFile getImage_pro() {
		return image_pro;
	}

	public void setImage_pro(MultipartFile image_pro) {
		this.image_pro = image_pro;
	}
	
	
}
