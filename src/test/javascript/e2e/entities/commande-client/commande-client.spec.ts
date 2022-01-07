import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CommandeClientComponentsPage, CommandeClientDeleteDialog, CommandeClientUpdatePage } from './commande-client.page-object';

const expect = chai.expect;

describe('CommandeClient e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let commandeClientComponentsPage: CommandeClientComponentsPage;
  let commandeClientUpdatePage: CommandeClientUpdatePage;
  let commandeClientDeleteDialog: CommandeClientDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CommandeClients', async () => {
    await navBarPage.goToEntity('commande-client');
    commandeClientComponentsPage = new CommandeClientComponentsPage();
    await browser.wait(ec.visibilityOf(commandeClientComponentsPage.title), 5000);
    expect(await commandeClientComponentsPage.getTitle()).to.eq('myCrMv1App.commandeClient.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(commandeClientComponentsPage.entities), ec.visibilityOf(commandeClientComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CommandeClient page', async () => {
    await commandeClientComponentsPage.clickOnCreateButton();
    commandeClientUpdatePage = new CommandeClientUpdatePage();
    expect(await commandeClientUpdatePage.getPageTitle()).to.eq('myCrMv1App.commandeClient.home.createOrEditLabel');
    await commandeClientUpdatePage.cancel();
  });

  it('should create and save CommandeClients', async () => {
    const nbButtonsBeforeCreate = await commandeClientComponentsPage.countDeleteButtons();

    await commandeClientComponentsPage.clickOnCreateButton();

    await promise.all([
      commandeClientUpdatePage.setCmdIdenClInput('5'),
      commandeClientUpdatePage.setCmdDateEffetClInput('2000-12-31'),
      commandeClientUpdatePage.setCmdDateLivraisonClInput('2000-12-31'),
      commandeClientUpdatePage.clientSelectLastOption(),
      commandeClientUpdatePage.livraisonClSelectLastOption(),
    ]);

    await commandeClientUpdatePage.save();
    expect(await commandeClientUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await commandeClientComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CommandeClient', async () => {
    const nbButtonsBeforeDelete = await commandeClientComponentsPage.countDeleteButtons();
    await commandeClientComponentsPage.clickOnLastDeleteButton();

    commandeClientDeleteDialog = new CommandeClientDeleteDialog();
    expect(await commandeClientDeleteDialog.getDialogTitle()).to.eq('myCrMv1App.commandeClient.delete.question');
    await commandeClientDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(commandeClientComponentsPage.title), 5000);

    expect(await commandeClientComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
