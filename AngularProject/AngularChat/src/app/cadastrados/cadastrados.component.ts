import { Component, OnInit } from '@angular/core';
import { Http } from '@angular/http';
import { Produto } from '../entities/produto';
import { forEach } from '@angular/router/src/utils/collection';

@Component({
  selector: 'app-cadastrados',
  templateUrl: './cadastrados.component.html',
  styleUrls: ['./cadastrados.component.css']
})

export class CadastradosComponent implements OnInit {
  produtos = new Array<Produto>();
  mensagemErro = '';
  total = 0.00;

  constructor(private http: Http) {
    this.pegarLista();
  }

  ngOnInit() {
  }

  pegarLista() {
    this.http.get('http://localhost:8080/ServidorDeProdutos/RestDeProdutos/pegarProdutos').toPromise()
    .then(response => {
      this.produtos = response.json() as Produto[];
      this.updateTotal();
    })
    .catch(err => { this.mensagemErro = 'Falha na conexão'; console.error = err.message || err; });
  }

  updateTotal() {
    let soma = 0.00;

    for (const produto of this.produtos) {
      soma += produto.preco as number;
    }

    this.total = soma;
  }

  removeItem(index: number) {
    this.http.get('http://localhost:8080/ServidorDeProdutos/RestDeProdutos/removerProdutoNaPosicao/' + index)
    .toPromise()
    .then(response => this.pegarLista())
    .catch(err => { this.mensagemErro = 'Falha na conexão'; console.error = err.message || err; });
  }

  exportDataXLSX () {
    alasql('SELECT * INTO XLSX("produtos.xlsx",{headers:true}) FROM ?', [this.produtos]);
  }

  exportDataCSV() {
    alasql('SELECT * INTO CSV("produtos.csv",{headers:true, separator:";"}) FROM ?', [this.produtos]);
  }

  exportDataPDF() {
    /*html2canvas(document.getElementById('exportthis'), {
        onrendered: function (canvas) {
            const data = canvas.toDataURL();
            const docDefinition = {
                content: [{
                    image: data,
                    width: 500,
                }]
            };
            pdfMake.createPdf(docDefinition).download('produtos.pdf');
        }
    });*/
  }
}
