import { Component, OnInit, inject } from '@angular/core';
import { Init } from 'v8';
import { ComandeService } from '../services/comande/comande.service';
import { Comande } from '../model/comande.interface';
import { Router, RouterModule } from '@angular/router';
import { CommonModule, DatePipe } from '@angular/common';
import { ComandeDb } from '../model/comande_list.interface';
import { UsersService } from '../authentification/users.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-comande-list',
  standalone: true,
  imports: [DatePipe,RouterModule,CommonModule],
templateUrl: './comande-list.component.html',
  styleUrl: './comande-list.component.scss'
})
export default class ComandeListComponent implements OnInit{

  private comandeService = inject(ComandeService);
  comandes : ComandeDb[]=[];
  errorMessage:string ='';

  comande ?:Comande;
  errors ?:string[] = [];

  isAuthenticated:boolean = false;
  isAdmin:boolean = false;
  isUser:boolean = false;

  constructor(
    private readonly userService:UsersService,
    private router:Router,
  ){}

  ngOnInit(): void {
    this.loadComandes();
    this.isAuthenticated = this.userService.isAuthenticated();
    this.isAdmin = this.userService.isAdmin();
    this.isUser = this.userService.isUser();
  }


  async loadComandes(){
    try {
      const token:any = localStorage.getItem('token');
      const role = localStorage.getItem('role');
      let response;
      if(role === 'USER'){
        response = await this.comandeService.getComandeByUser(token);
      }else{
        response = await this.comandeService.getAllComande(token);
      }

      if(response){
        this.comandes = response;
      }
      else{
        this.showError('No comandes found.');
      }
    } catch (error:any) {
      this.showError(error.message);
    }
  }


  deleteComande(id:number){
    const confirmDelete = confirm("Are you sure you want to delete this comande?");
    if(!confirmDelete){
      return;
    }
    const token:any = localStorage.getItem('token');
    return this.comandeService.delete(id,token)
    .subscribe(()=>{
      this.loadComandes();
    })
  }

  approuverComande(comandeId:number){
    const token:any = localStorage.getItem('token');
    const confirApprouve = confirm("Are you sure you want to approuve this comande?");
    if(!confirApprouve){
      return;
    }else{
      if(token){
        return this.comandeService.approuverComande(comandeId,token)
        .subscribe(()=>{
          this.loadComandes();
        })
      }else{
        return this.showError("token for user not found");
      }
    }

  }
  refuserComande(comandeId:number){
    const token:any = localStorage.getItem('token');
    const confirApprouve = confirm("Are you sure you want to refuse this comande?");
    if(!confirApprouve){
      return;
    }else{
      if(token){
        return this.comandeService.refuserComande(comandeId,token)
        .subscribe(()=>{
          this.loadComandes();
        })
      }else{
        return this.showError("token for user not found");
      }
    }

  }

  showError(mess: string){
    this.errorMessage = mess;
    setTimeout(() => {
      this.errorMessage = ''
    },3000)
  }
}
