import { element, by, ElementFinder } from 'protractor';

export class CommandeClientComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-commande-client div table .btn-danger'));
  title = element.all(by.css('jhi-commande-client div h2#page-heading span')).first();
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

export class CommandeClientUpdatePage {
  pageTitle = element(by.id('jhi-commande-client-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  cmdIdenClInput = element(by.id('field_cmdIdenCl'));
  cmdDateEffetClInput = element(by.id('field_cmdDateEffetCl'));
  cmdDateLivraisonClInput = element(by.id('field_cmdDateLivraisonCl'));

  clientSelect = element(by.id('field_client'));
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

  async setCmdIdenClInput(cmdIdenCl: string): Promise<void> {
    await this.cmdIdenClInput.sendKeys(cmdIdenCl);
  }

  async getCmdIdenClInput(): Promise<string> {
    return await this.cmdIdenClInput.getAttribute('value');
  }

  async setCmdDateEffetClInput(cmdDateEffetCl: string): Promise<void> {
    await this.cmdDateEffetClInput.sendKeys(cmdDateEffetCl);
  }

  async getCmdDateEffetClInput(): Promise<string> {
    return await this.cmdDateEffetClInput.getAttribute('value');
  }

  async setCmdDateLivraisonClInput(cmdDateLivraisonCl: string): Promise<void> {
    await this.cmdDateLivraisonClInput.sendKeys(cmdDateLivraisonCl);
  }

  async getCmdDateLivraisonClInput(): Promise<string> {
    return await this.cmdDateLivraisonClInput.getAttribute('value');
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

export class CommandeClientDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-commandeClient-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-commandeClient'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
