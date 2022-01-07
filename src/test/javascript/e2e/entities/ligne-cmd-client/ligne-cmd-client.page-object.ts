import { element, by, ElementFinder } from 'protractor';

export class LigneCmdClientComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-ligne-cmd-client div table .btn-danger'));
  title = element.all(by.css('jhi-ligne-cmd-client div h2#page-heading span')).first();
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

export class LigneCmdClientUpdatePage {
  pageTitle = element(by.id('jhi-ligne-cmd-client-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  cmdQnClInput = element(by.id('field_cmdQnCl'));
  cmdNmPiecesClInput = element(by.id('field_cmdNmPiecesCl'));

  commandeClientSelect = element(by.id('field_commandeClient'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setCmdQnClInput(cmdQnCl: string): Promise<void> {
    await this.cmdQnClInput.sendKeys(cmdQnCl);
  }

  async getCmdQnClInput(): Promise<string> {
    return await this.cmdQnClInput.getAttribute('value');
  }

  async setCmdNmPiecesClInput(cmdNmPiecesCl: string): Promise<void> {
    await this.cmdNmPiecesClInput.sendKeys(cmdNmPiecesCl);
  }

  async getCmdNmPiecesClInput(): Promise<string> {
    return await this.cmdNmPiecesClInput.getAttribute('value');
  }

  async commandeClientSelectLastOption(): Promise<void> {
    await this.commandeClientSelect.all(by.tagName('option')).last().click();
  }

  async commandeClientSelectOption(option: string): Promise<void> {
    await this.commandeClientSelect.sendKeys(option);
  }

  getCommandeClientSelect(): ElementFinder {
    return this.commandeClientSelect;
  }

  async getCommandeClientSelectedOption(): Promise<string> {
    return await this.commandeClientSelect.element(by.css('option:checked')).getText();
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

export class LigneCmdClientDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-ligneCmdClient-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-ligneCmdClient'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
