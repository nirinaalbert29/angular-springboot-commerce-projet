import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Comande } from '../../model/comande.interface';
import { ComandeDb } from '../../model/comande_list.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ComandeService {

  private BASE_URL = "http://localhost:8080";
  constructor(private https :HttpClient) { }

  private http= inject(HttpClient);

  async getAllComande(token: string): Promise<ComandeDb[]> {
    const url = `${this.BASE_URL}/admin/comande`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    try {
      const result = await this.https.get<ComandeDb[]>(url, { headers }).toPromise();
      return result ?? [];
    } catch (error) {
       return [];
    }
  }
  async getComandeByUser(token:string):Promise<ComandeDb[]>{
    const url = `${this.BASE_URL}/user/comande`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    try {
      const response  = await this.http.get<ComandeDb[]>(url,{headers}).toPromise();
      return response ?? [];

    } catch (error) {
      throw [];
    }
  }

  get(id:number,token:string){
    const url = `${this.BASE_URL}/adminuser/comande/${id}`;
    const headers = new HttpHeaders({
    'Authorization': `Bearer ${token}`
  });
  return this.http.get<Comande>(url,{headers});
}

  create(comande:Comande,token:string){
    const url = `${this.BASE_URL}/user/comande`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.post<Comande>(url,comande,{headers});
  }

  update(comande:Comande,id:number,token:string){
    const url = `${this.BASE_URL}/adminuser/comande/${id}`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.put<Comande>(url,comande,{headers});
  }

  delete(id:number,token:string){
    const url = `${this.BASE_URL}/user/comande/${id}`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.delete<void>(url,{headers});
  }

  approuverComande(id:number,token:string){
    const url = `${this.BASE_URL}/admin/comande/approuver/${id}`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.put<Comande>(url,null, {headers} );
  }
  refuserComande(id:number,token:string){
    const url = `${this.BASE_URL}/admin/comande/refuser/${id}`;
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${token}`
    });
    return this.http.put<Comande>(url,null, {headers} );
  }
}
