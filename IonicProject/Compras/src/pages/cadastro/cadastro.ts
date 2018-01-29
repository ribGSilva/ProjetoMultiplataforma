import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { Http } from '@angular/http';
import { AlertController } from 'ionic-angular';
import { isUndefined } from 'ionic-angular/util/util';

@IonicPage()
@Component({
  selector: 'page-cadastro',
  templateUrl: 'cadastro.html',
})
export class CadastroPage {

  nome: String;
  preco: number = 0;
  descricao: String;

  constructor(private alertCtrl: AlertController, public navCtrl: NavController, public navParams: NavParams, private http: Http ) {
  }

  cadastrar(){

    if (isUndefined(this.nome) || this.nome == '') {
      this.alerta('Falha', 'Nome inválido');
      return;
    }
    if (isUndefined(this.preco) || this.preco < 0) {
      this.alerta('Falha', 'Preço inválido');
      return;
    }
    if (isUndefined(this.descricao) || this.descricao == '') {
      this.alerta('Falha', 'Descrição inválido');
      return;
    }

    this.efetuarCadastro();
  }

  efetuarCadastro(){
    // const novoProduto = new Produto();
    // novoProduto.nome = this.nome;
    // novoProduto.preco = this.preco;
    // novoProduto.descricao = this.descricao;

    // const body = JSON.stringify(novoProduto);

    // const head = new Headers({ 'Content-Type': 'application/json'});

    // this.http.post(
    //   'http://localhost:8080/ServidorDeProdutos/RestDeProdutos/adicionarProduto', body, { headers: head }
    // ).toPromise().then(response => {
    //   console.log(response);
    //   this.sucesso = 'Sucesso ao cadastrar o item ' + novoProduto.nome;
    // }).catch(err => { this.mensagemDeErro = 'Falha na conexão'; console.error = err.message || err; });

    this.http.get(
      'http://localhost:8080/ServidorDeProdutos/RestDeProdutos/adicionarProduto/' +
        this.nome + '/' + this.preco + '/' + this.descricao )
    .toPromise()
    .then(response => this.alerta('Sucesso', 'Item cadastrado com sucesso!') )
    .catch(err => { this.alerta('Falha', 'Falha na conexão'); console.error = err.message || err; });

    this.nome = '';
    this.preco = 0.0;
    this.descricao = '';
  }

  alerta(titulo: string, corpo: string) {
    let alert = this.alertCtrl.create({
      title: titulo,
      subTitle: corpo,
      buttons: ['OK']
    });
    alert.present();
  }

}
