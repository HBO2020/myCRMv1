import { element, by, ElementFinder } from 'protractor';

export class ContactFournisseurComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-contact-fournisseur div table .btn-danger'));
  title = element.all(by.css('jhi-contact-fournisseur div h2#page-heading span')).first();
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

export class ContactFournisseurUpdatePage {
  pageTitle = element(by.id('jhi-contact-fournisseur-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  contactFrNameInput = element(by.id('field_contactFrName'));
  contactfrPrenomInput = element(by.id('field_contactfrPrenom'));
  contactFrEmailInput = element(by.id('field_contactFrEmail'));
  contactFrMobilePhoneInput = element(by.id('field_contactFrMobilePhone'));
  contactFrStatusInput = element(by.id('field_contactFrStatus'));

  fournisseurSelect = element(by.id('field_fournisseur'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setContactFrNameInput(contactFrName: string): Promise<void> {
    await this.contactFrNameInput.sendKeys(contactFrName);
  }

  async getContactFrNameInput(): Promise<string> {
    return await this.contactFrNameInput.getAttribute('value');
  }

  async setContactfrPrenomInput(contactfrPrenom: string): Promise<void> {
    await this.contactfrPrenomInput.sendKeys(contactfrPrenom);
  }

  async getContactfrPrenomInput(): Promise<string> {
    return await this.contactfrPrenomInput.getAttribute('value');
  }

  async setContactFrEmailInput(contactFrEmail: string): Promise<void> {
    await this.contactFrEmailInput.sendKeys(contactFrEmail);
  }

  async getContactFrEmailInput(): Promise<string> {
    return await this.contactFrEmailInput.getAttribute('value');
  }

  async setContactFrMobilePhoneInput(contactFrMobilePhone: string): Promise<void> {
    await this.contactFrMobilePhoneInput.sendKeys(contactFrMobilePhone);
  }

  async getContactFrMobilePhoneInput(): Promise<string> {
    return await this.contactFrMobilePhoneInput.getAttribute('value');
  }

  async setContactFrStatusInput(contactFrStatus: string): Promise<void> {
    await this.contactFrStatusInput.sendKeys(contactFrStatus);
  }

  async getContactFrStatusInput(): Promise<string> {
    return await this.contactFrStatusInput.getAttribute('value');
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

export class ContactFournisseurDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-contactFournisseur-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-contactFournisseur'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
