import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NavComponent } from './authentification/nav/nav.component';
import { FooterComponent } from './authentification/footer/footer.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet,NavComponent,FooterComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'ventevoitureangular';
}
