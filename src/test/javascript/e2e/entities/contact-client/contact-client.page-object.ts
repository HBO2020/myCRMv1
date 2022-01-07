import { element, by, ElementFinder } from 'protractor';

export class ContactClientComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-contact-client div table .btn-danger'));
  title = element.all(by.css('jhi-contact-client div h2#page-heading span')).first();
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

export class ContactClientUpdatePage {
  pageTitle = element(by.id('jhi-contact-client-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  contactNameClInput = element(by.id('field_contactNameCl'));
  contactPrenomClInput = element(by.id('field_contactPrenomCl'));
  contactEmailClInput = element(by.id('field_contactEmailCl'));
  contactMobilePhoneClInput = element(by.id('field_contactMobilePhoneCl'));
  contactStatusClInput = element(by.id('field_contactStatusCl'));

  clientSelect = element(by.id('field_client'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setContactNameClInput(contactNameCl: string): Promise<void> {
    await this.contactNameClInput.sendKeys(contactNameCl);
  }

  async getContactNameClInput(): Promise<string> {
    return await this.contactNameClInput.getAttribute('value');
  }

  async setContactPrenomClInput(contactPrenomCl: string): Promise<void> {
    await this.contactPrenomClInput.sendKeys(contactPrenomCl);
  }

  async getContactPrenomClInput(): Promise<string> {
    return await this.contactPrenomClInput.getAttribute('value');
  }

  async setContactEmailClInput(contactEmailCl: string): Promise<void> {
    await this.contactEmailClInput.sendKeys(contactEmailCl);
  }

  async getContactEmailClInput(): Promise<string> {
    return await this.contactEmailClInput.getAttribute('value');
  }

  async setContactMobilePhoneClInput(contactMobilePhoneCl: string): Promise<void> {
    await this.contactMobilePhoneClInput.sendKeys(contactMobilePhoneCl);
  }

  async getContactMobilePhoneClInput(): Promise<string> {
    return await this.contactMobilePhoneClInput.getAttribute('value');
  }

  async setContactStatusClInput(contactStatusCl: string): Promise<void> {
    await this.contactStatusClInput.sendKeys(contactStatusCl);
  }

  async getContactStatusClInput(): Promise<string> {
    return await this.contactStatusClInput.getAttribute('value');
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

export class ContactClientDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-contactClient-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-contactClient'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
