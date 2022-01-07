import { element, by, ElementFinder } from 'protractor';

export class CartsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-carts div table .btn-danger'));
  title = element.all(by.css('jhi-carts div h2#page-heading span')).first();
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

export class CartsUpdatePage {
  pageTitle = element(by.id('jhi-carts-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  cartIsEmptyInput = element(by.id('field_cartIsEmpty'));
  cartUserEmailInput = element(by.id('field_cartUserEmail'));
  cartListProductInput = element(by.id('field_cartListProduct'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  getCartIsEmptyInput(): ElementFinder {
    return this.cartIsEmptyInput;
  }

  async setCartUserEmailInput(cartUserEmail: string): Promise<void> {
    await this.cartUserEmailInput.sendKeys(cartUserEmail);
  }

  async getCartUserEmailInput(): Promise<string> {
    return await this.cartUserEmailInput.getAttribute('value');
  }

  async setCartListProductInput(cartListProduct: string): Promise<void> {
    await this.cartListProductInput.sendKeys(cartListProduct);
  }

  async getCartListProductInput(): Promise<string> {
    return await this.cartListProductInput.getAttribute('value');
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

export class CartsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-carts-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-carts'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
