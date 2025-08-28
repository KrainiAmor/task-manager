import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { NgIf } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { TaskService } from './task.service';
import { Task } from './task.model';

@Component({
  standalone: true,
  selector: 'app-task-detail',
  imports: [NgIf, MatCardModule, MatButtonModule, MatIconModule],
  template: `
    <mat-card *ngIf="task">
      <mat-card-header>
        <mat-card-title>{{ task.label }}</mat-card-title>
        <mat-card-subtitle>ID: {{ task.id }}</mat-card-subtitle>
      </mat-card-header>
      <mat-card-content>
        <p>{{ task.description }}</p>
        <p>Status: <strong>{{ task.completed ? 'Done' : 'Open' }}</strong></p>
      </mat-card-content>
      <mat-card-actions>
        <button mat-raised-button color="primary" (click)="toggle()">
          <mat-icon>{{ task.completed ? 'undo' : 'check' }}</mat-icon>
          {{ task.completed ? 'Mark as open' : 'Mark as done' }}
        </button>
      </mat-card-actions>
    </mat-card>
  `
})
export class TaskDetailComponent implements OnInit {
  task?: Task;
  constructor(private route: ActivatedRoute, private svc: TaskService) {}
  ngOnInit() { const id = this.route.snapshot.paramMap.get('id')!; this.svc.get(id).subscribe(t => this.task = t); }
  toggle() { if (!this.task) return; this.svc.setCompleted(this.task.id, !this.task.completed).subscribe(t => this.task = t); }
}
