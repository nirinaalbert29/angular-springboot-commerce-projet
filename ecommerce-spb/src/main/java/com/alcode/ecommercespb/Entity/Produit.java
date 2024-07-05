package com.alcode.ecommercespb.Entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity; 
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "produits")
public class Produit {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String code_pro;
	
	private String nom_pro;
	
	private String categorie;
	
	private String description;
	
	private double prix_pro;
	
	@Lob
	@Column(length = 1000000)
	private byte[] image_pro;
	
	
	public Produit() {
		// TODO Auto-generated constructor stub
	}
	
	public Produit(Long id, String code_pro, String nom_pro, String categorie, String description, double prix_pro,
			byte[] image_pro) {
		super();
		this.id = id;
		this.code_pro = code_pro;
		this.nom_pro = nom_pro;
		this.categorie = categorie;
		this.description = description;
		this.prix_pro = prix_pro;
		this.image_pro = image_pro;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public byte[] getImage_pro() {
		return image_pro;
	}

	public void setImage_pro(byte[] image_pro) {
		this.image_pro = image_pro;
	}
	
	
	
}
