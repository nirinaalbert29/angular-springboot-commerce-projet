import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Employee } from '../model/employee.interface';

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  private http = inject(HttpClient);

  list(){
    return this.http.get<Employee[]>('http://localhost:8080/api/employee')
  }

  get(id: number){
    return this.http.get<Employee>(`http://localhost:8080/api/employee/${id}`)
  }

  create(employee: Employee){
    return this.http.post<Employee>('http://localhost:8080/api/employee',employee)
  }

  update(id: number,employee: Employee){
    return this.http.put<Employee>(`http://localhost:8080/api/employee/${id}`,employee)
  }

  delete(id: number){
    return this.http.delete<void>(`http://localhost:8080/api/employee/${id}`)
  }


  // constructor() { }
}
