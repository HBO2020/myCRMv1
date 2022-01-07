import { element, by, ElementFinder } from 'protractor';

export class PayementClientComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-payement-client div table .btn-danger'));
  title = element.all(by.css('jhi-payement-client div h2#page-heading span')).first();
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

export class PayementClientUpdatePage {
  pageTitle = element(by.id('jhi-payement-client-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  payementClIdentInput = element(by.id('field_payementClIdent'));
  payementClDateInput = element(by.id('field_payementClDate'));
  payementClModeInput = element(by.id('field_payementClMode'));
  payementClEcheanceInput = element(by.id('field_payementClEcheance'));
  payementClMontantInput = element(by.id('field_payementClMontant'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setPayementClIdentInput(payementClIdent: string): Promise<void> {
    await this.payementClIdentInput.sendKeys(payementClIdent);
  }

  async getPayementClIdentInput(): Promise<string> {
    return await this.payementClIdentInput.getAttribute('value');
  }

  async setPayementClDateInput(payementClDate: string): Promise<void> {
    await this.payementClDateInput.sendKeys(payementClDate);
  }

  async getPayementClDateInput(): Promise<string> {
    return await this.payementClDateInput.getAttribute('value');
  }

  async setPayementClModeInput(payementClMode: string): Promise<void> {
    await this.payementClModeInput.sendKeys(payementClMode);
  }

  async getPayementClModeInput(): Promise<string> {
    return await this.payementClModeInput.getAttribute('value');
  }

  async setPayementClEcheanceInput(payementClEcheance: string): Promise<void> {
    await this.payementClEcheanceInput.sendKeys(payementClEcheance);
  }

  async getPayementClEcheanceInput(): Promise<string> {
    return await this.payementClEcheanceInput.getAttribute('value');
  }

  async setPayementClMontantInput(payementClMontant: string): Promise<void> {
    await this.payementClMontantInput.sendKeys(payementClMontant);
  }

  async getPayementClMontantInput(): Promise<string> {
    return await this.payementClMontantInput.getAttribute('value');
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

export class PayementClientDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-payementClient-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-payementClient'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
