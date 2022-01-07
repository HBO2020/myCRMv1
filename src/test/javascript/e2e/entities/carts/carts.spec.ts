import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CartsComponentsPage, CartsDeleteDialog, CartsUpdatePage } from './carts.page-object';

const expect = chai.expect;

describe('Carts e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let cartsComponentsPage: CartsComponentsPage;
  let cartsUpdatePage: CartsUpdatePage;
  let cartsDeleteDialog: CartsDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Carts', async () => {
    await navBarPage.goToEntity('carts');
    cartsComponentsPage = new CartsComponentsPage();
    await browser.wait(ec.visibilityOf(cartsComponentsPage.title), 5000);
    expect(await cartsComponentsPage.getTitle()).to.eq('myCrMv1App.carts.home.title');
    await browser.wait(ec.or(ec.visibilityOf(cartsComponentsPage.entities), ec.visibilityOf(cartsComponentsPage.noResult)), 1000);
  });

  it('should load create Carts page', async () => {
    await cartsComponentsPage.clickOnCreateButton();
    cartsUpdatePage = new CartsUpdatePage();
    expect(await cartsUpdatePage.getPageTitle()).to.eq('myCrMv1App.carts.home.createOrEditLabel');
    await cartsUpdatePage.cancel();
  });

  it('should create and save Carts', async () => {
    const nbButtonsBeforeCreate = await cartsComponentsPage.countDeleteButtons();

    await cartsComponentsPage.clickOnCreateButton();

    await promise.all([
      cartsUpdatePage.getCartIsEmptyInput().click(),
      cartsUpdatePage.setCartUserEmailInput('cartUserEmail'),
      cartsUpdatePage.setCartListProductInput('cartListProduct'),
    ]);

    await cartsUpdatePage.save();
    expect(await cartsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await cartsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Carts', async () => {
    const nbButtonsBeforeDelete = await cartsComponentsPage.countDeleteButtons();
    await cartsComponentsPage.clickOnLastDeleteButton();

    cartsDeleteDialog = new CartsDeleteDialog();
    expect(await cartsDeleteDialog.getDialogTitle()).to.eq('myCrMv1App.carts.delete.question');
    await cartsDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(cartsComponentsPage.title), 5000);

    expect(await cartsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
