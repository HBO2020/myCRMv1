import { element, by, ElementFinder } from 'protractor';

export class FactureVenteComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-facture-vente div table .btn-danger'));
  title = element.all(by.css('jhi-facture-vente div h2#page-heading span')).first();
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

export class FactureVenteUpdatePage {
  pageTitle = element(by.id('jhi-facture-vente-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  venteIdentFacInput = element(by.id('field_venteIdentFac'));
  venteDateEffetInput = element(by.id('field_venteDateEffet'));
  venteDateUpdateInput = element(by.id('field_venteDateUpdate'));
  venteStatusFactInput = element(by.id('field_venteStatusFact'));
  venteMontantHTInput = element(by.id('field_venteMontantHT'));
  venteMontantTVAInput = element(by.id('field_venteMontantTVA'));
  venteMontantTTCInput = element(by.id('field_venteMontantTTC'));

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

  async setVenteIdentFacInput(venteIdentFac: string): Promise<void> {
    await this.venteIdentFacInput.sendKeys(venteIdentFac);
  }

  async getVenteIdentFacInput(): Promise<string> {
    return await this.venteIdentFacInput.getAttribute('value');
  }

  async setVenteDateEffetInput(venteDateEffet: string): Promise<void> {
    await this.venteDateEffetInput.sendKeys(venteDateEffet);
  }

  async getVenteDateEffetInput(): Promise<string> {
    return await this.venteDateEffetInput.getAttribute('value');
  }

  async setVenteDateUpdateInput(venteDateUpdate: string): Promise<void> {
    await this.venteDateUpdateInput.sendKeys(venteDateUpdate);
  }

  async getVenteDateUpdateInput(): Promise<string> {
    return await this.venteDateUpdateInput.getAttribute('value');
  }

  async setVenteStatusFactInput(venteStatusFact: string): Promise<void> {
    await this.venteStatusFactInput.sendKeys(venteStatusFact);
  }

  async getVenteStatusFactInput(): Promise<string> {
    return await this.venteStatusFactInput.getAttribute('value');
  }

  async setVenteMontantHTInput(venteMontantHT: string): Promise<void> {
    await this.venteMontantHTInput.sendKeys(venteMontantHT);
  }

  async getVenteMontantHTInput(): Promise<string> {
    return await this.venteMontantHTInput.getAttribute('value');
  }

  async setVenteMontantTVAInput(venteMontantTVA: string): Promise<void> {
    await this.venteMontantTVAInput.sendKeys(venteMontantTVA);
  }

  async getVenteMontantTVAInput(): Promise<string> {
    return await this.venteMontantTVAInput.getAttribute('value');
  }

  async setVenteMontantTTCInput(venteMontantTTC: string): Promise<void> {
    await this.venteMontantTTCInput.sendKeys(venteMontantTTC);
  }

  async getVenteMontantTTCInput(): Promise<string> {
    return await this.venteMontantTTCInput.getAttribute('value');
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

export class FactureVenteDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-factureVente-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-factureVente'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
