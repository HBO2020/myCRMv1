import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PayementClientComponentsPage, PayementClientDeleteDialog, PayementClientUpdatePage } from './payement-client.page-object';

const expect = chai.expect;

describe('PayementClient e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let payementClientComponentsPage: PayementClientComponentsPage;
  let payementClientUpdatePage: PayementClientUpdatePage;
  let payementClientDeleteDialog: PayementClientDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PayementClients', async () => {
    await navBarPage.goToEntity('payement-client');
    payementClientComponentsPage = new PayementClientComponentsPage();
    await browser.wait(ec.visibilityOf(payementClientComponentsPage.title), 5000);
    expect(await payementClientComponentsPage.getTitle()).to.eq('myCrMv1App.payementClient.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(payementClientComponentsPage.entities), ec.visibilityOf(payementClientComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PayementClient page', async () => {
    await payementClientComponentsPage.clickOnCreateButton();
    payementClientUpdatePage = new PayementClientUpdatePage();
    expect(await payementClientUpdatePage.getPageTitle()).to.eq('myCrMv1App.payementClient.home.createOrEditLabel');
    await payementClientUpdatePage.cancel();
  });

  it('should create and save PayementClients', async () => {
    const nbButtonsBeforeCreate = await payementClientComponentsPage.countDeleteButtons();

    await payementClientComponentsPage.clickOnCreateButton();

    await promise.all([
      payementClientUpdatePage.setPayementClIdentInput('5'),
      payementClientUpdatePage.setPayementClDateInput('2000-12-31'),
      payementClientUpdatePage.setPayementClModeInput('payementClMode'),
      payementClientUpdatePage.setPayementClEcheanceInput('2000-12-31'),
      payementClientUpdatePage.setPayementClMontantInput('5'),
    ]);

    await payementClientUpdatePage.save();
    expect(await payementClientUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await payementClientComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PayementClient', async () => {
    const nbButtonsBeforeDelete = await payementClientComponentsPage.countDeleteButtons();
    await payementClientComponentsPage.clickOnLastDeleteButton();

    payementClientDeleteDialog = new PayementClientDeleteDialog();
    expect(await payementClientDeleteDialog.getDialogTitle()).to.eq('myCrMv1App.payementClient.delete.question');
    await payementClientDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(payementClientComponentsPage.title), 5000);

    expect(await payementClientComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
