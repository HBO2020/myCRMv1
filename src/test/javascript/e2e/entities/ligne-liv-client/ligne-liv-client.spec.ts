import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LigneLivClientComponentsPage, LigneLivClientDeleteDialog, LigneLivClientUpdatePage } from './ligne-liv-client.page-object';

const expect = chai.expect;

describe('LigneLivClient e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let ligneLivClientComponentsPage: LigneLivClientComponentsPage;
  let ligneLivClientUpdatePage: LigneLivClientUpdatePage;
  let ligneLivClientDeleteDialog: LigneLivClientDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LigneLivClients', async () => {
    await navBarPage.goToEntity('ligne-liv-client');
    ligneLivClientComponentsPage = new LigneLivClientComponentsPage();
    await browser.wait(ec.visibilityOf(ligneLivClientComponentsPage.title), 5000);
    expect(await ligneLivClientComponentsPage.getTitle()).to.eq('myCrMv1App.ligneLivClient.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(ligneLivClientComponentsPage.entities), ec.visibilityOf(ligneLivClientComponentsPage.noResult)),
      1000
    );
  });

  it('should load create LigneLivClient page', async () => {
    await ligneLivClientComponentsPage.clickOnCreateButton();
    ligneLivClientUpdatePage = new LigneLivClientUpdatePage();
    expect(await ligneLivClientUpdatePage.getPageTitle()).to.eq('myCrMv1App.ligneLivClient.home.createOrEditLabel');
    await ligneLivClientUpdatePage.cancel();
  });

  it('should create and save LigneLivClients', async () => {
    const nbButtonsBeforeCreate = await ligneLivClientComponentsPage.countDeleteButtons();

    await ligneLivClientComponentsPage.clickOnCreateButton();

    await promise.all([
      ligneLivClientUpdatePage.setLivQuantiteClInput('5'),
      ligneLivClientUpdatePage.setLivNmPiecesClInput('5'),
      ligneLivClientUpdatePage.setLivTotalPrixClInput('5'),
      ligneLivClientUpdatePage.livraisonClSelectLastOption(),
    ]);

    await ligneLivClientUpdatePage.save();
    expect(await ligneLivClientUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await ligneLivClientComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last LigneLivClient', async () => {
    const nbButtonsBeforeDelete = await ligneLivClientComponentsPage.countDeleteButtons();
    await ligneLivClientComponentsPage.clickOnLastDeleteButton();

    ligneLivClientDeleteDialog = new LigneLivClientDeleteDialog();
    expect(await ligneLivClientDeleteDialog.getDialogTitle()).to.eq('myCrMv1App.ligneLivClient.delete.question');
    await ligneLivClientDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(ligneLivClientComponentsPage.title), 5000);

    expect(await ligneLivClientComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
