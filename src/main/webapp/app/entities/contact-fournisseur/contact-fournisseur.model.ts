import { IFournisseur } from 'app/entities/fournisseur/fournisseur.model';

export interface IContactFournisseur {
  id?: number;
  contactFrName?: string | null;
  contactfrPrenom?: string | null;
  contactFrEmail?: string | null;
  contactFrMobilePhone?: string | null;
  contactFrStatus?: string | null;
  fournisseur?: IFournisseur | null;
}

export class ContactFournisseur implements IContactFournisseur {
  constructor(
    public id?: number,
    public contactFrName?: string | null,
    public contactfrPrenom?: string | null,
    public contactFrEmail?: string | null,
    public contactFrMobilePhone?: string | null,
    public contactFrStatus?: string | null,
    public fournisseur?: IFournisseur | null
  ) {}
}

export function getContactFournisseurIdentifier(contactFournisseur: IContactFournisseur): number | undefined {
  return contactFournisseur.id;
}
