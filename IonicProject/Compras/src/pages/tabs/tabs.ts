import { Component } from '@angular/core';

import { CadastroPage } from '../cadastro/cadastro';
import { HomePage } from '../home/home';
import { CadastradosPage } from '../cadastrados/cadastrados';

@Component({
  templateUrl: 'tabs.html'
})
export class TabsPage {

  tab1Root = HomePage;
  tab2Root = CadastroPage;
  tab3Root = CadastradosPage;

  constructor() {

  }
}
