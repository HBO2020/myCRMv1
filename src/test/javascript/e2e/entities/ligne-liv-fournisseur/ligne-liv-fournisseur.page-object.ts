import { element, by, ElementFinder } from 'protractor';

export class LigneLivFournisseurComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-ligne-liv-fournisseur div table .btn-danger'));
  title = element.all(by.css('jhi-ligne-liv-fournisseur div h2#page-heading span')).first();
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

export class LigneLivFournisseurUpdatePage {
  pageTitle = element(by.id('jhi-ligne-liv-fournisseur-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  livFrQuantiteInput = element(by.id('field_livFrQuantite'));
  livFrNmPiecesInput = element(by.id('field_livFrNmPieces'));
  livFrTotalPrixInput = element(by.id('field_livFrTotalPrix'));

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

  async setLivFrQuantiteInput(livFrQuantite: string): Promise<void> {
    await this.livFrQuantiteInput.sendKeys(livFrQuantite);
  }

  async getLivFrQuantiteInput(): Promise<string> {
    return await this.livFrQuantiteInput.getAttribute('value');
  }

  async setLivFrNmPiecesInput(livFrNmPieces: string): Promise<void> {
    await this.livFrNmPiecesInput.sendKeys(livFrNmPieces);
  }

  async getLivFrNmPiecesInput(): Promise<string> {
    return await this.livFrNmPiecesInput.getAttribute('value');
  }

  async setLivFrTotalPrixInput(livFrTotalPrix: string): Promise<void> {
    await this.livFrTotalPrixInput.sendKeys(livFrTotalPrix);
  }

  async getLivFrTotalPrixInput(): Promise<string> {
    return await this.livFrTotalPrixInput.getAttribute('value');
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

export class LigneLivFournisseurDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-ligneLivFournisseur-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-ligneLivFournisseur'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
