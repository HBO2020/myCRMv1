import { element, by, ElementFinder } from 'protractor';

export class CommandeFournisseurComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-commande-fournisseur div table .btn-danger'));
  title = element.all(by.css('jhi-commande-fournisseur div h2#page-heading span')).first();
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

export class CommandeFournisseurUpdatePage {
  pageTitle = element(by.id('jhi-commande-fournisseur-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  cmdIdenFrInput = element(by.id('field_cmdIdenFr'));
  cmdDateEffetFrInput = element(by.id('field_cmdDateEffetFr'));
  cmdDateLivraisonFrInput = element(by.id('field_cmdDateLivraisonFr'));

  fournisseurSelect = element(by.id('field_fournisseur'));
  livraisonFrSelect = element(by.id('field_livraisonFr'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setCmdIdenFrInput(cmdIdenFr: string): Promise<void> {
    await this.cmdIdenFrInput.sendKeys(cmdIdenFr);
  }

  async getCmdIdenFrInput(): Promise<string> {
    return await this.cmdIdenFrInput.getAttribute('value');
  }

  async setCmdDateEffetFrInput(cmdDateEffetFr: string): Promise<void> {
    await this.cmdDateEffetFrInput.sendKeys(cmdDateEffetFr);
  }

  async getCmdDateEffetFrInput(): Promise<string> {
    return await this.cmdDateEffetFrInput.getAttribute('value');
  }

  async setCmdDateLivraisonFrInput(cmdDateLivraisonFr: string): Promise<void> {
    await this.cmdDateLivraisonFrInput.sendKeys(cmdDateLivraisonFr);
  }

  async getCmdDateLivraisonFrInput(): Promise<string> {
    return await this.cmdDateLivraisonFrInput.getAttribute('value');
  }

  async fournisseurSelectLastOption(): Promise<void> {
    await this.fournisseurSelect.all(by.tagName('option')).last().click();
  }

  async fournisseurSelectOption(option: string): Promise<void> {
    await this.fournisseurSelect.sendKeys(option);
  }

  getFournisseurSelect(): ElementFinder {
    return this.fournisseurSelect;
  }

  async getFournisseurSelectedOption(): Promise<string> {
    return await this.fournisseurSelect.element(by.css('option:checked')).getText();
  }

  async livraisonFrSelectLastOption(): Promise<void> {
    await this.livraisonFrSelect.all(by.tagName('option')).last().click();
  }

  async livraisonFrSelectOption(option: string): Promise<void> {
    await this.livraisonFrSelect.sendKeys(option);
  }

  getLivraisonFrSelect(): ElementFinder {
    return this.livraisonFrSelect;
  }

  async getLivraisonFrSelectedOption(): Promise<string> {
    return await this.livraisonFrSelect.element(by.css('option:checked')).getText();
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

export class CommandeFournisseurDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-commandeFournisseur-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-commandeFournisseur'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
