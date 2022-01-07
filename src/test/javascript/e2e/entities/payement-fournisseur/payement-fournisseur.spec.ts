import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  PayementFournisseurComponentsPage,
  PayementFournisseurDeleteDialog,
  PayementFournisseurUpdatePage,
} from './payement-fournisseur.page-object';

const expect = chai.expect;

describe('PayementFournisseur e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let payementFournisseurComponentsPage: PayementFournisseurComponentsPage;
  let payementFournisseurUpdatePage: PayementFournisseurUpdatePage;
  let payementFournisseurDeleteDialog: PayementFournisseurDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load PayementFournisseurs', async () => {
    await navBarPage.goToEntity('payement-fournisseur');
    payementFournisseurComponentsPage = new PayementFournisseurComponentsPage();
    await browser.wait(ec.visibilityOf(payementFournisseurComponentsPage.title), 5000);
    expect(await payementFournisseurComponentsPage.getTitle()).to.eq('myCrMv1App.payementFournisseur.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(payementFournisseurComponentsPage.entities), ec.visibilityOf(payementFournisseurComponentsPage.noResult)),
      1000
    );
  });

  it('should load create PayementFournisseur page', async () => {
    await payementFournisseurComponentsPage.clickOnCreateButton();
    payementFournisseurUpdatePage = new PayementFournisseurUpdatePage();
    expect(await payementFournisseurUpdatePage.getPageTitle()).to.eq('myCrMv1App.payementFournisseur.home.createOrEditLabel');
    await payementFournisseurUpdatePage.cancel();
  });

  it('should create and save PayementFournisseurs', async () => {
    const nbButtonsBeforeCreate = await payementFournisseurComponentsPage.countDeleteButtons();

    await payementFournisseurComponentsPage.clickOnCreateButton();

    await promise.all([
      payementFournisseurUpdatePage.setPayementFrIdentInput('5'),
      payementFournisseurUpdatePage.setPayementFrDateInput('2000-12-31'),
      payementFournisseurUpdatePage.setPayementFrModeInput('payementFrMode'),
      payementFournisseurUpdatePage.setPayementFrEcheanceInput('2000-12-31'),
      payementFournisseurUpdatePage.setPayementFrMontantInput('5'),
    ]);

    await payementFournisseurUpdatePage.save();
    expect(await payementFournisseurUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await payementFournisseurComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last PayementFournisseur', async () => {
    const nbButtonsBeforeDelete = await payementFournisseurComponentsPage.countDeleteButtons();
    await payementFournisseurComponentsPage.clickOnLastDeleteButton();

    payementFournisseurDeleteDialog = new PayementFournisseurDeleteDialog();
    expect(await payementFournisseurDeleteDialog.getDialogTitle()).to.eq('myCrMv1App.payementFournisseur.delete.question');
    await payementFournisseurDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(payementFournisseurComponentsPage.title), 5000);

    expect(await payementFournisseurComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
