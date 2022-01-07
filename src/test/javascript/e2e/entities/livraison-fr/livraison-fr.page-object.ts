import { element, by, ElementFinder } from 'protractor';

export class LivraisonFrComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-livraison-fr div table .btn-danger'));
  title = element.all(by.css('jhi-livraison-fr div h2#page-heading span')).first();
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

export class LivraisonFrUpdatePage {
  pageTitle = element(by.id('jhi-livraison-fr-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  bonLivIdentInput = element(by.id('field_bonLivIdent'));
  livFrDateInput = element(by.id('field_livFrDate'));
  livFrDateUpdateInput = element(by.id('field_livFrDateUpdate'));
  livDateEffetInput = element(by.id('field_livDateEffet'));
  bonLivTotalInput = element(by.id('field_bonLivTotal'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setBonLivIdentInput(bonLivIdent: string): Promise<void> {
    await this.bonLivIdentInput.sendKeys(bonLivIdent);
  }

  async getBonLivIdentInput(): Promise<string> {
    return await this.bonLivIdentInput.getAttribute('value');
  }

  async setLivFrDateInput(livFrDate: string): Promise<void> {
    await this.livFrDateInput.sendKeys(livFrDate);
  }

  async getLivFrDateInput(): Promise<string> {
    return await this.livFrDateInput.getAttribute('value');
  }

  async setLivFrDateUpdateInput(livFrDateUpdate: string): Promise<void> {
    await this.livFrDateUpdateInput.sendKeys(livFrDateUpdate);
  }

  async getLivFrDateUpdateInput(): Promise<string> {
    return await this.livFrDateUpdateInput.getAttribute('value');
  }

  async setLivDateEffetInput(livDateEffet: string): Promise<void> {
    await this.livDateEffetInput.sendKeys(livDateEffet);
  }

  async getLivDateEffetInput(): Promise<string> {
    return await this.livDateEffetInput.getAttribute('value');
  }

  async setBonLivTotalInput(bonLivTotal: string): Promise<void> {
    await this.bonLivTotalInput.sendKeys(bonLivTotal);
  }

  async getBonLivTotalInput(): Promise<string> {
    return await this.bonLivTotalInput.getAttribute('value');
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

export class LivraisonFrDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-livraisonFr-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-livraisonFr'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
