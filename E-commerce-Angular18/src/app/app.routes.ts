import { Routes } from '@angular/router';
import { LoginComponent } from './authentification/login/login.component';
import { RegisterComponent } from './authentification/register/register.component';
import { ProfileComponent } from './authentification/profile/profile.component';
import { UpdateuserComponent } from './authentification/updateuser/updateuser.component';
import { UserslistComponent } from './authentification/userslist/userslist.component';
import { adminGuard, usersGuard } from './authentification/users.guard';
import ComandeListComponent from './comande-list/comande-list.component';
import ProduitFormComponent from './produit-form/produit-form.component';
import ProduitListComponent from './produit-list/produit-list.component';
import ClientFormComponent from './client-form/client-form.component';
import ClientListComponent from './client-list/client-list.component';
import ComandeFormComponent from './comande-form/comande-form.component';

export const routes: Routes = [
    // {
    //   path:'',
    //   loadComponent:()=>import('./dashboard/dashboard.component')
    // },

    {path:'client',component:ClientListComponent},
    {path:'client/new',component:ClientFormComponent},
    {path:'client/:id/edit',component:ClientFormComponent},

    { path: 'produit/list', component: ProduitListComponent, canActivate: [usersGuard] },
    { path: 'produit/list', component: ProduitListComponent, canActivate: [adminGuard] },

    {path:'produit/new',component:ProduitFormComponent,canActivate:[adminGuard]},
    {path:'produit/list/:id/edit',component:ProduitFormComponent,canActivate:[adminGuard]},

    {path:'comande/list',component:ComandeListComponent},

    {path:'comande/list/:id/edit',component:ComandeFormComponent},


    {path:'login',component:LoginComponent},
    {path:'register',component:RegisterComponent},
    {path:'profile',component:ProfileComponent,canActivate:[usersGuard]},
    {path:'update/:id',component:UpdateuserComponent},
    {path:'users',component:UserslistComponent,canActivate:[adminGuard]},
    {path:'**',component:LoginComponent},
    {path:'',redirectTo: '/login',pathMatch: 'full'},
];
