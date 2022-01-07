import { element, by, ElementFinder } from 'protractor';

export class CiviliteFournisseurComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-civilite-fournisseur div table .btn-danger'));
  title = element.all(by.css('jhi-civilite-fournisseur div h2#page-heading span')).first();
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

export class CiviliteFournisseurUpdatePage {
  pageTitle = element(by.id('jhi-civilite-fournisseur-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  civiliteFrLibelleInput = element(by.id('field_civiliteFrLibelle'));
  civiliteFrCodeInput = element(by.id('field_civiliteFrCode'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setCiviliteFrLibelleInput(civiliteFrLibelle: string): Promise<void> {
    await this.civiliteFrLibelleInput.sendKeys(civiliteFrLibelle);
  }

  async getCiviliteFrLibelleInput(): Promise<string> {
    return await this.civiliteFrLibelleInput.getAttribute('value');
  }

  async setCiviliteFrCodeInput(civiliteFrCode: string): Promise<void> {
    await this.civiliteFrCodeInput.sendKeys(civiliteFrCode);
  }

  async getCiviliteFrCodeInput(): Promise<string> {
    return await this.civiliteFrCodeInput.getAttribute('value');
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

export class CiviliteFournisseurDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-civiliteFournisseur-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-civiliteFournisseur'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
