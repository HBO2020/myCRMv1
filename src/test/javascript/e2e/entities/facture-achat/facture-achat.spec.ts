import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FactureAchatComponentsPage, FactureAchatDeleteDialog, FactureAchatUpdatePage } from './facture-achat.page-object';

const expect = chai.expect;

describe('FactureAchat e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let factureAchatComponentsPage: FactureAchatComponentsPage;
  let factureAchatUpdatePage: FactureAchatUpdatePage;
  let factureAchatDeleteDialog: FactureAchatDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FactureAchats', async () => {
    await navBarPage.goToEntity('facture-achat');
    factureAchatComponentsPage = new FactureAchatComponentsPage();
    await browser.wait(ec.visibilityOf(factureAchatComponentsPage.title), 5000);
    expect(await factureAchatComponentsPage.getTitle()).to.eq('myCrMv1App.factureAchat.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(factureAchatComponentsPage.entities), ec.visibilityOf(factureAchatComponentsPage.noResult)),
      1000
    );
  });

  it('should load create FactureAchat page', async () => {
    await factureAchatComponentsPage.clickOnCreateButton();
    factureAchatUpdatePage = new FactureAchatUpdatePage();
    expect(await factureAchatUpdatePage.getPageTitle()).to.eq('myCrMv1App.factureAchat.home.createOrEditLabel');
    await factureAchatUpdatePage.cancel();
  });

  it('should create and save FactureAchats', async () => {
    const nbButtonsBeforeCreate = await factureAchatComponentsPage.countDeleteButtons();

    await factureAchatComponentsPage.clickOnCreateButton();

    await promise.all([
      factureAchatUpdatePage.setAchatIdentFacInput('5'),
      factureAchatUpdatePage.setAchatDateEffetInput('2000-12-31'),
      factureAchatUpdatePage.setAchatDateUpdateInput('2000-12-31'),
      factureAchatUpdatePage.setAchatStatusFactInput('achatStatusFact'),
      factureAchatUpdatePage.setAchatMontantHTInput('5'),
      factureAchatUpdatePage.setAchatMontantTVAInput('5'),
      factureAchatUpdatePage.setAchatMontantTTCInput('5'),
      factureAchatUpdatePage.fournisseurSelectLastOption(),
      factureAchatUpdatePage.payementFrSelectLastOption(),
      factureAchatUpdatePage.livraisonFrSelectLastOption(),
      factureAchatUpdatePage.clientSelectLastOption(),
      factureAchatUpdatePage.payementClSelectLastOption(),
    ]);

    await factureAchatUpdatePage.save();
    expect(await factureAchatUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await factureAchatComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last FactureAchat', async () => {
    const nbButtonsBeforeDelete = await factureAchatComponentsPage.countDeleteButtons();
    await factureAchatComponentsPage.clickOnLastDeleteButton();

    factureAchatDeleteDialog = new FactureAchatDeleteDialog();
    expect(await factureAchatDeleteDialog.getDialogTitle()).to.eq('myCrMv1App.factureAchat.delete.question');
    await factureAchatDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(factureAchatComponentsPage.title), 5000);

    expect(await factureAchatComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
