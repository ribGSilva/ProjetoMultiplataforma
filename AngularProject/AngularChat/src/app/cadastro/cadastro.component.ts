import { Component, OnInit } from '@angular/core';
import { Http, Headers, Response } from '@angular/http';
import { Produto } from '../entities/produto';

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.css']
})
export class CadastroComponent implements OnInit {
  errorText: String = '';
  sucesso: String = '';
  nome: String = '';
  preco: Number = 0.0;
  descricao: String = '';

  constructor(private http: Http) {}

  ngOnInit() {}

  cadastrarProduto() {
    this.errorText = '';
    if (this.nome === '') {
      this.errorText = 'Nome vazio';
      return;
    }

    if (this.preco < 0) {
      this.errorText = 'Preco inválido';
      return;
    }

    if (this.descricao === '') {
      this.errorText = 'Descricao vazia';
      return;
    }

    this.fazRequest();
  }

  private fazRequest() {
    /*const produto = new Produto();
    produto.nome = this.nome;
    produto.preco = this.preco;
    produto.descricao = this.descricao;

    const body = JSON.stringify(produto);

    const head = new Headers({ 'Content-Type': 'application/json'});

    this.http.post(
      'http://localhost:8080/ServidorDeProdutos/RestDeProdutos/adicionarProduto', body, { headers: head }
    ).toPromise().then(response => {
      console.log(response);
      this.sucesso = response.json();
    }).catch(this.handleError);*/

    this.http.get(
        'http://localhost:8080/ServidorDeProdutos/RestDeProdutos/adicionarProduto/' +
          this.nome + '/' + this.preco + '/' + this.descricao )
      .toPromise()
      .then(response => this.sucesso = this.nome + ' foi cadastrado')
      .catch(err => { this.errorText = 'Falha na conexão'; console.error = err.message || err; });
  }
}
