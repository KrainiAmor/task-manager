import { Routes } from '@angular/router';
import { TaskListComponent } from './features/tasks/task-list.component';
import { TaskDetailComponent } from './features/tasks/task-detail.component';

export const routes: Routes = [
  { path: '', redirectTo: 'tasks', pathMatch: 'full' },
  { path: 'tasks', component: TaskListComponent },
  { path: 'tasks/:id', component: TaskDetailComponent },
  { path: '**', redirectTo: 'tasks' }
];
