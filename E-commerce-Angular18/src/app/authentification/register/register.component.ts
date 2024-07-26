import { Component, signal } from '@angular/core';
import { UsersService } from '../users.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon'; 
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatButtonModule } from '@angular/material/button';
import {MatSelectModule} from '@angular/material/select';
import {MatFormFieldModule} from '@angular/material/form-field';


@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    MatIconModule,
    MatCardModule,
    MatInputModule,
    MatCheckboxModule,
    MatButtonModule,
    MatFormFieldModule, 
    MatSelectModule
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {
  formData:any = {
    name:'',
    email:'',
    password:'',
    role:'',
    city:''
  };
  errorMessage:string = '';

  constructor(
    private readonly userService:UsersService,
    private readonly router:Router
  ){}

  async handleSubmit(){
    if(!this.formData.name || !this.formData.email || !this.formData.password || !this.formData.role || !this.formData.city){
      this.showError("please fill in the fields.");
      return;
    }

    //confirm registration with user
    const confirmRegistration = confirm("Are you sure you want to register this user?");
    if(!confirmRegistration){
      return;
    }
    try {


      const response = await this.userService.register(this.formData);
      if(response.statutsCode === 200){
        this.router.navigate(["/users"]);
        
      } else{
        this.showError(response.message)
      }
    } catch (error:any) {
      this.showError(error.message);
    }
  }


  showError(mess: string){
    this.errorMessage = mess;
    setTimeout(() => {
      this.errorMessage = ''
    },3000)
  }

  hide = signal(true);
  clickEvent(event: MouseEvent) {
    this.hide.set(!this.hide());
    event.stopPropagation();
  }
}
