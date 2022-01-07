import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LigneCmdClientComponentsPage, LigneCmdClientDeleteDialog, LigneCmdClientUpdatePage } from './ligne-cmd-client.page-object';

const expect = chai.expect;

describe('LigneCmdClient e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let ligneCmdClientComponentsPage: LigneCmdClientComponentsPage;
  let ligneCmdClientUpdatePage: LigneCmdClientUpdatePage;
  let ligneCmdClientDeleteDialog: LigneCmdClientDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LigneCmdClients', async () => {
    await navBarPage.goToEntity('ligne-cmd-client');
    ligneCmdClientComponentsPage = new LigneCmdClientComponentsPage();
    await browser.wait(ec.visibilityOf(ligneCmdClientComponentsPage.title), 5000);
    expect(await ligneCmdClientComponentsPage.getTitle()).to.eq('myCrMv1App.ligneCmdClient.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(ligneCmdClientComponentsPage.entities), ec.visibilityOf(ligneCmdClientComponentsPage.noResult)),
      1000
    );
  });

  it('should load create LigneCmdClient page', async () => {
    await ligneCmdClientComponentsPage.clickOnCreateButton();
    ligneCmdClientUpdatePage = new LigneCmdClientUpdatePage();
    expect(await ligneCmdClientUpdatePage.getPageTitle()).to.eq('myCrMv1App.ligneCmdClient.home.createOrEditLabel');
    await ligneCmdClientUpdatePage.cancel();
  });

  it('should create and save LigneCmdClients', async () => {
    const nbButtonsBeforeCreate = await ligneCmdClientComponentsPage.countDeleteButtons();

    await ligneCmdClientComponentsPage.clickOnCreateButton();

    await promise.all([
      ligneCmdClientUpdatePage.setCmdQnClInput('5'),
      ligneCmdClientUpdatePage.setCmdNmPiecesClInput('5'),
      ligneCmdClientUpdatePage.commandeClientSelectLastOption(),
    ]);

    await ligneCmdClientUpdatePage.save();
    expect(await ligneCmdClientUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await ligneCmdClientComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last LigneCmdClient', async () => {
    const nbButtonsBeforeDelete = await ligneCmdClientComponentsPage.countDeleteButtons();
    await ligneCmdClientComponentsPage.clickOnLastDeleteButton();

    ligneCmdClientDeleteDialog = new LigneCmdClientDeleteDialog();
    expect(await ligneCmdClientDeleteDialog.getDialogTitle()).to.eq('myCrMv1App.ligneCmdClient.delete.question');
    await ligneCmdClientDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(ligneCmdClientComponentsPage.title), 5000);

    expect(await ligneCmdClientComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
