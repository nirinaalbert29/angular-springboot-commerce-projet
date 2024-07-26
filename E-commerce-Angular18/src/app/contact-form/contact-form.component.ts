import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { ContactService } from './../services/contact.service';
import { Employee } from '../model/employee.interface';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-contact-form',
  standalone: true,
  imports: [RouterModule,ReactiveFormsModule],
  templateUrl: './contact-form.component.html',
  styleUrl: './contact-form.component.scss'
})
export default class ContactFormComponent implements OnInit{
  private fb = inject(FormBuilder);
  private  contactService = inject(ContactService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  form?: FormGroup;
  employee?:Employee;
  errors: string[] = [];

  ngOnInit():void{
    const id = this.route.snapshot.paramMap.get('id');

    if(id){
      this.contactService.get(parseInt(id))
      .subscribe(employee => {
        this.employee = employee;
        this.form = this.fb.group({
          nom:[employee.nom,[Validators.required]],
          email:[employee.email,[Validators.required,Validators.email]],
          phone:[employee.phone,[Validators.required]],
          departement:[employee.departement,[Validators.required]]
        });

      })
    }else{
      this.form = this.fb.group({
        ////----Validation avec Angular-----///
        nom:['',[Validators.required]],
        email:['',[Validators.required,Validators.email]],
        phone:['',[Validators.required]],
        departement:['',[Validators.required]]

        // nom:['',[]],
        // email:['',[]],
        // phone:['',[]],
        // departement:['',[]]
      });
    }

  }


  save(){
    if(this.form?.invalid){
      this.form.markAllAsTouched();
      return;
    }
    const employeeForm = this.form!.value;
    let request:Observable<Employee>;

    if(this.employee){
      request = this.contactService.update(this.employee.id,employeeForm);
    }else{
      request = this.contactService.create(employeeForm);
    }

    request
    .subscribe({
      next : () => {
        this.errors=[];
        this.router.navigate(['/employee']);
      },
      error: response =>{
        this.errors = response.error.errors;
      }
    });
  }
}
