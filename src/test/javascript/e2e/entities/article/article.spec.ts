import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ArticleComponentsPage, ArticleDeleteDialog, ArticleUpdatePage } from './article.page-object';
import path from 'path';

const expect = chai.expect;

describe('Article e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let articleComponentsPage: ArticleComponentsPage;
  let articleUpdatePage: ArticleUpdatePage;
  let articleDeleteDialog: ArticleDeleteDialog;
  const fileNameToUpload = 'logo-jhipster.png';
  const fileToUpload = '../../../../../../src/main/webapp/content/images/' + fileNameToUpload;
  const absolutePath = path.resolve(__dirname, fileToUpload);
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Articles', async () => {
    await navBarPage.goToEntity('article');
    articleComponentsPage = new ArticleComponentsPage();
    await browser.wait(ec.visibilityOf(articleComponentsPage.title), 5000);
    expect(await articleComponentsPage.getTitle()).to.eq('myCrMv1App.article.home.title');
    await browser.wait(ec.or(ec.visibilityOf(articleComponentsPage.entities), ec.visibilityOf(articleComponentsPage.noResult)), 1000);
  });

  it('should load create Article page', async () => {
    await articleComponentsPage.clickOnCreateButton();
    articleUpdatePage = new ArticleUpdatePage();
    expect(await articleUpdatePage.getPageTitle()).to.eq('myCrMv1App.article.home.createOrEditLabel');
    await articleUpdatePage.cancel();
  });

  it('should create and save Articles', async () => {
    const nbButtonsBeforeCreate = await articleComponentsPage.countDeleteButtons();

    await articleComponentsPage.clickOnCreateButton();

    await promise.all([
      articleUpdatePage.setArtclIdenInput('5'),
      articleUpdatePage.setArtclReferenceInput('artclReference'),
      articleUpdatePage.setArtclDesignationInput('artclDesignation'),
      articleUpdatePage.setArtclQnStockInput('5'),
      articleUpdatePage.setArtclImgInput(absolutePath),
      articleUpdatePage.setArtclSerieInput('artclSerie'),
      articleUpdatePage.setArtclPrixAchatInput('5'),
      articleUpdatePage.setArtclPxAchatTotalInput('5'),
      articleUpdatePage.setArtclPxVenteTotalInput('5'),
      articleUpdatePage.ligneCmdFournisseurSelectLastOption(),
      articleUpdatePage.ligneLivFournisseurSelectLastOption(),
      articleUpdatePage.uniteArticleSelectLastOption(),
      articleUpdatePage.ligneCmdClientSelectLastOption(),
      articleUpdatePage.ligneLivClientSelectLastOption(),
    ]);

    await articleUpdatePage.save();
    expect(await articleUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await articleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Article', async () => {
    const nbButtonsBeforeDelete = await articleComponentsPage.countDeleteButtons();
    await articleComponentsPage.clickOnLastDeleteButton();

    articleDeleteDialog = new ArticleDeleteDialog();
    expect(await articleDeleteDialog.getDialogTitle()).to.eq('myCrMv1App.article.delete.question');
    await articleDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(articleComponentsPage.title), 5000);

    expect(await articleComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
