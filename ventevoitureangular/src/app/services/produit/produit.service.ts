import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Produit } from '../../model/produit.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProduitService {

  private BASE_URL = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  list(token: string): Observable<Produit[]> {
    const url = `${this.BASE_URL}/adminuser/produit`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<Produit[]>(url, { headers });
  }

  get(id:number,token:string):Observable<Produit>{
    const url = `${this.BASE_URL}/adminuser/produit/${id}`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.get<Produit>(url,{headers});
  }

  create(produit:FormData,token:string):Observable<Produit>{
    const url = `${this.BASE_URL}/admin/produit`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.post<Produit>(url,produit,{ headers });
  }

  update(id:number,produit:FormData,token:string):Observable<Produit>{
    const url = `${this.BASE_URL}/admin/produit/${id}`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.put<Produit>(url,produit,{ headers });
  }

  delete(id:number,token:string):Observable<void>{
    const url = `${this.BASE_URL}/admin/produit/${id}`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.delete<void>(url,{headers});
  }
}
