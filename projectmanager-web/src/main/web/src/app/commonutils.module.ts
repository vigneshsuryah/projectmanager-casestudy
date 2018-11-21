import { NgModule, ModuleWithProviders } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { TaskSearchPipe } from './pipes/searchtask';
import { ProjectSearchPipe } from './pipes/searchproject';
import { UserSearchPipe } from './pipes/searchuser';

@NgModule({
  imports: [CommonModule, RouterModule, FormsModule],
  declarations: [TaskSearchPipe, ProjectSearchPipe, UserSearchPipe],
  exports: [CommonModule, FormsModule, RouterModule, TaskSearchPipe, ProjectSearchPipe, UserSearchPipe],
  providers: [TaskSearchPipe, ProjectSearchPipe, UserSearchPipe]
})
export class CommonutilsModule {

  static forRoot(): ModuleWithProviders {
    return {
      ngModule: CommonutilsModule,      
    };
  }

  
}
