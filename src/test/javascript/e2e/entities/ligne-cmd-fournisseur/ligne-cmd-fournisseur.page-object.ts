import { element, by, ElementFinder } from 'protractor';

export class LigneCmdFournisseurComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-ligne-cmd-fournisseur div table .btn-danger'));
  title = element.all(by.css('jhi-ligne-cmd-fournisseur div h2#page-heading span')).first();
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

export class LigneCmdFournisseurUpdatePage {
  pageTitle = element(by.id('jhi-ligne-cmd-fournisseur-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  cmdQnFrInput = element(by.id('field_cmdQnFr'));
  cmdNmPiecesInput = element(by.id('field_cmdNmPieces'));

  commandeFourniseurSelect = element(by.id('field_commandeFourniseur'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setCmdQnFrInput(cmdQnFr: string): Promise<void> {
    await this.cmdQnFrInput.sendKeys(cmdQnFr);
  }

  async getCmdQnFrInput(): Promise<string> {
    return await this.cmdQnFrInput.getAttribute('value');
  }

  async setCmdNmPiecesInput(cmdNmPieces: string): Promise<void> {
    await this.cmdNmPiecesInput.sendKeys(cmdNmPieces);
  }

  async getCmdNmPiecesInput(): Promise<string> {
    return await this.cmdNmPiecesInput.getAttribute('value');
  }

  async commandeFourniseurSelectLastOption(): Promise<void> {
    await this.commandeFourniseurSelect.all(by.tagName('option')).last().click();
  }

  async commandeFourniseurSelectOption(option: string): Promise<void> {
    await this.commandeFourniseurSelect.sendKeys(option);
  }

  getCommandeFourniseurSelect(): ElementFinder {
    return this.commandeFourniseurSelect;
  }

  async getCommandeFourniseurSelectedOption(): Promise<string> {
    return await this.commandeFourniseurSelect.element(by.css('option:checked')).getText();
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

export class LigneCmdFournisseurDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-ligneCmdFournisseur-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-ligneCmdFournisseur'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
