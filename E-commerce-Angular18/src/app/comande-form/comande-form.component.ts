import { Component, OnInit, inject } from '@angular/core';
import { ComandeService } from '../services/comande/comande.service';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Comande } from '../model/comande.interface';
import { Observable } from 'rxjs';
import { Produit } from '../model/produit.interface';
import { ProduitService } from '../services/produit/produit.service';
import { CommonModule } from '@angular/common';
import { User } from '../model/user.interface';

@Component({
  selector: 'app-comande-form',
  standalone: true,
  imports: [RouterModule,ReactiveFormsModule,CommonModule],

templateUrl: './comande-form.component.html',
  styleUrl: './comande-form.component.scss'
})
export default class ComandeFormComponent implements OnInit{

  formData:any = {
    qte_com:'',
    produit_id:'',
    statut_com:'',
  };

  private comandeService = inject(ComandeService);
  private produitService = inject(ProduitService);
  private fb = inject(FormBuilder);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  form?: FormGroup;
  comande?: Comande;
  errors: string[] = [];
  user: User[] = [];
  produits: Produit[] = [];

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get("id");
    const token:any = localStorage.getItem('token');

    if(id){
      this.comandeService.get(parseInt(id),token).subscribe(comande=>{
        this.comande = comande;
        this.form = this.fb.group({
          qte_com:[comande.qte_com,[Validators.required]],
          produit_id:[comande.produit_id,[Validators.required]],
          statut_com:[comande.statut_com,[Validators.required]],
        })

      })
    }else{
      this.form = this.fb.group({
        qte_com:['',[Validators.required]],
        produit_id:['',[Validators.required]],
        statut_com:['',[Validators.required]],
      })
    }

    this.loadProduits();

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

  save(){
    const token:any = localStorage.getItem('token');

    if(this.form?.invalid){
      this.form.markAllAsTouched();
      return;
    }
    const comandeform  = this.form!.value;
    let request : Observable<Comande>;

    if(this.comande){
      request = this.comandeService.update(comandeform,this.comande.id,token);
    }else{
      console.log("erreur lors de l'ajout de comande");

      return;
    }

    request
    .subscribe({
      next : () => {
        this.errors=[];
        this.router.navigate(['/comande/list']);
      },
      error: response =>{
        this.errors = response.error.errors;
      }
    });
  }
}
