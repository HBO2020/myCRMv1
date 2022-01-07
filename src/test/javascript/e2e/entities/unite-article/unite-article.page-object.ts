import { element, by, ElementFinder } from 'protractor';

export class UniteArticleComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-unite-article div table .btn-danger'));
  title = element.all(by.css('jhi-unite-article div h2#page-heading span')).first();
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

export class UniteArticleUpdatePage {
  pageTitle = element(by.id('jhi-unite-article-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  uniteCodeInput = element(by.id('field_uniteCode'));
  uniteLibelleInput = element(by.id('field_uniteLibelle'));
  uniteOptionInput = element(by.id('field_uniteOption'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setUniteCodeInput(uniteCode: string): Promise<void> {
    await this.uniteCodeInput.sendKeys(uniteCode);
  }

  async getUniteCodeInput(): Promise<string> {
    return await this.uniteCodeInput.getAttribute('value');
  }

  async setUniteLibelleInput(uniteLibelle: string): Promise<void> {
    await this.uniteLibelleInput.sendKeys(uniteLibelle);
  }

  async getUniteLibelleInput(): Promise<string> {
    return await this.uniteLibelleInput.getAttribute('value');
  }

  async setUniteOptionInput(uniteOption: string): Promise<void> {
    await this.uniteOptionInput.sendKeys(uniteOption);
  }

  async getUniteOptionInput(): Promise<string> {
    return await this.uniteOptionInput.getAttribute('value');
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

export class UniteArticleDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-uniteArticle-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-uniteArticle'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
