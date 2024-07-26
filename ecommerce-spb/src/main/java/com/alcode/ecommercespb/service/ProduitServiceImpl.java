package com.alcode.ecommercespb.service;

import java.io.IOException; 
import java.util.List;

import com.alcode.ecommercespb.Entity.OurUsers;
import com.alcode.ecommercespb.dto.ReqRes;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alcode.ecommercespb.Entity.Produit;
import com.alcode.ecommercespb.dto.ProduitDto;
import com.alcode.ecommercespb.exception.RessourceNotFoundException;
import com.alcode.ecommercespb.repository.ProduitRepository;

@Service
public class ProduitServiceImpl implements ProduitService{
	
	@Autowired
	private ProduitRepository produitRepository;

	@Autowired
	private UsersManagementService userService;
	
	private final ModelMapper mapper = new ModelMapper();

	@Override
	public List<Produit> getAllProduit() {
		// TODO Auto-generated method stub
		
		return produitRepository.findAll();
	}

	@Override
	public Produit findProduitById(Long id) {
		Produit prod  = produitRepository.findById(id).orElseThrow(RessourceNotFoundException::new);
		return prod;
	}

	@Override
	public Produit saveProduit(ProduitDto produitdto) throws IOException {
		
		
		Produit prod = mapper.map(produitdto, Produit.class);
		if (produitdto.getImage_pro() != null) {
            prod.setImage_pro(produitdto.getImage_pro().getBytes());
        }
		return produitRepository.save(prod);
	}

	@Override
	public Produit updateProduit(Long id, ProduitDto produitdto) throws IOException{
		
		Produit produitDb = findProduitById(id);	
		
		mapper.map(produitdto, produitDb);
		
		if (produitdto.getImage_pro() != null) {
            try {
				produitDb.setImage_pro(produitdto.getImage_pro().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		
		return produitRepository.save(produitDb);
	}

	@Override
	public void deleteClient(Long id) throws Exception {
		if(!produitRepository.existsById(id)) {
			throw new Exception("prduit avec l'id "+id+" n'existe pas");
		}
		produitRepository.deleteById(id);
		
	}

	@Override
	public Produit likeProduit(Long produitId, int userId) throws Exception {
		Produit produit = findProduitById(produitId);

		ReqRes userReq = userService.getUserById(userId);
		OurUsers user = userReq.getOurUsers();

		if(produit.getLiked().contains(user)){
			produit.getLiked().remove(user);
		}
		else{
			produit.getLiked().add(user);
		}
		return produitRepository.save(produit);
	}

	@Override
	public boolean checkifLikedByCurrentUser(Long produitId) {
		ReqRes user = userService.getCurentUser();
		int userId = user.getOurUsers().getId();
		Produit prod = findProduitById(produitId);
		if(prod.getLiked().contains(user.getOurUsers())){
			return true;
		} else{
			return false;
		}
	}

}
