import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ContactClientComponentsPage, ContactClientDeleteDialog, ContactClientUpdatePage } from './contact-client.page-object';

const expect = chai.expect;

describe('ContactClient e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let contactClientComponentsPage: ContactClientComponentsPage;
  let contactClientUpdatePage: ContactClientUpdatePage;
  let contactClientDeleteDialog: ContactClientDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ContactClients', async () => {
    await navBarPage.goToEntity('contact-client');
    contactClientComponentsPage = new ContactClientComponentsPage();
    await browser.wait(ec.visibilityOf(contactClientComponentsPage.title), 5000);
    expect(await contactClientComponentsPage.getTitle()).to.eq('myCrMv1App.contactClient.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(contactClientComponentsPage.entities), ec.visibilityOf(contactClientComponentsPage.noResult)),
      1000
    );
  });

  it('should load create ContactClient page', async () => {
    await contactClientComponentsPage.clickOnCreateButton();
    contactClientUpdatePage = new ContactClientUpdatePage();
    expect(await contactClientUpdatePage.getPageTitle()).to.eq('myCrMv1App.contactClient.home.createOrEditLabel');
    await contactClientUpdatePage.cancel();
  });

  it('should create and save ContactClients', async () => {
    const nbButtonsBeforeCreate = await contactClientComponentsPage.countDeleteButtons();

    await contactClientComponentsPage.clickOnCreateButton();

    await promise.all([
      contactClientUpdatePage.setContactNameClInput('contactNameCl'),
      contactClientUpdatePage.setContactPrenomClInput('contactPrenomCl'),
      contactClientUpdatePage.setContactEmailClInput('contactEmailCl'),
      contactClientUpdatePage.setContactMobilePhoneClInput('contactMobilePhoneCl'),
      contactClientUpdatePage.setContactStatusClInput('contactStatusCl'),
      contactClientUpdatePage.clientSelectLastOption(),
    ]);

    await contactClientUpdatePage.save();
    expect(await contactClientUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await contactClientComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last ContactClient', async () => {
    const nbButtonsBeforeDelete = await contactClientComponentsPage.countDeleteButtons();
    await contactClientComponentsPage.clickOnLastDeleteButton();

    contactClientDeleteDialog = new ContactClientDeleteDialog();
    expect(await contactClientDeleteDialog.getDialogTitle()).to.eq('myCrMv1App.contactClient.delete.question');
    await contactClientDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(contactClientComponentsPage.title), 5000);

    expect(await contactClientComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
