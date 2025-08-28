import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Task } from './task.model';
import { tap, catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import 'zone.js';

/** Service des tâches (dev: proxy /api -> backend). */
@Injectable({ providedIn: 'root' })
export class TaskService {
  private readonly tasksSig = signal<Task[] | null>(null);
  tasks = this.tasksSig.asReadonly();

  constructor(private http: HttpClient) {}

  loadAll(completed?: boolean) {
    const url = completed === undefined ? '/api/tasks' : `/api/tasks?completed=${completed}`;
    return this.http.get<Task[]>(url).pipe(
      tap(ts => this.tasksSig.set(ts)),
      catchError(err => { console.error('API error', err); this.tasksSig.set([]); return of([]); })
    ).subscribe();
  }

  get(id: string) { return this.http.get<Task>(`/api/tasks/${id}`); }

  setCompleted(id: string, completed: boolean) {
    return this.http.patch<Task>(`/api/tasks/${id}/status`, { completed }).pipe(
      tap(updated => {
        const list = this.tasksSig() ?? [];
        this.tasksSig.set(list.map(t => t.id === id ? updated : t));
      })
    );
  }
}
