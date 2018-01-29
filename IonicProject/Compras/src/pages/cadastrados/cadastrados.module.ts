import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { CadastradosPage } from './cadastrados';

@NgModule({
  declarations: [
    CadastradosPage,
  ],
  imports: [
    IonicPageModule.forChild(CadastradosPage),
  ],
})
export class CadastradosPageModule {}
