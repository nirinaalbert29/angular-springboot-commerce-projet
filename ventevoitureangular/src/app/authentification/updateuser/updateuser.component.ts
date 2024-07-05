import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UsersService } from '../users.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-updateuser',
  standalone: true,
  imports: [CommonModule,FormsModule],
  templateUrl: './updateuser.component.html',
  styleUrl: './updateuser.component.scss'
})
export class UpdateuserComponent implements OnInit {

  constructor(
    private readonly userService:UsersService,
    private readonly router:Router,
    private readonly route:ActivatedRoute
  ){}

  userId:any;
  userData:any={};
  errorMessage:string = '';
  successMessage:string = '';

  ngOnInit(): void {
    this.getUserById()
  }

  async getUserById(){
    this.userId = this.route.snapshot.paramMap.get('id')
    const token = localStorage.getItem('token');
    if(!this.userId || !token){
      this.showError("User Id or Token is required");
      return;
    }

    try {
      let userDataResponse = await this.userService.getUserById(this.userId,token);
      const {name,email,role,city} = userDataResponse.ourUsers
      this.userData = {name,email,role,city};
    } catch (error:any) {
      this.showError(error.message);
    }
  }

  async updateUser(){
    const confirmup  = confirm("Are you sure you wanna update this user");
    if(!confirmup) return
    try {

      const token = localStorage.getItem('token')
      if(!token){
        throw new Error("Token not found")
      }
      const res = await this.userService.updateUser(this.userId,this.userData,token);

      if(res.statutsCode === 200 && localStorage.getItem('role') === "ADMIN"){
        this.router.navigate(['/users'])
      }else if(res.statutsCode === 200 && localStorage.getItem('role') === "USER"){
        this.showSuccessUser();
      }
      else{
        this.showError(res.message)
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
  showSuccessUser(){
    this.successMessage = 'User updated successfuly';
    setTimeout(() => {
      this.successMessage = ''
    },3000)
  }

}
