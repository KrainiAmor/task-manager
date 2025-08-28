import { Component, OnInit, computed, signal } from '@angular/core';
import { NgFor, NgIf } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MatListModule } from '@angular/material/list';
import { MatChipsModule } from '@angular/material/chips';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { TaskService } from './task.service';

@Component({
  standalone: true,
  selector: 'app-task-list',
  imports: [NgFor, NgIf, RouterLink, MatListModule, MatChipsModule, MatButtonModule, MatIconModule],
  template: `
    <div style="display:flex;gap:8px;align-items:center;margin:16px 0;">
      <mat-chip-listbox (change)="onFilter($event)">
        <mat-chip-option [selected]="filter() === 'all'">All</mat-chip-option>
        <mat-chip-option [selected]="filter() === 'open'">Open</mat-chip-option>
        <mat-chip-option [selected]="filter() === 'done'">Done</mat-chip-option>
      </mat-chip-listbox>
      <button mat-raised-button color="primary" (click)="refresh()"><mat-icon>refresh</mat-icon> Refresh</button>
    </div>

    <mat-nav-list *ngIf="tasks(); else loading">
      <ng-container *ngIf="filtered().length; else empty">
        <a mat-list-item *ngFor="let t of filtered()" [routerLink]="['/tasks', t.id]">
          <mat-icon matListItemIcon>{{ t.completed ? 'check_circle' : 'radio_button_unchecked' }}</mat-icon>
          <div matListItemTitle>{{ t.label }}</div>
          <div matListItemLine>{{ t.description }}</div>
        </a>
      </ng-container>
    </mat-nav-list>

    <ng-template #loading>Chargement…</ng-template>
    <ng-template #empty>Aucune tâche à afficher.</ng-template>
  `
})
export class TaskListComponent implements OnInit {
  filter = signal<'all'|'open'|'done'>('all');
  tasks = this.svc.tasks;

  constructor(private svc: TaskService) {}
  ngOnInit() { this.svc.loadAll(); }
  refresh() { this.svc.loadAll(this.filter() === 'all' ? undefined : this.filter() === 'done'); }
  onFilter(_: any) {
    const idx = (_.value ?? 0);
    this.filter.set(idx === 0 ? 'all' : idx === 1 ? 'open' : 'done');
    this.refresh();
  }
  filtered = computed(() => {
    const list = this.tasks(); if (!list) return [];
    if (this.filter() === 'all') return list;
    return list.filter(t => this.filter() === 'done' ? t.completed : !t.completed);
  });
}
