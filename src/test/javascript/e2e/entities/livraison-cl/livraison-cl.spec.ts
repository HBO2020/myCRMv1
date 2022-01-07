import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LivraisonClComponentsPage, LivraisonClDeleteDialog, LivraisonClUpdatePage } from './livraison-cl.page-object';

const expect = chai.expect;

describe('LivraisonCl e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let livraisonClComponentsPage: LivraisonClComponentsPage;
  let livraisonClUpdatePage: LivraisonClUpdatePage;
  let livraisonClDeleteDialog: LivraisonClDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LivraisonCls', async () => {
    await navBarPage.goToEntity('livraison-cl');
    livraisonClComponentsPage = new LivraisonClComponentsPage();
    await browser.wait(ec.visibilityOf(livraisonClComponentsPage.title), 5000);
    expect(await livraisonClComponentsPage.getTitle()).to.eq('myCrMv1App.livraisonCl.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(livraisonClComponentsPage.entities), ec.visibilityOf(livraisonClComponentsPage.noResult)),
      1000
    );
  });

  it('should load create LivraisonCl page', async () => {
    await livraisonClComponentsPage.clickOnCreateButton();
    livraisonClUpdatePage = new LivraisonClUpdatePage();
    expect(await livraisonClUpdatePage.getPageTitle()).to.eq('myCrMv1App.livraisonCl.home.createOrEditLabel');
    await livraisonClUpdatePage.cancel();
  });

  it('should create and save LivraisonCls', async () => {
    const nbButtonsBeforeCreate = await livraisonClComponentsPage.countDeleteButtons();

    await livraisonClComponentsPage.clickOnCreateButton();

    await promise.all([
      livraisonClUpdatePage.setBonLivIdentClInput('5'),
      livraisonClUpdatePage.setLivDateClInput('2000-12-31'),
      livraisonClUpdatePage.setLivDateUpdateClInput('2000-12-31'),
      livraisonClUpdatePage.setLivDateEffetClInput('2000-12-31'),
      livraisonClUpdatePage.setBonLivTotalClInput('5'),
    ]);

    await livraisonClUpdatePage.save();
    expect(await livraisonClUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await livraisonClComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last LivraisonCl', async () => {
    const nbButtonsBeforeDelete = await livraisonClComponentsPage.countDeleteButtons();
    await livraisonClComponentsPage.clickOnLastDeleteButton();

    livraisonClDeleteDialog = new LivraisonClDeleteDialog();
    expect(await livraisonClDeleteDialog.getDialogTitle()).to.eq('myCrMv1App.livraisonCl.delete.question');
    await livraisonClDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(livraisonClComponentsPage.title), 5000);

    expect(await livraisonClComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
