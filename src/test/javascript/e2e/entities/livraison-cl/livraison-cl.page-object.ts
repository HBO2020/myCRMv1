import { element, by, ElementFinder } from 'protractor';

export class LivraisonClComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-livraison-cl div table .btn-danger'));
  title = element.all(by.css('jhi-livraison-cl div h2#page-heading span')).first();
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

export class LivraisonClUpdatePage {
  pageTitle = element(by.id('jhi-livraison-cl-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  bonLivIdentClInput = element(by.id('field_bonLivIdentCl'));
  livDateClInput = element(by.id('field_livDateCl'));
  livDateUpdateClInput = element(by.id('field_livDateUpdateCl'));
  livDateEffetClInput = element(by.id('field_livDateEffetCl'));
  bonLivTotalClInput = element(by.id('field_bonLivTotalCl'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setBonLivIdentClInput(bonLivIdentCl: string): Promise<void> {
    await this.bonLivIdentClInput.sendKeys(bonLivIdentCl);
  }

  async getBonLivIdentClInput(): Promise<string> {
    return await this.bonLivIdentClInput.getAttribute('value');
  }

  async setLivDateClInput(livDateCl: string): Promise<void> {
    await this.livDateClInput.sendKeys(livDateCl);
  }

  async getLivDateClInput(): Promise<string> {
    return await this.livDateClInput.getAttribute('value');
  }

  async setLivDateUpdateClInput(livDateUpdateCl: string): Promise<void> {
    await this.livDateUpdateClInput.sendKeys(livDateUpdateCl);
  }

  async getLivDateUpdateClInput(): Promise<string> {
    return await this.livDateUpdateClInput.getAttribute('value');
  }

  async setLivDateEffetClInput(livDateEffetCl: string): Promise<void> {
    await this.livDateEffetClInput.sendKeys(livDateEffetCl);
  }

  async getLivDateEffetClInput(): Promise<string> {
    return await this.livDateEffetClInput.getAttribute('value');
  }

  async setBonLivTotalClInput(bonLivTotalCl: string): Promise<void> {
    await this.bonLivTotalClInput.sendKeys(bonLivTotalCl);
  }

  async getBonLivTotalClInput(): Promise<string> {
    return await this.bonLivTotalClInput.getAttribute('value');
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

export class LivraisonClDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-livraisonCl-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-livraisonCl'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
