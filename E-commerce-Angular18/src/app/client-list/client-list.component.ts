import { Component, OnInit, inject } from '@angular/core';
import { ClientService } from '../services/client/client.service';
import { Client } from '../model/client.interface';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-client-list',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './client-list.component.html',
  styleUrl: './client-list.component.scss'
})
export default class ClientListComponent implements OnInit {

  private clientService = inject(ClientService);

  clients: Client[] = [];

  ngOnInit(): void {
    this.loadClient()
  }

  loadClient(){
    this.clientService.list()
    .subscribe(client =>{
      this.clients = client
    });
  }

  deleteClient(id:number){
    this.clientService.delete(id)
    .subscribe(()=>{
      this.loadClient();
    })
  }

}
