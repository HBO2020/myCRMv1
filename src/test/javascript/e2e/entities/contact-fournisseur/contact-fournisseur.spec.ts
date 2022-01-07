import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ContactFournisseurComponentsPage,
  ContactFournisseurDeleteDialog,
  ContactFournisseurUpdatePage,
} from './contact-fournisseur.page-object';

const expect = chai.expect;

describe('ContactFournisseur e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let contactFournisseurComponentsPage: ContactFournisseurComponentsPage;
  let contactFournisseurUpdatePage: ContactFournisseurUpdatePage;
  let contactFournisseurDeleteDialog: ContactFournisseurDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ContactFournisseurs', async () => {
    await navBarPage.goToEntity('contact-fournisseur');
    contactFournisseurComponentsPage = new ContactFournisseurComponentsPage();
    await browser.wait(ec.visibilityOf(contactFournisseurComponentsPage.title), 5000);
    expect(await contactFournisseurComponentsPage.getTitle()).to.eq('myCrMv1App.contactFournisseur.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(contactFournisseurComponentsPage.entities), ec.visibilityOf(contactFournisseurComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ContactFournisseur page', async () => {
    await contactFournisseurComponentsPage.clickOnCreateButton();
    contactFournisseurUpdatePage = new ContactFournisseurUpdatePage();
    expect(await contactFournisseurUpdatePage.getPageTitle()).to.eq('myCrMv1App.contactFournisseur.home.createOrEditLabel');
    await contactFournisseurUpdatePage.cancel();
  });

  it('should create and save ContactFournisseurs', async () => {
    const nbButtonsBeforeCreate = await contactFournisseurComponentsPage.countDeleteButtons();

    await contactFournisseurComponentsPage.clickOnCreateButton();

    await promise.all([
      contactFournisseurUpdatePage.setContactFrNameInput('contactFrName'),
      contactFournisseurUpdatePage.setContactfrPrenomInput('contactfrPrenom'),
      contactFournisseurUpdatePage.setContactFrEmailInput('contactFrEmail'),
      contactFournisseurUpdatePage.setContactFrMobilePhoneInput('contactFrMobilePhone'),
      contactFournisseurUpdatePage.setContactFrStatusInput('contactFrStatus'),
      contactFournisseurUpdatePage.fournisseurSelectLastOption(),
    ]);

    await contactFournisseurUpdatePage.save();
    expect(await contactFournisseurUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await contactFournisseurComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ContactFournisseur', async () => {
    const nbButtonsBeforeDelete = await contactFournisseurComponentsPage.countDeleteButtons();
    await contactFournisseurComponentsPage.clickOnLastDeleteButton();

    contactFournisseurDeleteDialog = new ContactFournisseurDeleteDialog();
    expect(await contactFournisseurDeleteDialog.getDialogTitle()).to.eq('myCrMv1App.contactFournisseur.delete.question');
    await contactFournisseurDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(contactFournisseurComponentsPage.title), 5000);

    expect(await contactFournisseurComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
