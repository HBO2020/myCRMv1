import { element, by, ElementFinder } from 'protractor';

export class FactureAchatComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-facture-achat div table .btn-danger'));
  title = element.all(by.css('jhi-facture-achat div h2#page-heading span')).first();
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

export class FactureAchatUpdatePage {
  pageTitle = element(by.id('jhi-facture-achat-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  achatIdentFacInput = element(by.id('field_achatIdentFac'));
  achatDateEffetInput = element(by.id('field_achatDateEffet'));
  achatDateUpdateInput = element(by.id('field_achatDateUpdate'));
  achatStatusFactInput = element(by.id('field_achatStatusFact'));
  achatMontantHTInput = element(by.id('field_achatMontantHT'));
  achatMontantTVAInput = element(by.id('field_achatMontantTVA'));
  achatMontantTTCInput = element(by.id('field_achatMontantTTC'));

  fournisseurSelect = element(by.id('field_fournisseur'));
  payementFrSelect = element(by.id('field_payementFr'));
  livraisonFrSelect = element(by.id('field_livraisonFr'));
  clientSelect = element(by.id('field_client'));
  payementClSelect = element(by.id('field_payementCl'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setAchatIdentFacInput(achatIdentFac: string): Promise<void> {
    await this.achatIdentFacInput.sendKeys(achatIdentFac);
  }

  async getAchatIdentFacInput(): Promise<string> {
    return await this.achatIdentFacInput.getAttribute('value');
  }

  async setAchatDateEffetInput(achatDateEffet: string): Promise<void> {
    await this.achatDateEffetInput.sendKeys(achatDateEffet);
  }

  async getAchatDateEffetInput(): Promise<string> {
    return await this.achatDateEffetInput.getAttribute('value');
  }

  async setAchatDateUpdateInput(achatDateUpdate: string): Promise<void> {
    await this.achatDateUpdateInput.sendKeys(achatDateUpdate);
  }

  async getAchatDateUpdateInput(): Promise<string> {
    return await this.achatDateUpdateInput.getAttribute('value');
  }

  async setAchatStatusFactInput(achatStatusFact: string): Promise<void> {
    await this.achatStatusFactInput.sendKeys(achatStatusFact);
  }

  async getAchatStatusFactInput(): Promise<string> {
    return await this.achatStatusFactInput.getAttribute('value');
  }

  async setAchatMontantHTInput(achatMontantHT: string): Promise<void> {
    await this.achatMontantHTInput.sendKeys(achatMontantHT);
  }

  async getAchatMontantHTInput(): Promise<string> {
    return await this.achatMontantHTInput.getAttribute('value');
  }

  async setAchatMontantTVAInput(achatMontantTVA: string): Promise<void> {
    await this.achatMontantTVAInput.sendKeys(achatMontantTVA);
  }

  async getAchatMontantTVAInput(): Promise<string> {
    return await this.achatMontantTVAInput.getAttribute('value');
  }

  async setAchatMontantTTCInput(achatMontantTTC: string): Promise<void> {
    await this.achatMontantTTCInput.sendKeys(achatMontantTTC);
  }

  async getAchatMontantTTCInput(): Promise<string> {
    return await this.achatMontantTTCInput.getAttribute('value');
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

  async payementFrSelectLastOption(): Promise<void> {
    await this.payementFrSelect.all(by.tagName('option')).last().click();
  }

  async payementFrSelectOption(option: string): Promise<void> {
    await this.payementFrSelect.sendKeys(option);
  }

  getPayementFrSelect(): ElementFinder {
    return this.payementFrSelect;
  }

  async getPayementFrSelectedOption(): Promise<string> {
    return await this.payementFrSelect.element(by.css('option:checked')).getText();
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

  async clientSelectLastOption(): Promise<void> {
    await this.clientSelect.all(by.tagName('option')).last().click();
  }

  async clientSelectOption(option: string): Promise<void> {
    await this.clientSelect.sendKeys(option);
  }

  getClientSelect(): ElementFinder {
    return this.clientSelect;
  }

  async getClientSelectedOption(): Promise<string> {
    return await this.clientSelect.element(by.css('option:checked')).getText();
  }

  async payementClSelectLastOption(): Promise<void> {
    await this.payementClSelect.all(by.tagName('option')).last().click();
  }

  async payementClSelectOption(option: string): Promise<void> {
    await this.payementClSelect.sendKeys(option);
  }

  getPayementClSelect(): ElementFinder {
    return this.payementClSelect;
  }

  async getPayementClSelectedOption(): Promise<string> {
    return await this.payementClSelect.element(by.css('option:checked')).getText();
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

export class FactureAchatDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-factureAchat-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-factureAchat'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
