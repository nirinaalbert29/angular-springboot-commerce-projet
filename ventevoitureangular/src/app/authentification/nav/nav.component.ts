import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { UsersService } from '../users.service';

@Component({
  selector: 'app-nav',
  standalone: true,
  imports: [CommonModule,RouterOutlet,RouterLink],
  templateUrl: './nav.component.html',
  styleUrl: './nav.component.scss'
})
export class NavComponent implements OnInit{
  constructor(private readonly userService:UsersService){}

  isAuthenticated:boolean = false;
  isAdmin:boolean = false;
  isUser:boolean = false;

  ngOnInit(): void {
    this.isAuthenticated = this.userService.isAuthenticated();
    this.isAdmin = this.userService.isAdmin();
    this.isUser = this.userService.isUser();
  }

  logout():void{
    this.userService.logout();
    this.isAuthenticated = false;
    this.isAdmin = false;
    this.isUser = false;
  }
}
