import { NgModule } from '@angular/core';
import { type Routes, RouterModule } from '@angular/router';
import { TestFormComponent } from './components/test-form/test-form.component';

export const routes: Routes = [{ path: '', component: TestFormComponent }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MainRoutingModule {}
