import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  LigneLivFournisseurComponentsPage,
  LigneLivFournisseurDeleteDialog,
  LigneLivFournisseurUpdatePage,
} from './ligne-liv-fournisseur.page-object';

const expect = chai.expect;

describe('LigneLivFournisseur e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let ligneLivFournisseurComponentsPage: LigneLivFournisseurComponentsPage;
  let ligneLivFournisseurUpdatePage: LigneLivFournisseurUpdatePage;
  let ligneLivFournisseurDeleteDialog: LigneLivFournisseurDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LigneLivFournisseurs', async () => {
    await navBarPage.goToEntity('ligne-liv-fournisseur');
    ligneLivFournisseurComponentsPage = new LigneLivFournisseurComponentsPage();
    await browser.wait(ec.visibilityOf(ligneLivFournisseurComponentsPage.title), 5000);
    expect(await ligneLivFournisseurComponentsPage.getTitle()).to.eq('myCrMv1App.ligneLivFournisseur.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(ligneLivFournisseurComponentsPage.entities), ec.visibilityOf(ligneLivFournisseurComponentsPage.noResult)),
      1000
    );
  });

  it('should load create LigneLivFournisseur page', async () => {
    await ligneLivFournisseurComponentsPage.clickOnCreateButton();
    ligneLivFournisseurUpdatePage = new LigneLivFournisseurUpdatePage();
    expect(await ligneLivFournisseurUpdatePage.getPageTitle()).to.eq('myCrMv1App.ligneLivFournisseur.home.createOrEditLabel');
    await ligneLivFournisseurUpdatePage.cancel();
  });

  it('should create and save LigneLivFournisseurs', async () => {
    const nbButtonsBeforeCreate = await ligneLivFournisseurComponentsPage.countDeleteButtons();

    await ligneLivFournisseurComponentsPage.clickOnCreateButton();

    await promise.all([
      ligneLivFournisseurUpdatePage.setLivFrQuantiteInput('5'),
      ligneLivFournisseurUpdatePage.setLivFrNmPiecesInput('5'),
      ligneLivFournisseurUpdatePage.setLivFrTotalPrixInput('5'),
      ligneLivFournisseurUpdatePage.livraisonFrSelectLastOption(),
    ]);

    await ligneLivFournisseurUpdatePage.save();
    expect(await ligneLivFournisseurUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await ligneLivFournisseurComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last LigneLivFournisseur', async () => {
    const nbButtonsBeforeDelete = await ligneLivFournisseurComponentsPage.countDeleteButtons();
    await ligneLivFournisseurComponentsPage.clickOnLastDeleteButton();

    ligneLivFournisseurDeleteDialog = new LigneLivFournisseurDeleteDialog();
    expect(await ligneLivFournisseurDeleteDialog.getDialogTitle()).to.eq('myCrMv1App.ligneLivFournisseur.delete.question');
    await ligneLivFournisseurDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(ligneLivFournisseurComponentsPage.title), 5000);

    expect(await ligneLivFournisseurComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
