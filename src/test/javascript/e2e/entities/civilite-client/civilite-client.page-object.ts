import { element, by, ElementFinder } from 'protractor';

export class CiviliteClientComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-civilite-client div table .btn-danger'));
  title = element.all(by.css('jhi-civilite-client div h2#page-heading span')).first();
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

export class CiviliteClientUpdatePage {
  pageTitle = element(by.id('jhi-civilite-client-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  civiliteLibelleClInput = element(by.id('field_civiliteLibelleCl'));
  civiliteCodeClInput = element(by.id('field_civiliteCodeCl'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setCiviliteLibelleClInput(civiliteLibelleCl: string): Promise<void> {
    await this.civiliteLibelleClInput.sendKeys(civiliteLibelleCl);
  }

  async getCiviliteLibelleClInput(): Promise<string> {
    return await this.civiliteLibelleClInput.getAttribute('value');
  }

  async setCiviliteCodeClInput(civiliteCodeCl: string): Promise<void> {
    await this.civiliteCodeClInput.sendKeys(civiliteCodeCl);
  }

  async getCiviliteCodeClInput(): Promise<string> {
    return await this.civiliteCodeClInput.getAttribute('value');
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

export class CiviliteClientDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-civiliteClient-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-civiliteClient'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
