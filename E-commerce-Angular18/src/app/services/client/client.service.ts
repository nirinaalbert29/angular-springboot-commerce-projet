import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Client } from '../../model/client.interface';

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  private http = inject(HttpClient);

  list(){
    return this.http.get<Client[]>("http://localhost:8080/api/client")
  }
  get(id:number){
    return this.http.get<Client>(`http://localhost:8080/api/client/${id}`)
  }
  create(client:Client){
    return this.http.post<Client>("http://localhost:8080/api/client",client);
  }
  update(id:number,client:Client){
    return this.http.put<Client>(`http://localhost:8080/api/client/${id}`,client);
  }
  delete(id:number){
    return this.http.delete<void>(`http://localhost:8080/api/client/${id}`);
  }
}
