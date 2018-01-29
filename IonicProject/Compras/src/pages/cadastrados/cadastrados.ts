import { Component } from '@angular/core';
import { IonicPage, NavController, NavParams } from 'ionic-angular';
import { Http } from '@angular/http';
import { AlertController } from 'ionic-angular';
import { Produto } from '../../app/entities/produto';


@IonicPage()
@Component({
  selector: 'page-cadastrados',
  templateUrl: 'cadastrados.html',
})
export class CadastradosPage {

  produtos = new Array<Produto>();
  total = 0.00;

  constructor(private alertCtrl: AlertController, public navCtrl: NavController, public navParams: NavParams, private http: Http) {
  }

  ionViewWillEnter () {
    this.pegarLista();
  }

  pegarLista() {
    this.http.get('http://localhost:8080/ServidorDeProdutos/RestDeProdutos/pegarProdutos').toPromise()
    .then(response => {
      this.produtos = response.json() as Produto[];
      this.updateTotal();
    })
    .catch(err => { this.alerta('Falha', 'Falha na conexão'); console.error = err.message || err; });
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
    .then(response => { this.pegarLista(); this.alerta('Sucesso', 'Item removido com sucesso!'); })
    .catch(err => { this.alerta('Falha', 'Falha na conexão'); console.error = err.message || err; });
  }

  alerta(titulo: string, corpo: string) {
    let alert = this.alertCtrl.create({
      title: titulo,
      subTitle: corpo,
      buttons: ['OK']
    });
    alert.present();
  }

  alertaDeExclusao(index: number){
    let alert = this.alertCtrl.create({
      title: "Cuidado!",
      subTitle: "Tem certeza que deseja apagar esse item?",
      buttons: [
        {
          text: 'Cancelar',
          role: 'cancel'
        },
        {
          text: 'Apagar',
          handler: () => {
            this.removeItem(index);
          }
        }
      ]
    });
    alert.present();
  }

}
