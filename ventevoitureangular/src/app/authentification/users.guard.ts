import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { UsersService } from './users.service';

export const usersGuard: CanActivateFn = (route, state) => {
  if(inject(UsersService).isAuthenticated()){
    return true;
  }
  else{
    inject(Router).navigate(['/login'])
    return false;
  }
};

export const adminGuard:CanActivateFn = (route,state) => {
  if(inject(UsersService).isAdmin()){
    return true;
  }
  else{
    inject(Router).navigate(['/login'])
    return false;
  }
}
