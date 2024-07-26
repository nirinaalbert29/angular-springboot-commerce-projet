package com.alcode.ecommercespb.Entity;

import java.time.LocalDateTime; 

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "comandes")
public class Comande {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int qte_com;
	
	private LocalDateTime date_com;
	
	private String statut_com;
	
	@ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
	private OurUsers user;
	
	@ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
	private Produit produit;
	
	public Comande() {
		// TODO Auto-generated constructor stub
	}

	public Comande(Long id, int qte_com, LocalDateTime date_com, String statut_com, OurUsers user, Produit produit) {
		super();
		this.id = id;
		this.qte_com = qte_com;
		this.date_com = date_com;
		this.statut_com = statut_com;
		this.user = user;
		this.produit = produit;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public OurUsers getUser() {
		return user;
	}

	public void setUser(OurUsers user) {
		this.user = user;
	}


	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}
	
	
	
}
