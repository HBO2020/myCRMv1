import dayjs from 'dayjs/esm';
import { ICommandeFournisseur } from 'app/entities/commande-fournisseur/commande-fournisseur.model';
import { IFactureAchat } from 'app/entities/facture-achat/facture-achat.model';
import { IContactFournisseur } from 'app/entities/contact-fournisseur/contact-fournisseur.model';
import { ICiviliteFournisseur } from 'app/entities/civilite-fournisseur/civilite-fournisseur.model';

export interface IFournisseur {
  id?: number;
  frIdent?: number | null;
  frRaisonSocial?: string | null;
  frAdresse?: string | null;
  frCodePostal?: string | null;
  frVille?: string | null;
  frCountry?: string | null;
  frEmail?: string | null;
  frNumeroMobile?: string | null;
  frNumeroFax?: string | null;
  frNumeroFixe?: string | null;
  frDateCreation?: dayjs.Dayjs | null;
  frDateUpdate?: dayjs.Dayjs | null;
  frStatus?: string | null;
  frNumeroSiret?: string | null;
  commandeFournisseurs?: ICommandeFournisseur[] | null;
  factureAchats?: IFactureAchat[] | null;
  contactFournisseurs?: IContactFournisseur[] | null;
  civilitefr?: ICiviliteFournisseur | null;
}

export class Fournisseur implements IFournisseur {
  constructor(
    public id?: number,
    public frIdent?: number | null,
    public frRaisonSocial?: string | null,
    public frAdresse?: string | null,
    public frCodePostal?: string | null,
    public frVille?: string | null,
    public frCountry?: string | null,
    public frEmail?: string | null,
    public frNumeroMobile?: string | null,
    public frNumeroFax?: string | null,
    public frNumeroFixe?: string | null,
    public frDateCreation?: dayjs.Dayjs | null,
    public frDateUpdate?: dayjs.Dayjs | null,
    public frStatus?: string | null,
    public frNumeroSiret?: string | null,
    public commandeFournisseurs?: ICommandeFournisseur[] | null,
    public factureAchats?: IFactureAchat[] | null,
    public contactFournisseurs?: IContactFournisseur[] | null,
    public civilitefr?: ICiviliteFournisseur | null
  ) {}
}

export function getFournisseurIdentifier(fournisseur: IFournisseur): number | undefined {
  return fournisseur.id;
}
