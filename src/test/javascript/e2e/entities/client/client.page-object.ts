import { element, by, ElementFinder } from 'protractor';

export class ClientComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-client div table .btn-danger'));
  title = element.all(by.css('jhi-client div h2#page-heading span')).first();
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

export class ClientUpdatePage {
  pageTitle = element(by.id('jhi-client-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  clIdentInput = element(by.id('field_clIdent'));
  clRaisonSocialInput = element(by.id('field_clRaisonSocial'));
  clAdresseInput = element(by.id('field_clAdresse'));
  clCodePostalInput = element(by.id('field_clCodePostal'));
  clVilleInput = element(by.id('field_clVille'));
  clCountryInput = element(by.id('field_clCountry'));
  clEmailInput = element(by.id('field_clEmail'));
  clNumeroMobileInput = element(by.id('field_clNumeroMobile'));
  clNumeroFaxInput = element(by.id('field_clNumeroFax'));
  clNumeroFixeInput = element(by.id('field_clNumeroFixe'));
  clDateCreationInput = element(by.id('field_clDateCreation'));
  clDateUpdateInput = element(by.id('field_clDateUpdate'));
  clStatusInput = element(by.id('field_clStatus'));
  clNumeroSiretInput = element(by.id('field_clNumeroSiret'));

  civiliteclSelect = element(by.id('field_civilitecl'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setClIdentInput(clIdent: string): Promise<void> {
    await this.clIdentInput.sendKeys(clIdent);
  }

  async getClIdentInput(): Promise<string> {
    return await this.clIdentInput.getAttribute('value');
  }

  async setClRaisonSocialInput(clRaisonSocial: string): Promise<void> {
    await this.clRaisonSocialInput.sendKeys(clRaisonSocial);
  }

  async getClRaisonSocialInput(): Promise<string> {
    return await this.clRaisonSocialInput.getAttribute('value');
  }

  async setClAdresseInput(clAdresse: string): Promise<void> {
    await this.clAdresseInput.sendKeys(clAdresse);
  }

  async getClAdresseInput(): Promise<string> {
    return await this.clAdresseInput.getAttribute('value');
  }

  async setClCodePostalInput(clCodePostal: string): Promise<void> {
    await this.clCodePostalInput.sendKeys(clCodePostal);
  }

  async getClCodePostalInput(): Promise<string> {
    return await this.clCodePostalInput.getAttribute('value');
  }

  async setClVilleInput(clVille: string): Promise<void> {
    await this.clVilleInput.sendKeys(clVille);
  }

  async getClVilleInput(): Promise<string> {
    return await this.clVilleInput.getAttribute('value');
  }

  async setClCountryInput(clCountry: string): Promise<void> {
    await this.clCountryInput.sendKeys(clCountry);
  }

  async getClCountryInput(): Promise<string> {
    return await this.clCountryInput.getAttribute('value');
  }

  async setClEmailInput(clEmail: string): Promise<void> {
    await this.clEmailInput.sendKeys(clEmail);
  }

  async getClEmailInput(): Promise<string> {
    return await this.clEmailInput.getAttribute('value');
  }

  async setClNumeroMobileInput(clNumeroMobile: string): Promise<void> {
    await this.clNumeroMobileInput.sendKeys(clNumeroMobile);
  }

  async getClNumeroMobileInput(): Promise<string> {
    return await this.clNumeroMobileInput.getAttribute('value');
  }

  async setClNumeroFaxInput(clNumeroFax: string): Promise<void> {
    await this.clNumeroFaxInput.sendKeys(clNumeroFax);
  }

  async getClNumeroFaxInput(): Promise<string> {
    return await this.clNumeroFaxInput.getAttribute('value');
  }

  async setClNumeroFixeInput(clNumeroFixe: string): Promise<void> {
    await this.clNumeroFixeInput.sendKeys(clNumeroFixe);
  }

  async getClNumeroFixeInput(): Promise<string> {
    return await this.clNumeroFixeInput.getAttribute('value');
  }

  async setClDateCreationInput(clDateCreation: string): Promise<void> {
    await this.clDateCreationInput.sendKeys(clDateCreation);
  }

  async getClDateCreationInput(): Promise<string> {
    return await this.clDateCreationInput.getAttribute('value');
  }

  async setClDateUpdateInput(clDateUpdate: string): Promise<void> {
    await this.clDateUpdateInput.sendKeys(clDateUpdate);
  }

  async getClDateUpdateInput(): Promise<string> {
    return await this.clDateUpdateInput.getAttribute('value');
  }

  async setClStatusInput(clStatus: string): Promise<void> {
    await this.clStatusInput.sendKeys(clStatus);
  }

  async getClStatusInput(): Promise<string> {
    return await this.clStatusInput.getAttribute('value');
  }

  async setClNumeroSiretInput(clNumeroSiret: string): Promise<void> {
    await this.clNumeroSiretInput.sendKeys(clNumeroSiret);
  }

  async getClNumeroSiretInput(): Promise<string> {
    return await this.clNumeroSiretInput.getAttribute('value');
  }

  async civiliteclSelectLastOption(): Promise<void> {
    await this.civiliteclSelect.all(by.tagName('option')).last().click();
  }

  async civiliteclSelectOption(option: string): Promise<void> {
    await this.civiliteclSelect.sendKeys(option);
  }

  getCiviliteclSelect(): ElementFinder {
    return this.civiliteclSelect;
  }

  async getCiviliteclSelectedOption(): Promise<string> {
    return await this.civiliteclSelect.element(by.css('option:checked')).getText();
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

export class ClientDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-client-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-client'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
