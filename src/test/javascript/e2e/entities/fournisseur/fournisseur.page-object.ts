import { element, by, ElementFinder } from 'protractor';

export class FournisseurComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-fournisseur div table .btn-danger'));
  title = element.all(by.css('jhi-fournisseur div h2#page-heading span')).first();
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

export class FournisseurUpdatePage {
  pageTitle = element(by.id('jhi-fournisseur-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  frIdentInput = element(by.id('field_frIdent'));
  frRaisonSocialInput = element(by.id('field_frRaisonSocial'));
  frAdresseInput = element(by.id('field_frAdresse'));
  frCodePostalInput = element(by.id('field_frCodePostal'));
  frVilleInput = element(by.id('field_frVille'));
  frCountryInput = element(by.id('field_frCountry'));
  frEmailInput = element(by.id('field_frEmail'));
  frNumeroMobileInput = element(by.id('field_frNumeroMobile'));
  frNumeroFaxInput = element(by.id('field_frNumeroFax'));
  frNumeroFixeInput = element(by.id('field_frNumeroFixe'));
  frDateCreationInput = element(by.id('field_frDateCreation'));
  frDateUpdateInput = element(by.id('field_frDateUpdate'));
  frStatusInput = element(by.id('field_frStatus'));
  frNumeroSiretInput = element(by.id('field_frNumeroSiret'));

  civilitefrSelect = element(by.id('field_civilitefr'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setFrIdentInput(frIdent: string): Promise<void> {
    await this.frIdentInput.sendKeys(frIdent);
  }

  async getFrIdentInput(): Promise<string> {
    return await this.frIdentInput.getAttribute('value');
  }

  async setFrRaisonSocialInput(frRaisonSocial: string): Promise<void> {
    await this.frRaisonSocialInput.sendKeys(frRaisonSocial);
  }

  async getFrRaisonSocialInput(): Promise<string> {
    return await this.frRaisonSocialInput.getAttribute('value');
  }

  async setFrAdresseInput(frAdresse: string): Promise<void> {
    await this.frAdresseInput.sendKeys(frAdresse);
  }

  async getFrAdresseInput(): Promise<string> {
    return await this.frAdresseInput.getAttribute('value');
  }

  async setFrCodePostalInput(frCodePostal: string): Promise<void> {
    await this.frCodePostalInput.sendKeys(frCodePostal);
  }

  async getFrCodePostalInput(): Promise<string> {
    return await this.frCodePostalInput.getAttribute('value');
  }

  async setFrVilleInput(frVille: string): Promise<void> {
    await this.frVilleInput.sendKeys(frVille);
  }

  async getFrVilleInput(): Promise<string> {
    return await this.frVilleInput.getAttribute('value');
  }

  async setFrCountryInput(frCountry: string): Promise<void> {
    await this.frCountryInput.sendKeys(frCountry);
  }

  async getFrCountryInput(): Promise<string> {
    return await this.frCountryInput.getAttribute('value');
  }

  async setFrEmailInput(frEmail: string): Promise<void> {
    await this.frEmailInput.sendKeys(frEmail);
  }

  async getFrEmailInput(): Promise<string> {
    return await this.frEmailInput.getAttribute('value');
  }

  async setFrNumeroMobileInput(frNumeroMobile: string): Promise<void> {
    await this.frNumeroMobileInput.sendKeys(frNumeroMobile);
  }

  async getFrNumeroMobileInput(): Promise<string> {
    return await this.frNumeroMobileInput.getAttribute('value');
  }

  async setFrNumeroFaxInput(frNumeroFax: string): Promise<void> {
    await this.frNumeroFaxInput.sendKeys(frNumeroFax);
  }

  async getFrNumeroFaxInput(): Promise<string> {
    return await this.frNumeroFaxInput.getAttribute('value');
  }

  async setFrNumeroFixeInput(frNumeroFixe: string): Promise<void> {
    await this.frNumeroFixeInput.sendKeys(frNumeroFixe);
  }

  async getFrNumeroFixeInput(): Promise<string> {
    return await this.frNumeroFixeInput.getAttribute('value');
  }

  async setFrDateCreationInput(frDateCreation: string): Promise<void> {
    await this.frDateCreationInput.sendKeys(frDateCreation);
  }

  async getFrDateCreationInput(): Promise<string> {
    return await this.frDateCreationInput.getAttribute('value');
  }

  async setFrDateUpdateInput(frDateUpdate: string): Promise<void> {
    await this.frDateUpdateInput.sendKeys(frDateUpdate);
  }

  async getFrDateUpdateInput(): Promise<string> {
    return await this.frDateUpdateInput.getAttribute('value');
  }

  async setFrStatusInput(frStatus: string): Promise<void> {
    await this.frStatusInput.sendKeys(frStatus);
  }

  async getFrStatusInput(): Promise<string> {
    return await this.frStatusInput.getAttribute('value');
  }

  async setFrNumeroSiretInput(frNumeroSiret: string): Promise<void> {
    await this.frNumeroSiretInput.sendKeys(frNumeroSiret);
  }

  async getFrNumeroSiretInput(): Promise<string> {
    return await this.frNumeroSiretInput.getAttribute('value');
  }

  async civilitefrSelectLastOption(): Promise<void> {
    await this.civilitefrSelect.all(by.tagName('option')).last().click();
  }

  async civilitefrSelectOption(option: string): Promise<void> {
    await this.civilitefrSelect.sendKeys(option);
  }

  getCivilitefrSelect(): ElementFinder {
    return this.civilitefrSelect;
  }

  async getCivilitefrSelectedOption(): Promise<string> {
    return await this.civilitefrSelect.element(by.css('option:checked')).getText();
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

export class FournisseurDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-fournisseur-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-fournisseur'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
