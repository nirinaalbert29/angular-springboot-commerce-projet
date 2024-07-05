import { Component, OnInit } from '@angular/core';
import { UsersService } from '../users.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-userslist',
  standalone: true,
  imports: [CommonModule,RouterModule],
  templateUrl: './userslist.component.html',
  styleUrl: './userslist.component.scss'
})
export class UserslistComponent implements OnInit {

  users:any[] =[];
  errorMessage:string ='';
  constructor(
    private readonly userService:UsersService,
    private readonly router:Router
  ){}

  ngOnInit(): void {
    this.loadusers();
  }

  async loadusers(){
    try {
      const token:any = localStorage.getItem('token');
      const response = await this.userService.getAllUsers(token);
      if(response && response.statutsCode === 200 && response.ourUsersList){
        this.users = response.ourUsersList;
      }
      else{
        this.showError('No users found.');
      }
    } catch (error:any) {
      this.showError(error.message);
    }
  }

  async deleteUser(userId:string){
    const confirmDelete = confirm('Are you sure to want to delete this user?');
    if(confirmDelete){
      try {
        const token:any = localStorage.getItem('token');
        await this.userService.deleteUser(userId,token);

        //refesh the user list after deletion
        this.loadusers();
      } catch (error:any) {
        this.showError(error.message)
      }
    }
  }

  NavigateToUpdate(userId:string){
    this.router.navigate(['/update',userId])
  }


  showError(mess: string){
    this.errorMessage = mess;
    setTimeout(() => {
      this.errorMessage = ''
    },3000)
  }
}
