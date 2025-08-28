import { Component } from '@angular/core';
import { RouterOutlet, RouterLink } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterLink, MatToolbarModule, MatButtonModule, MatIconModule],
  template: `
    <mat-toolbar color="primary">
      <span>Tâches</span>
      <span style="flex:1 1 auto"></span>
      <a mat-button routerLink="/tasks"><mat-icon>list</mat-icon> Tasks</a>
      <a mat-button href="http://localhost:8080/swagger-ui.html" target="_blank"><mat-icon>api</mat-icon> API</a>
    </mat-toolbar>
    <div class="container">
      <router-outlet></router-outlet>
    </div>
  `
})
export class AppComponent {}
