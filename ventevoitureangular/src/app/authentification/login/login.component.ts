import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UsersService } from '../users.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  constructor(
    private readonly userService:UsersService,
    private router:Router
  ){ }

  email:string = '';
  password:string = ''
  errorMessage :string = ''

  async handleSubmit(){
    if(!this.email || !this.password){
      this.showError("Email and Password is required");
      return
    }

    try {

      const response = await this.userService.login(this.email,this.password);
      console.log(response)
      if(response.statutsCode == 200){
        localStorage.setItem('token',response.token)
        localStorage.setItem('role',response.role)
        this.router.navigate(['/profile']);

      }else{
        console.log("Not 200");
        this.showError(response.message);
      }
    } catch (error:any) {
      this.showError(error.message)
    }
  }

  showError(mess: string){
    this.errorMessage = mess;
    setTimeout(() => {
      this.errorMessage = ''
    },3000)
  }
}
