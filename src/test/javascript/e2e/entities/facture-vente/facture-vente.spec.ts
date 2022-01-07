import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FactureVenteComponentsPage, FactureVenteDeleteDialog, FactureVenteUpdatePage } from './facture-vente.page-object';

const expect = chai.expect;

describe('FactureVente e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let factureVenteComponentsPage: FactureVenteComponentsPage;
  let factureVenteUpdatePage: FactureVenteUpdatePage;
  let factureVenteDeleteDialog: FactureVenteDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FactureVentes', async () => {
    await navBarPage.goToEntity('facture-vente');
    factureVenteComponentsPage = new FactureVenteComponentsPage();
    await browser.wait(ec.visibilityOf(factureVenteComponentsPage.title), 5000);
    expect(await factureVenteComponentsPage.getTitle()).to.eq('myCrMv1App.factureVente.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(factureVenteComponentsPage.entities), ec.visibilityOf(factureVenteComponentsPage.noResult)),
      1000
    );
  });

  it('should load create FactureVente page', async () => {
    await factureVenteComponentsPage.clickOnCreateButton();
    factureVenteUpdatePage = new FactureVenteUpdatePage();
    expect(await factureVenteUpdatePage.getPageTitle()).to.eq('myCrMv1App.factureVente.home.createOrEditLabel');
    await factureVenteUpdatePage.cancel();
  });

  it('should create and save FactureVentes', async () => {
    const nbButtonsBeforeCreate = await factureVenteComponentsPage.countDeleteButtons();

    await factureVenteComponentsPage.clickOnCreateButton();

    await promise.all([
      factureVenteUpdatePage.setVenteIdentFacInput('5'),
      factureVenteUpdatePage.setVenteDateEffetInput('2000-12-31'),
      factureVenteUpdatePage.setVenteDateUpdateInput('2000-12-31'),
      factureVenteUpdatePage.setVenteStatusFactInput('venteStatusFact'),
      factureVenteUpdatePage.setVenteMontantHTInput('5'),
      factureVenteUpdatePage.setVenteMontantTVAInput('5'),
      factureVenteUpdatePage.setVenteMontantTTCInput('5'),
      factureVenteUpdatePage.livraisonClSelectLastOption(),
    ]);

    await factureVenteUpdatePage.save();
    expect(await factureVenteUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await factureVenteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last FactureVente', async () => {
    const nbButtonsBeforeDelete = await factureVenteComponentsPage.countDeleteButtons();
    await factureVenteComponentsPage.clickOnLastDeleteButton();

    factureVenteDeleteDialog = new FactureVenteDeleteDialog();
    expect(await factureVenteDeleteDialog.getDialogTitle()).to.eq('myCrMv1App.factureVente.delete.question');
    await factureVenteDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(factureVenteComponentsPage.title), 5000);

    expect(await factureVenteComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
