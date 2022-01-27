import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'article',
        data: { pageTitle: 'myCrMv1App.article.home.title' },
        loadChildren: () => import('./article/article.module').then(m => m.ArticleModule),
      },
      {
        path: 'unite-article',
        data: { pageTitle: 'myCrMv1App.uniteArticle.home.title' },
        loadChildren: () => import('./unite-article/unite-article.module').then(m => m.UniteArticleModule),
      },
      {
        path: 'ligne-cmd-fournisseur',
        data: { pageTitle: 'myCrMv1App.ligneCmdFournisseur.home.title' },
        loadChildren: () => import('./ligne-cmd-fournisseur/ligne-cmd-fournisseur.module').then(m => m.LigneCmdFournisseurModule),
      },
      {
        path: 'commande-fournisseur',
        data: { pageTitle: 'myCrMv1App.commandeFournisseur.home.title' },
        loadChildren: () => import('./commande-fournisseur/commande-fournisseur.module').then(m => m.CommandeFournisseurModule),
      },
      {
        path: 'facture-achat',
        data: { pageTitle: 'myCrMv1App.factureAchat.home.title' },
        loadChildren: () => import('./facture-achat/facture-achat.module').then(m => m.FactureAchatModule),
      },
      {
        path: 'livraison-fr',
        data: { pageTitle: 'myCrMv1App.livraisonFr.home.title' },
        loadChildren: () => import('./livraison-fr/livraison-fr.module').then(m => m.LivraisonFrModule),
      },
      {
        path: 'ligne-liv-fournisseur',
        data: { pageTitle: 'myCrMv1App.ligneLivFournisseur.home.title' },
        loadChildren: () => import('./ligne-liv-fournisseur/ligne-liv-fournisseur.module').then(m => m.LigneLivFournisseurModule),
      },
      {
        path: 'payement-fournisseur',
        data: { pageTitle: 'myCrMv1App.payementFournisseur.home.title' },
        loadChildren: () => import('./payement-fournisseur/payement-fournisseur.module').then(m => m.PayementFournisseurModule),
      },
      {
        path: 'contact-fournisseur',
        data: { pageTitle: 'myCrMv1App.contactFournisseur.home.title' },
        loadChildren: () => import('./contact-fournisseur/contact-fournisseur.module').then(m => m.ContactFournisseurModule),
      },
      {
        path: 'civilite-fournisseur',
        data: { pageTitle: 'myCrMv1App.civiliteFournisseur.home.title' },
        loadChildren: () => import('./civilite-fournisseur/civilite-fournisseur.module').then(m => m.CiviliteFournisseurModule),
      },
      {
        path: 'fournisseur',
        data: { pageTitle: 'myCrMv1App.fournisseur.home.title' },
        loadChildren: () => import('./fournisseur/fournisseur.module').then(m => m.FournisseurModule),
      },
      {
        path: 'ligne-cmd-client',
        data: { pageTitle: 'myCrMv1App.ligneCmdClient.home.title' },
        loadChildren: () => import('./ligne-cmd-client/ligne-cmd-client.module').then(m => m.LigneCmdClientModule),
      },
      {
        path: 'commande-client',
        data: { pageTitle: 'myCrMv1App.commandeClient.home.title' },
        loadChildren: () => import('./commande-client/commande-client.module').then(m => m.CommandeClientModule),
      },
      {
        path: 'facture-vente',
        data: { pageTitle: 'myCrMv1App.factureVente.home.title' },
        loadChildren: () => import('./facture-vente/facture-vente.module').then(m => m.FactureVenteModule),
      },
      {
        path: 'livraison-cl',
        data: { pageTitle: 'myCrMv1App.livraisonCl.home.title' },
        loadChildren: () => import('./livraison-cl/livraison-cl.module').then(m => m.LivraisonClModule),
      },
      {
        path: 'ligne-liv-client',
        data: { pageTitle: 'myCrMv1App.ligneLivClient.home.title' },
        loadChildren: () => import('./ligne-liv-client/ligne-liv-client.module').then(m => m.LigneLivClientModule),
      },
      {
        path: 'payement-client',
        data: { pageTitle: 'myCrMv1App.payementClient.home.title' },
        loadChildren: () => import('./payement-client/payement-client.module').then(m => m.PayementClientModule),
      },
      {
        path: 'contact-client',
        data: { pageTitle: 'myCrMv1App.contactClient.home.title' },
        loadChildren: () => import('./contact-client/contact-client.module').then(m => m.ContactClientModule),
      },
      {
        path: 'civilite-client',
        data: { pageTitle: 'myCrMv1App.civiliteClient.home.title' },
        loadChildren: () => import('./civilite-client/civilite-client.module').then(m => m.CiviliteClientModule),
      },
      {
        path: 'client',
        data: { pageTitle: 'myCrMv1App.client.home.title' },
        loadChildren: () => import('./client/client.module').then(m => m.ClientModule),
      },
      {
        path: 'carts',
        data: { pageTitle: 'myCrMv1App.carts.home.title' },
        loadChildren: () => import('./carts/carts.module').then(m => m.CartsModule),
      },
      {
        path: 'category',
        data: { pageTitle: 'myCrMv1App.category.home.title' },
        loadChildren: () => import('./category/category.module').then(m => m.CategoryModule),
      },
      {
        path: 'catalogue',
        data: { pageTitle: 'myCrMv1App.catalogue.home.title' },
        loadChildren: () => import('./catalogue/catalogue.module').then(m => m.CatalogueModule),
      },
      {
        path: 'book',
        data: { pageTitle: 'myCrMv1App.book.home.title' },
        loadChildren: () => import('./book/book.module').then(m => m.BookModule),
      },
      {
        path: 'staff',
        data: { pageTitle: 'myCrMv1App.staff.home.title' },
        loadChildren: () => import('./staff/staff.module').then(m => m.StaffModule),
      },
      {
        path: 'member',
        data: { pageTitle: 'myCrMv1App.member.home.title' },
        loadChildren: () => import('./member/member.module').then(m => m.MemberModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
