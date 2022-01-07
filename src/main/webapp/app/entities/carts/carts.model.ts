export interface ICarts {
  id?: number;
  cartIsEmpty?: boolean | null;
  cartUserEmail?: string | null;
  cartListProduct?: string | null;
}

export class Carts implements ICarts {
  constructor(
    public id?: number,
    public cartIsEmpty?: boolean | null,
    public cartUserEmail?: string | null,
    public cartListProduct?: string | null
  ) {
    this.cartIsEmpty = this.cartIsEmpty ?? false;
  }
}

export function getCartsIdentifier(carts: ICarts): number | undefined {
  return carts.id;
}
