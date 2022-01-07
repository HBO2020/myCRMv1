import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  LigneCmdFournisseurComponentsPage,
  LigneCmdFournisseurDeleteDialog,
  LigneCmdFournisseurUpdatePage,
} from './ligne-cmd-fournisseur.page-object';

const expect = chai.expect;

describe('LigneCmdFournisseur e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let ligneCmdFournisseurComponentsPage: LigneCmdFournisseurComponentsPage;
  let ligneCmdFournisseurUpdatePage: LigneCmdFournisseurUpdatePage;
  let ligneCmdFournisseurDeleteDialog: LigneCmdFournisseurDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LigneCmdFournisseurs', async () => {
    await navBarPage.goToEntity('ligne-cmd-fournisseur');
    ligneCmdFournisseurComponentsPage = new LigneCmdFournisseurComponentsPage();
    await browser.wait(ec.visibilityOf(ligneCmdFournisseurComponentsPage.title), 5000);
    expect(await ligneCmdFournisseurComponentsPage.getTitle()).to.eq('myCrMv1App.ligneCmdFournisseur.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(ligneCmdFournisseurComponentsPage.entities), ec.visibilityOf(ligneCmdFournisseurComponentsPage.noResult)),
      1000
    );
  });

  it('should load create LigneCmdFournisseur page', async () => {
    await ligneCmdFournisseurComponentsPage.clickOnCreateButton();
    ligneCmdFournisseurUpdatePage = new LigneCmdFournisseurUpdatePage();
    expect(await ligneCmdFournisseurUpdatePage.getPageTitle()).to.eq('myCrMv1App.ligneCmdFournisseur.home.createOrEditLabel');
    await ligneCmdFournisseurUpdatePage.cancel();
  });

  it('should create and save LigneCmdFournisseurs', async () => {
    const nbButtonsBeforeCreate = await ligneCmdFournisseurComponentsPage.countDeleteButtons();

    await ligneCmdFournisseurComponentsPage.clickOnCreateButton();

    await promise.all([
      ligneCmdFournisseurUpdatePage.setCmdQnFrInput('5'),
      ligneCmdFournisseurUpdatePage.setCmdNmPiecesInput('5'),
      ligneCmdFournisseurUpdatePage.commandeFourniseurSelectLastOption(),
    ]);

    await ligneCmdFournisseurUpdatePage.save();
    expect(await ligneCmdFournisseurUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await ligneCmdFournisseurComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last LigneCmdFournisseur', async () => {
    const nbButtonsBeforeDelete = await ligneCmdFournisseurComponentsPage.countDeleteButtons();
    await ligneCmdFournisseurComponentsPage.clickOnLastDeleteButton();

    ligneCmdFournisseurDeleteDialog = new LigneCmdFournisseurDeleteDialog();
    expect(await ligneCmdFournisseurDeleteDialog.getDialogTitle()).to.eq('myCrMv1App.ligneCmdFournisseur.delete.question');
    await ligneCmdFournisseurDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(ligneCmdFournisseurComponentsPage.title), 5000);

    expect(await ligneCmdFournisseurComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
