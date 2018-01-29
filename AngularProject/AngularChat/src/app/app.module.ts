import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { HttpModule } from '@angular/http';
import * as alasql from 'alasql';

import { AppComponent } from './app.component';
import { CadastradosComponent } from './cadastrados/cadastrados.component';
import { CadastroComponent } from './cadastro/cadastro.component';
import { HomeComponent } from './home/home.component';

@NgModule({
  declarations: [
    AppComponent,
    CadastradosComponent,
    CadastroComponent,
    HomeComponent
  ],
  imports: [
    RouterModule.forRoot([
      {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full'
      },
      {
        path: 'cadastro',
        component: CadastroComponent
      },
      {
        path: 'cadastrados',
        component: CadastradosComponent
      },
      {
        path: 'home',
        component: HomeComponent
      }
    ]),
    HttpModule,
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})

export class AppModule {
  irParaHome = function() {
    this.router.navigate(['/home']);
  };
  irParaCadastro = function() {
    this.router.navigate(['/cadastro']);
  };
  irParaCadastrados = function() {
    this.router.navigate(['/cadastrados']);
  };
}
