import { element, by, ElementFinder } from 'protractor';

export class LigneLivClientComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-ligne-liv-client div table .btn-danger'));
  title = element.all(by.css('jhi-ligne-liv-client div h2#page-heading span')).first();
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

export class LigneLivClientUpdatePage {
  pageTitle = element(by.id('jhi-ligne-liv-client-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  livQuantiteClInput = element(by.id('field_livQuantiteCl'));
  livNmPiecesClInput = element(by.id('field_livNmPiecesCl'));
  livTotalPrixClInput = element(by.id('field_livTotalPrixCl'));

  livraisonClSelect = element(by.id('field_livraisonCl'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setLivQuantiteClInput(livQuantiteCl: string): Promise<void> {
    await this.livQuantiteClInput.sendKeys(livQuantiteCl);
  }

  async getLivQuantiteClInput(): Promise<string> {
    return await this.livQuantiteClInput.getAttribute('value');
  }

  async setLivNmPiecesClInput(livNmPiecesCl: string): Promise<void> {
    await this.livNmPiecesClInput.sendKeys(livNmPiecesCl);
  }

  async getLivNmPiecesClInput(): Promise<string> {
    return await this.livNmPiecesClInput.getAttribute('value');
  }

  async setLivTotalPrixClInput(livTotalPrixCl: string): Promise<void> {
    await this.livTotalPrixClInput.sendKeys(livTotalPrixCl);
  }

  async getLivTotalPrixClInput(): Promise<string> {
    return await this.livTotalPrixClInput.getAttribute('value');
  }

  async livraisonClSelectLastOption(): Promise<void> {
    await this.livraisonClSelect.all(by.tagName('option')).last().click();
  }

  async livraisonClSelectOption(option: string): Promise<void> {
    await this.livraisonClSelect.sendKeys(option);
  }

  getLivraisonClSelect(): ElementFinder {
    return this.livraisonClSelect;
  }

  async getLivraisonClSelectedOption(): Promise<string> {
    return await this.livraisonClSelect.element(by.css('option:checked')).getText();
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

export class LigneLivClientDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-ligneLivClient-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-ligneLivClient'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
