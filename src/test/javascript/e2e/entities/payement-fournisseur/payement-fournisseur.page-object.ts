import { element, by, ElementFinder } from 'protractor';

export class PayementFournisseurComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-payement-fournisseur div table .btn-danger'));
  title = element.all(by.css('jhi-payement-fournisseur div h2#page-heading span')).first();
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

export class PayementFournisseurUpdatePage {
  pageTitle = element(by.id('jhi-payement-fournisseur-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  payementFrIdentInput = element(by.id('field_payementFrIdent'));
  payementFrDateInput = element(by.id('field_payementFrDate'));
  payementFrModeInput = element(by.id('field_payementFrMode'));
  payementFrEcheanceInput = element(by.id('field_payementFrEcheance'));
  payementFrMontantInput = element(by.id('field_payementFrMontant'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setPayementFrIdentInput(payementFrIdent: string): Promise<void> {
    await this.payementFrIdentInput.sendKeys(payementFrIdent);
  }

  async getPayementFrIdentInput(): Promise<string> {
    return await this.payementFrIdentInput.getAttribute('value');
  }

  async setPayementFrDateInput(payementFrDate: string): Promise<void> {
    await this.payementFrDateInput.sendKeys(payementFrDate);
  }

  async getPayementFrDateInput(): Promise<string> {
    return await this.payementFrDateInput.getAttribute('value');
  }

  async setPayementFrModeInput(payementFrMode: string): Promise<void> {
    await this.payementFrModeInput.sendKeys(payementFrMode);
  }

  async getPayementFrModeInput(): Promise<string> {
    return await this.payementFrModeInput.getAttribute('value');
  }

  async setPayementFrEcheanceInput(payementFrEcheance: string): Promise<void> {
    await this.payementFrEcheanceInput.sendKeys(payementFrEcheance);
  }

  async getPayementFrEcheanceInput(): Promise<string> {
    return await this.payementFrEcheanceInput.getAttribute('value');
  }

  async setPayementFrMontantInput(payementFrMontant: string): Promise<void> {
    await this.payementFrMontantInput.sendKeys(payementFrMontant);
  }

  async getPayementFrMontantInput(): Promise<string> {
    return await this.payementFrMontantInput.getAttribute('value');
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

export class PayementFournisseurDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-payementFournisseur-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-payementFournisseur'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
