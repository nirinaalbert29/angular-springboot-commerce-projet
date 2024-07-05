import { Component, OnInit, inject } from '@angular/core';
import { ProduitService } from './../services/produit/produit.service';
import { Produit } from '../model/produit.interface';
import { Router, RouterModule } from '@angular/router';
import { Comande } from '../model/comande.interface';
import { ComandeService } from './../services/comande/comande.service';
import Swal from 'sweetalert2';
import { UsersService } from '../authentification/users.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-produit-list',
  standalone: true,
  imports: [RouterModule,CommonModule],
  templateUrl: './produit-list.component.html',
  styleUrl: './produit-list.component.scss'
})
export default class ProduitListComponent implements OnInit{

  private produitService = inject(ProduitService);
  private comandeService = inject(ComandeService);

  private router = inject(Router);

  produits:Produit[]=[];
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
  }

  ngOnInit(): void {
    this.loadProduits();
    this.isAuthenticated = this.userService.isAuthenticated();
    this.isAdmin = this.userService.isAdmin();
    this.isUser = this.userService.isUser();
  }

  loadProduits(){
    const token:any = localStorage.getItem('token');
    if(token){
      this.produitService.list(token)
      .subscribe(produit=>{
        this.produits = produit;
      });
    }else{
      console.log("token not found for view product list")
    }

  }

  deleteProduitById(id:number){
    const token:any = localStorage.getItem('token');
    this.produitService.delete(id,token)
    .subscribe(()=>{
      this.loadProduits();
    })
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
