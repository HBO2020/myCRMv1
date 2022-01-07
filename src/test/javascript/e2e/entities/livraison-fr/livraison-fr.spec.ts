import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { LivraisonFrComponentsPage, LivraisonFrDeleteDialog, LivraisonFrUpdatePage } from './livraison-fr.page-object';

const expect = chai.expect;

describe('LivraisonFr e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let livraisonFrComponentsPage: LivraisonFrComponentsPage;
  let livraisonFrUpdatePage: LivraisonFrUpdatePage;
  let livraisonFrDeleteDialog: LivraisonFrDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load LivraisonFrs', async () => {
    await navBarPage.goToEntity('livraison-fr');
    livraisonFrComponentsPage = new LivraisonFrComponentsPage();
    await browser.wait(ec.visibilityOf(livraisonFrComponentsPage.title), 5000);
    expect(await livraisonFrComponentsPage.getTitle()).to.eq('myCrMv1App.livraisonFr.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(livraisonFrComponentsPage.entities), ec.visibilityOf(livraisonFrComponentsPage.noResult)),
      1000
    );
  });

  it('should load create LivraisonFr page', async () => {
    await livraisonFrComponentsPage.clickOnCreateButton();
    livraisonFrUpdatePage = new LivraisonFrUpdatePage();
    expect(await livraisonFrUpdatePage.getPageTitle()).to.eq('myCrMv1App.livraisonFr.home.createOrEditLabel');
    await livraisonFrUpdatePage.cancel();
  });

  it('should create and save LivraisonFrs', async () => {
    const nbButtonsBeforeCreate = await livraisonFrComponentsPage.countDeleteButtons();

    await livraisonFrComponentsPage.clickOnCreateButton();

    await promise.all([
      livraisonFrUpdatePage.setBonLivIdentInput('5'),
      livraisonFrUpdatePage.setLivFrDateInput('2000-12-31'),
      livraisonFrUpdatePage.setLivFrDateUpdateInput('2000-12-31'),
      livraisonFrUpdatePage.setLivDateEffetInput('2000-12-31'),
      livraisonFrUpdatePage.setBonLivTotalInput('5'),
    ]);

    await livraisonFrUpdatePage.save();
    expect(await livraisonFrUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await livraisonFrComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last LivraisonFr', async () => {
    const nbButtonsBeforeDelete = await livraisonFrComponentsPage.countDeleteButtons();
    await livraisonFrComponentsPage.clickOnLastDeleteButton();

    livraisonFrDeleteDialog = new LivraisonFrDeleteDialog();
    expect(await livraisonFrDeleteDialog.getDialogTitle()).to.eq('myCrMv1App.livraisonFr.delete.question');
    await livraisonFrDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(livraisonFrComponentsPage.title), 5000);

    expect(await livraisonFrComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
