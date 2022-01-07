import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CiviliteClientComponentsPage, CiviliteClientDeleteDialog, CiviliteClientUpdatePage } from './civilite-client.page-object';

const expect = chai.expect;

describe('CiviliteClient e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let civiliteClientComponentsPage: CiviliteClientComponentsPage;
  let civiliteClientUpdatePage: CiviliteClientUpdatePage;
  let civiliteClientDeleteDialog: CiviliteClientDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load CiviliteClients', async () => {
    await navBarPage.goToEntity('civilite-client');
    civiliteClientComponentsPage = new CiviliteClientComponentsPage();
    await browser.wait(ec.visibilityOf(civiliteClientComponentsPage.title), 5000);
    expect(await civiliteClientComponentsPage.getTitle()).to.eq('myCrMv1App.civiliteClient.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(civiliteClientComponentsPage.entities), ec.visibilityOf(civiliteClientComponentsPage.noResult)),
      1000
    );
  });

  it('should load create CiviliteClient page', async () => {
    await civiliteClientComponentsPage.clickOnCreateButton();
    civiliteClientUpdatePage = new CiviliteClientUpdatePage();
    expect(await civiliteClientUpdatePage.getPageTitle()).to.eq('myCrMv1App.civiliteClient.home.createOrEditLabel');
    await civiliteClientUpdatePage.cancel();
  });

  it('should create and save CiviliteClients', async () => {
    const nbButtonsBeforeCreate = await civiliteClientComponentsPage.countDeleteButtons();

    await civiliteClientComponentsPage.clickOnCreateButton();

    await promise.all([
      civiliteClientUpdatePage.setCiviliteLibelleClInput('civiliteLibelleCl'),
      civiliteClientUpdatePage.setCiviliteCodeClInput('5'),
    ]);

    await civiliteClientUpdatePage.save();
    expect(await civiliteClientUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await civiliteClientComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last CiviliteClient', async () => {
    const nbButtonsBeforeDelete = await civiliteClientComponentsPage.countDeleteButtons();
    await civiliteClientComponentsPage.clickOnLastDeleteButton();

    civiliteClientDeleteDialog = new CiviliteClientDeleteDialog();
    expect(await civiliteClientDeleteDialog.getDialogTitle()).to.eq('myCrMv1App.civiliteClient.delete.question');
    await civiliteClientDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(civiliteClientComponentsPage.title), 5000);

    expect(await civiliteClientComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
