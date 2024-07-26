import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ProduitService } from '../services/produit/produit.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Produit } from '../model/produit.interface';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';  // Ajoutez cette ligne

@Component({
  selector: 'app-produit-form',
  standalone: true,
  imports: [RouterModule,ReactiveFormsModule],
  templateUrl: './produit-form.component.html',
  styleUrl: './produit-form.component.scss'
})
export default class ProduitFormComponent implements OnInit{
  private fb  = inject(FormBuilder);
  private produitService= inject(ProduitService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  form?:FormGroup;
  produits?:Produit;
  errors: string[] = [];
  selectedFile: File | null = null;

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get("id");
    const token:any = localStorage.getItem('token');
    if(id && token){
      this.produitService.get(parseInt(id),token)
      .subscribe(produit =>{
        this.produits = produit;
        this.form = this.fb.group({
          code_pro : [produit.code_pro,[Validators.required]],
          nom_pro:[produit.nom_pro,[Validators.required]],
          categorie:[produit.categorie,[Validators.required]],
          description:[produit.description,[Validators.required]],
          prix_pro:[produit.prix_pro,[Validators.required]],
          image_pro:[produit.image_pro,[Validators.required]]
        });
      })
    }else{
      this.form=this.fb.group({
          code_pro : ["",[Validators.required]],
          nom_pro:["",[Validators.required]],
          categorie:["",[Validators.required]],
          description:["",[Validators.required]],
          prix_pro:["",[Validators.required]],
          image_pro:[null,[Validators.required]]
      });
    }
  }

  onFileChange(event: any) {
    this.selectedFile = event.target.files[0] || null;
  }

  save() {
    if (this.form?.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    const formData = new FormData();
    formData.append('code_pro', this.form!.get('code_pro')?.value);
    formData.append('nom_pro', this.form!.get('nom_pro')?.value);
    formData.append('categorie', this.form!.get('categorie')?.value);
    formData.append('description', this.form!.get('description')?.value);
    formData.append('prix_pro', this.form!.get('prix_pro')?.value.toString());
    if (this.selectedFile) {
      formData.append('image_pro', this.selectedFile);
    }

    let request: Observable<Produit>;
    // let token =localStorage.getItem("token");
    const token:any = localStorage.getItem('token');
    if (this.produits) {
      request = this.produitService.update(this.produits.id, formData,token);
    } else {
      request = this.produitService.create(formData,(token));
    }

    request.subscribe({
      next: () => {
        this.errors = [];
        this.router.navigate(['/produit/list']);
      },
      error: response => {
        this.errors = response.error.errors;
      }
    });
  }

}
