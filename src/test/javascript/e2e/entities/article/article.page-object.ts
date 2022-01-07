import { element, by, ElementFinder } from 'protractor';

export class ArticleComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-article div table .btn-danger'));
  title = element.all(by.css('jhi-article div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class ArticleUpdatePage {
  pageTitle = element(by.id('jhi-article-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  artclIdenInput = element(by.id('field_artclIden'));
  artclReferenceInput = element(by.id('field_artclReference'));
  artclDesignationInput = element(by.id('field_artclDesignation'));
  artclQnStockInput = element(by.id('field_artclQnStock'));
  artclImgInput = element(by.id('file_artclImg'));
  artclSerieInput = element(by.id('field_artclSerie'));
  artclPrixAchatInput = element(by.id('field_artclPrixAchat'));
  artclPxAchatTotalInput = element(by.id('field_artclPxAchatTotal'));
  artclPxVenteTotalInput = element(by.id('field_artclPxVenteTotal'));

  ligneCmdFournisseurSelect = element(by.id('field_ligneCmdFournisseur'));
  ligneLivFournisseurSelect = element(by.id('field_ligneLivFournisseur'));
  uniteArticleSelect = element(by.id('field_uniteArticle'));
  ligneCmdClientSelect = element(by.id('field_ligneCmdClient'));
  ligneLivClientSelect = element(by.id('field_ligneLivClient'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setArtclIdenInput(artclIden: string): Promise<void> {
    await this.artclIdenInput.sendKeys(artclIden);
  }

  async getArtclIdenInput(): Promise<string> {
    return await this.artclIdenInput.getAttribute('value');
  }

  async setArtclReferenceInput(artclReference: string): Promise<void> {
    await this.artclReferenceInput.sendKeys(artclReference);
  }

  async getArtclReferenceInput(): Promise<string> {
    return await this.artclReferenceInput.getAttribute('value');
  }

  async setArtclDesignationInput(artclDesignation: string): Promise<void> {
    await this.artclDesignationInput.sendKeys(artclDesignation);
  }

  async getArtclDesignationInput(): Promise<string> {
    return await this.artclDesignationInput.getAttribute('value');
  }

  async setArtclQnStockInput(artclQnStock: string): Promise<void> {
    await this.artclQnStockInput.sendKeys(artclQnStock);
  }

  async getArtclQnStockInput(): Promise<string> {
    return await this.artclQnStockInput.getAttribute('value');
  }

  async setArtclImgInput(artclImg: string): Promise<void> {
    await this.artclImgInput.sendKeys(artclImg);
  }

  async getArtclImgInput(): Promise<string> {
    return await this.artclImgInput.getAttribute('value');
  }

  async setArtclSerieInput(artclSerie: string): Promise<void> {
    await this.artclSerieInput.sendKeys(artclSerie);
  }

  async getArtclSerieInput(): Promise<string> {
    return await this.artclSerieInput.getAttribute('value');
  }

  async setArtclPrixAchatInput(artclPrixAchat: string): Promise<void> {
    await this.artclPrixAchatInput.sendKeys(artclPrixAchat);
  }

  async getArtclPrixAchatInput(): Promise<string> {
    return await this.artclPrixAchatInput.getAttribute('value');
  }

  async setArtclPxAchatTotalInput(artclPxAchatTotal: string): Promise<void> {
    await this.artclPxAchatTotalInput.sendKeys(artclPxAchatTotal);
  }

  async getArtclPxAchatTotalInput(): Promise<string> {
    return await this.artclPxAchatTotalInput.getAttribute('value');
  }

  async setArtclPxVenteTotalInput(artclPxVenteTotal: string): Promise<void> {
    await this.artclPxVenteTotalInput.sendKeys(artclPxVenteTotal);
  }

  async getArtclPxVenteTotalInput(): Promise<string> {
    return await this.artclPxVenteTotalInput.getAttribute('value');
  }

  async ligneCmdFournisseurSelectLastOption(): Promise<void> {
    await this.ligneCmdFournisseurSelect.all(by.tagName('option')).last().click();
  }

  async ligneCmdFournisseurSelectOption(option: string): Promise<void> {
    await this.ligneCmdFournisseurSelect.sendKeys(option);
  }

  getLigneCmdFournisseurSelect(): ElementFinder {
    return this.ligneCmdFournisseurSelect;
  }

  async getLigneCmdFournisseurSelectedOption(): Promise<string> {
    return await this.ligneCmdFournisseurSelect.element(by.css('option:checked')).getText();
  }

  async ligneLivFournisseurSelectLastOption(): Promise<void> {
    await this.ligneLivFournisseurSelect.all(by.tagName('option')).last().click();
  }

  async ligneLivFournisseurSelectOption(option: string): Promise<void> {
    await this.ligneLivFournisseurSelect.sendKeys(option);
  }

  getLigneLivFournisseurSelect(): ElementFinder {
    return this.ligneLivFournisseurSelect;
  }

  async getLigneLivFournisseurSelectedOption(): Promise<string> {
    return await this.ligneLivFournisseurSelect.element(by.css('option:checked')).getText();
  }

  async uniteArticleSelectLastOption(): Promise<void> {
    await this.uniteArticleSelect.all(by.tagName('option')).last().click();
  }

  async uniteArticleSelectOption(option: string): Promise<void> {
    await this.uniteArticleSelect.sendKeys(option);
  }

  getUniteArticleSelect(): ElementFinder {
    return this.uniteArticleSelect;
  }

  async getUniteArticleSelectedOption(): Promise<string> {
    return await this.uniteArticleSelect.element(by.css('option:checked')).getText();
  }

  async ligneCmdClientSelectLastOption(): Promise<void> {
    await this.ligneCmdClientSelect.all(by.tagName('option')).last().click();
  }

  async ligneCmdClientSelectOption(option: string): Promise<void> {
    await this.ligneCmdClientSelect.sendKeys(option);
  }

  getLigneCmdClientSelect(): ElementFinder {
    return this.ligneCmdClientSelect;
  }

  async getLigneCmdClientSelectedOption(): Promise<string> {
    return await this.ligneCmdClientSelect.element(by.css('option:checked')).getText();
  }

  async ligneLivClientSelectLastOption(): Promise<void> {
    await this.ligneLivClientSelect.all(by.tagName('option')).last().click();
  }

  async ligneLivClientSelectOption(option: string): Promise<void> {
    await this.ligneLivClientSelect.sendKeys(option);
  }

  getLigneLivClientSelect(): ElementFinder {
    return this.ligneLivClientSelect;
  }

  async getLigneLivClientSelectedOption(): Promise<string> {
    return await this.ligneLivClientSelect.element(by.css('option:checked')).getText();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ArticleDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-article-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-article'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
