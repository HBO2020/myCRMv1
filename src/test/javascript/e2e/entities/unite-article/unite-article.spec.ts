import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { UniteArticleComponentsPage, UniteArticleDeleteDialog, UniteArticleUpdatePage } from './unite-article.page-object';

const expect = chai.expect;

describe('UniteArticle e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let uniteArticleComponentsPage: UniteArticleComponentsPage;
  let uniteArticleUpdatePage: UniteArticleUpdatePage;
  let uniteArticleDeleteDialog: UniteArticleDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load UniteArticles', async () => {
    await navBarPage.goToEntity('unite-article');
    uniteArticleComponentsPage = new UniteArticleComponentsPage();
    await browser.wait(ec.visibilityOf(uniteArticleComponentsPage.title), 5000);
    expect(await uniteArticleComponentsPage.getTitle()).to.eq('myCrMv1App.uniteArticle.home.title');
    await browser.wait(
      ec.or(ec.visibilityOf(uniteArticleComponentsPage.entities), ec.visibilityOf(uniteArticleComponentsPage.noResult)),
      1000
    );
  });

  it('should load create UniteArticle page', async () => {
    await uniteArticleComponentsPage.clickOnCreateButton();
    uniteArticleUpdatePage = new UniteArticleUpdatePage();
    expect(await uniteArticleUpdatePage.getPageTitle()).to.eq('myCrMv1App.uniteArticle.home.createOrEditLabel');
    await uniteArticleUpdatePage.cancel();
  });

  it('should create and save UniteArticles', async () => {
    const nbButtonsBeforeCreate = await uniteArticleComponentsPage.countDeleteButtons();

    await uniteArticleComponentsPage.clickOnCreateButton();

    await promise.all([
      uniteArticleUpdatePage.setUniteCodeInput('5'),
      uniteArticleUpdatePage.setUniteLibelleInput('uniteLibelle'),
      uniteArticleUpdatePage.setUniteOptionInput('uniteOption'),
    ]);

    await uniteArticleUpdatePage.save();
    expect(await uniteArticleUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await uniteArticleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last UniteArticle', async () => {
    const nbButtonsBeforeDelete = await uniteArticleComponentsPage.countDeleteButtons();
    await uniteArticleComponentsPage.clickOnLastDeleteButton();

    uniteArticleDeleteDialog = new UniteArticleDeleteDialog();
    expect(await uniteArticleDeleteDialog.getDialogTitle()).to.eq('myCrMv1App.uniteArticle.delete.question');
    await uniteArticleDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(uniteArticleComponentsPage.title), 5000);

    expect(await uniteArticleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
