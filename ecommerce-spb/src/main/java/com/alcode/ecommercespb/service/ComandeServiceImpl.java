package com.alcode.ecommercespb.service;

import java.time.LocalDateTime; 
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.alcode.ecommercespb.Entity.Comande;
import com.alcode.ecommercespb.Entity.OurUsers;
import com.alcode.ecommercespb.Entity.Produit;
import com.alcode.ecommercespb.dto.ComandeDto;
import com.alcode.ecommercespb.dto.ReqRes;
import com.alcode.ecommercespb.exception.RessourceNotFoundException;
import com.alcode.ecommercespb.repository.ComandeRepository;
import com.alcode.ecommercespb.repository.UsersRepo;

@Service
public class ComandeServiceImpl implements ComandeService{

	@Autowired
	private ComandeRepository comandeRepository;
	
	@Autowired
    private UsersManagementService userService;

    @Autowired
    private ProduitServiceImpl produitService;
    
    @Autowired
    private UsersRepo userRepo;
	
	private final ModelMapper mapper = new ModelMapper();
	
	 

	
	@Override
	public List<Comande> getAllComande() {
		// TODO Auto-generated method stub
		return comandeRepository.findAll();
	}

	@Override
	public Comande getComandeById(Long id) {
		return comandeRepository.findById(id).orElseThrow(RessourceNotFoundException::new);
	}

	@Override
	 public List<Comande> getComandeByUserId(int userId) {
        return comandeRepository.findByUserId(userId);
    }

	@Override
	public Comande saveComande(ComandeDto comandeDto) {		
		
		Comande comande = new Comande();
		System.out.println("comande :"+comandeDto);
		System.out.println("user id : "+comandeDto.getUser_id()+",produit id :"+comandeDto.getProduit_id());
		
		
	    Produit produit = produitService.findProduitById(comandeDto.getProduit_id());
	    
/**
	 // Rechercher l'utilisateur dans la base de données
        Optional<OurUsers> optionalUser = userRepo.findById(userId);
        if (optionalUser.isPresent()) {
            OurUsers user = optionalUser.get();
            comande.setUser(user); // Définir l'utilisateur pour la commande
            comandeRepository.save(comande); // Sauvegarder la commande avec l'utilisateur
        } else {
            throw new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + userId);
        }
      
**/
	    
	    Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		ReqRes userDtoFromToken = userService.getMyInfo(email);
	    OurUsers user =userDtoFromToken.getOurUsers();

	    comande.setUser(user);
	    comande.setProduit(produit);
	    
	    comande.setStatut_com("En attente");
	        
		comande.setDate_com(LocalDateTime.now());
		comande.setQte_com(comandeDto.getQte_com());
		
		return comandeRepository.save(comande);
	
	}

	@Override
	public Comande updateComande(Long id, ComandeDto comandeDto) {
		Comande comandeDb = getComandeById(id);
		
		System.out.println("comande user:"+comandeDb.getUser().getId());
		
		/***
		comandeDb.setDate_com(comandeDto.getDate_com());
		comandeDb.setQte_com(comandeDto.getQte_com());
		comandeDb.setStatut_com(comandeDto.getStatut_com());
		***/
		
		Authentication authentication  = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		ReqRes userDtoFromToken = userService.getMyInfo(email);
	    OurUsers user =userDtoFromToken.getOurUsers();
		
	    Produit produit = produitService.findProduitById(comandeDto.getProduit_id());
		
	    mapper.map(comandeDto, comandeDb);
	    comandeDb.setProduit(produit);
	    comandeDb.setUser(user);
		comandeDb.setDate_com(LocalDateTime.now());
		
		return comandeRepository.save(comandeDb);
	}

	@Override
	public void deleteComande(Long id) throws Exception {
		if(!comandeRepository.existsById(id)) {
			throw new Exception("Comande avec id "+id+" n'existe pas");
		}
		comandeRepository.deleteById(id);
		
	}
	
	@Override
	public Comande approuverComande(Long comandeId) throws Exception {
		Comande comandeDb = getComandeById(comandeId);
		comandeDb.setStatut_com("Approuvé");
		return comandeRepository.save(comandeDb);
	}
	
	@Override
	public Comande RefuserComande(Long comandeId) throws Exception {
		Comande comandeDb = getComandeById(comandeId);
		comandeDb.setStatut_com("Réfusé");
		return comandeRepository.save(comandeDb);
	}

}
