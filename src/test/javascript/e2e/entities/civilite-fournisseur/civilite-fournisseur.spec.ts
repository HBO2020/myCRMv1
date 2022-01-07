import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CiviliteFournisseurComponentsPage,
  CiviliteFournisseurDeleteDialog,
  CiviliteFournisseurUpdatePage,
} from './civilite-fournisseur.page-object';

const expect = chai.expect;

describe('CiviliteFournisseur e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let civiliteFournisseurComponentsPage: CiviliteFournisseurComponentsPage;
  let civiliteFournisseurUpdatePage: CiviliteFournisseurUpdatePage;
  let civiliteFournisseurDeleteDialog: CiviliteFournisseurDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CiviliteFournisseurs', async () => {
    await navBarPage.goToEntity('civilite-fournisseur');
    civiliteFournisseurComponentsPage = new CiviliteFournisseurComponentsPage();
    await browser.wait(ec.visibilityOf(civiliteFournisseurComponentsPage.title), 5000);
    expect(await civiliteFournisseurComponentsPage.getTitle()).to.eq('myCrMv1App.civiliteFournisseur.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(civiliteFournisseurComponentsPage.entities), ec.visibilityOf(civiliteFournisseurComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CiviliteFournisseur page', async () => {
    await civiliteFournisseurComponentsPage.clickOnCreateButton();
    civiliteFournisseurUpdatePage = new CiviliteFournisseurUpdatePage();
    expect(await civiliteFournisseurUpdatePage.getPageTitle()).to.eq('myCrMv1App.civiliteFournisseur.home.createOrEditLabel');
    await civiliteFournisseurUpdatePage.cancel();
  });

  it('should create and save CiviliteFournisseurs', async () => {
    const nbButtonsBeforeCreate = await civiliteFournisseurComponentsPage.countDeleteButtons();

    await civiliteFournisseurComponentsPage.clickOnCreateButton();

    await promise.all([
      civiliteFournisseurUpdatePage.setCiviliteFrLibelleInput('civiliteFrLibelle'),
      civiliteFournisseurUpdatePage.setCiviliteFrCodeInput('5'),
    ]);

    await civiliteFournisseurUpdatePage.save();
    expect(await civiliteFournisseurUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await civiliteFournisseurComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CiviliteFournisseur', async () => {
    const nbButtonsBeforeDelete = await civiliteFournisseurComponentsPage.countDeleteButtons();
    await civiliteFournisseurComponentsPage.clickOnLastDeleteButton();

    civiliteFournisseurDeleteDialog = new CiviliteFournisseurDeleteDialog();
    expect(await civiliteFournisseurDeleteDialog.getDialogTitle()).to.eq('myCrMv1App.civiliteFournisseur.delete.question');
    await civiliteFournisseurDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(civiliteFournisseurComponentsPage.title), 5000);

    expect(await civiliteFournisseurComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
