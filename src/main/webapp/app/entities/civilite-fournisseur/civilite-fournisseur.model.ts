import { IFournisseur } from 'app/entities/fournisseur/fournisseur.model';

export interface ICiviliteFournisseur {
  id?: number;
  civiliteFrLibelle?: string | null;
  civiliteFrCode?: number | null;
  fournisseurs?: IFournisseur[] | null;
}

export class CiviliteFournisseur implements ICiviliteFournisseur {
  constructor(
    public id?: number,
    public civiliteFrLibelle?: string | null,
    public civiliteFrCode?: number | null,
    public fournisseurs?: IFournisseur[] | null
  ) {}
}

export function getCiviliteFournisseurIdentifier(civiliteFournisseur: ICiviliteFournisseur): number | undefined {
  return civiliteFournisseur.id;
}
