import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  CommandeFournisseurComponentsPage,
  CommandeFournisseurDeleteDialog,
  CommandeFournisseurUpdatePage,
} from './commande-fournisseur.page-object';

const expect = chai.expect;

describe('CommandeFournisseur e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let commandeFournisseurComponentsPage: CommandeFournisseurComponentsPage;
  let commandeFournisseurUpdatePage: CommandeFournisseurUpdatePage;
  let commandeFournisseurDeleteDialog: CommandeFournisseurDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CommandeFournisseurs', async () => {
    await navBarPage.goToEntity('commande-fournisseur');
    commandeFournisseurComponentsPage = new CommandeFournisseurComponentsPage();
    await browser.wait(ec.visibilityOf(commandeFournisseurComponentsPage.title), 5000);
    expect(await commandeFournisseurComponentsPage.getTitle()).to.eq('myCrMv1App.commandeFournisseur.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(commandeFournisseurComponentsPage.entities), ec.visibilityOf(commandeFournisseurComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CommandeFournisseur page', async () => {
    await commandeFournisseurComponentsPage.clickOnCreateButton();
    commandeFournisseurUpdatePage = new CommandeFournisseurUpdatePage();
    expect(await commandeFournisseurUpdatePage.getPageTitle()).to.eq('myCrMv1App.commandeFournisseur.home.createOrEditLabel');
    await commandeFournisseurUpdatePage.cancel();
  });

  it('should create and save CommandeFournisseurs', async () => {
    const nbButtonsBeforeCreate = await commandeFournisseurComponentsPage.countDeleteButtons();

    await commandeFournisseurComponentsPage.clickOnCreateButton();

    await promise.all([
      commandeFournisseurUpdatePage.setCmdIdenFrInput('5'),
      commandeFournisseurUpdatePage.setCmdDateEffetFrInput('2000-12-31'),
      commandeFournisseurUpdatePage.setCmdDateLivraisonFrInput('2000-12-31'),
      commandeFournisseurUpdatePage.fournisseurSelectLastOption(),
      commandeFournisseurUpdatePage.livraisonFrSelectLastOption(),
    ]);

    await commandeFournisseurUpdatePage.save();
    expect(await commandeFournisseurUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await commandeFournisseurComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CommandeFournisseur', async () => {
    const nbButtonsBeforeDelete = await commandeFournisseurComponentsPage.countDeleteButtons();
    await commandeFournisseurComponentsPage.clickOnLastDeleteButton();

    commandeFournisseurDeleteDialog = new CommandeFournisseurDeleteDialog();
    expect(await commandeFournisseurDeleteDialog.getDialogTitle()).to.eq('myCrMv1App.commandeFournisseur.delete.question');
    await commandeFournisseurDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(commandeFournisseurComponentsPage.title), 5000);

    expect(await commandeFournisseurComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
