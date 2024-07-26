import { Component, OnInit, inject } from '@angular/core';
import { ProduitService } from './../services/produit/produit.service';
import { Produit } from '../model/produit.interface';
import { Router, RouterModule } from '@angular/router';
import { Comande } from '../model/comande.interface';
import { ComandeService } from './../services/comande/comande.service';
import Swal from 'sweetalert2';
import { UsersService } from '../authentification/users.service';
import { CommonModule } from '@angular/common';


import { FormsModule } from '@angular/forms';
import { MatIconModule } from '@angular/material/icon'; 
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatButtonModule } from '@angular/material/button';



@Component({
  selector: 'app-produit-list',
  standalone: true,
  imports: [
    RouterModule,
    CommonModule,
    FormsModule,
    MatIconModule,
    MatCardModule,
    MatInputModule,
    MatCheckboxModule,
    MatButtonModule
  ],
  templateUrl: './produit-list.component.html',
  styleUrl: './produit-list.component.scss'
})
export default class ProduitListComponent implements OnInit{

  private produitService = inject(ProduitService);
  private comandeService = inject(ComandeService);

  private router = inject(Router);

  produits:Produit[]=[];
  produit:Produit;
  errors: string[] = [];
  comande : Comande;

  isAuthenticated:boolean = false;
  isAdmin:boolean = false;
  isUser:boolean = false;

  // comande: { id: number, qte_com: number, user_id: number, produit_id: number, statut_com: string };
  private readonly userService: UsersService;

  constructor(userService: UsersService) {
    this.userService = userService;
    this.comande = {
      id: 0,
      qte_com: 0,
      user_id: 0,
      produit_id: 0,
      statut_com: ""
    };

    this.produit = {
      id:0,
      categorie:"",
      code_pro:"",
      description:"",
      isLikedByCurrentUser:false,
      nom_pro:"",
      prix_pro:0,
      image_pro:""
    }
  }

  ngOnInit(): void {
    this.loadProduits();
    this.isAuthenticated = this.userService.isAuthenticated();
    this.isAdmin = this.userService.isAdmin();
    this.isUser = this.userService.isUser();

  }

  loadProduits() {
    const token: any = localStorage.getItem('token');
    if (token) {
      this.produitService.list(token)
        .subscribe(produits => {
          this.produits = produits;
          this.produits.forEach(produit => {
            this.produitService.etatLike(produit.id, token).subscribe(res => {
              produit.isLikedByCurrentUser = res;
              console.log(produit.isLikedByCurrentUser);
            });
          });
        });
    } else {
      console.log("token not found for view product list");
    }
  }
  

  deleteProduitById(id:number){
    const token:any = localStorage.getItem('token');
    this.produitService.delete(id,token)
    .subscribe(()=>{
      this.loadProduits();
    })
  }

    isFavoriteClicked = false; // Variable pour suivre l'état du clic sur l'icône favorite

    adorer(produitId: number) {
      const token:any = localStorage.getItem('token');
      if(token){
        return this.produitService.adorer(produitId,token)
        .subscribe(()=>{
          this.loadProduits();
          this.loadProduits();
          // this.isFavoriteClicked = !this.isFavoriteClicked; 
        })
      }else{
        return this.isFavoriteClicked = this.isFavoriteClicked;
      }
      
    }

    acheter(produitId: number, userId: number) {
      Swal.fire({
        title: 'Entrez la quantité',
        input: 'number',
        inputAttributes: {
          min: '1',
          step: '1'
        },
        showCancelButton: true,
        confirmButtonText: 'Acheter',
        cancelButtonText: 'Annuler'
      }).then((result) => {
        if (result.isConfirmed) {
          const quantite = result.value;
          if (quantite && quantite > 0) {
            // Appelez votre fonction d'achat ici avec la quantité saisie
            this.submitAchat(produitId, userId, quantite);
          } else {
            Swal.fire('Erreur', 'Veuillez entrer une quantité valide', 'error');
          }
        }
      });
    }

    submitAchat(prod: number, cli: number, qte: number) {
      this.comande.user_id=cli;
      this.comande.produit_id=prod;
      this.comande.qte_com=qte;
      const token:any = localStorage.getItem("token");

      console.log("comande",this.comande);

      this.comandeService.create(this.comande,token).subscribe({
        next : () => {
          this.errors=[];
          this.router.navigate(['/comande/list']);
        },
        error: response =>{
          this.errors = response.error.errors;
        }
      })
    }
}
