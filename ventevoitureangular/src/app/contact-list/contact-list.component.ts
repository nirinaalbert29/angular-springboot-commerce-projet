import { Component, OnInit, inject } from '@angular/core';
import { ContactService } from './../services/contact.service';
import { DatePipe } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Employee } from '../model/employee.interface';

@Component({
  selector: 'app-contact-list',
  standalone: true,
  imports: [DatePipe,RouterModule],
  templateUrl: './contact-list.component.html',
  styleUrl: './contact-list.component.scss'
})
export default class ContactListComponent implements OnInit
{
  private contactService = inject(ContactService);

  employees :Employee[] = [];

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll(){
    this.contactService.list()
    .subscribe(employees => {
      this.employees = employees;
    });
  }

  deleteEmployee(employee: Employee){
    this.contactService.delete(employee.id)
    .subscribe(()=>{
      this.loadAll();
    });
  }
}
