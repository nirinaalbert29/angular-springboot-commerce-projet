import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ClientService } from '../services/client/client.service';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { Client } from '../model/client.interface';
import { Observable } from 'rxjs';


@Component({
  selector: 'app-client-form',
  standalone: true,
  imports: [RouterModule,ReactiveFormsModule],

templateUrl: './client-form.component.html',
  styleUrl: './client-form.component.scss'
})
export default class ClientFormComponent implements OnInit{

  private fb = inject(FormBuilder);
  private clientService = inject(ClientService);
  private router  = inject(Router);
  private route = inject(ActivatedRoute);

  form?: FormGroup;
  client? : Client;
  errors:string[]=[];

  ngOnInit(): void {
    const id  = this.route.snapshot.paramMap.get("id");

    if(id){
      this.clientService.get(parseInt(id))
      .subscribe(client => {
        this.client = client;
        this.form = this.fb.group({
          nom :[client.nom,[Validators.required]],
          email:[client.email,[Validators.required,Validators.email]],
          adresse:[client.adresse,[Validators.required]],
          cin:[client.cin,[Validators.required]],
          date_naiss:[client.date_naiss,[Validators.required]]
        });
      })
    }
    else{
      this.form = this.fb.group({
        nom :['',[Validators.required]],
        email:['',[Validators.required,Validators.email]],
        adresse:['',[Validators.required]],
        cin:['',[Validators.required]],
        date_naiss:['',[Validators.required]]
      })
    }
  }

  save(){
    if(this.form?.invalid){
      this.form.markAllAsTouched();
      return;
    }
    const clientForm = this.form!.value;
    let request:Observable<Client>;

    if(this.client){
      request = this.clientService.update(this.client.id,clientForm);
    }else{
      request = this.clientService.create(clientForm);
    }

    request
    .subscribe({
      next:() =>{
        this.errors =[];
        this.router.navigate(['/client']);
      },
      error:response=>{
        this.errors = response.error.errors;
      }
    });
  }


}
