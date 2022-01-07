import dayjs from 'dayjs/esm';
import { ICommandeClient } from 'app/entities/commande-client/commande-client.model';
import { IFactureAchat } from 'app/entities/facture-achat/facture-achat.model';
import { IContactClient } from 'app/entities/contact-client/contact-client.model';
import { ICiviliteClient } from 'app/entities/civilite-client/civilite-client.model';

export interface IClient {
  id?: number;
  clIdent?: number | null;
  clRaisonSocial?: string | null;
  clAdresse?: string | null;
  clCodePostal?: string | null;
  clVille?: string | null;
  clCountry?: string | null;
  clEmail?: string | null;
  clNumeroMobile?: string | null;
  clNumeroFax?: string | null;
  clNumeroFixe?: string | null;
  clDateCreation?: dayjs.Dayjs | null;
  clDateUpdate?: dayjs.Dayjs | null;
  clStatus?: string | null;
  clNumeroSiret?: string | null;
  commandeClients?: ICommandeClient[] | null;
  factureAchats?: IFactureAchat[] | null;
  contactClients?: IContactClient[] | null;
  civilitecl?: ICiviliteClient | null;
}

export class Client implements IClient {
  constructor(
    public id?: number,
    public clIdent?: number | null,
    public clRaisonSocial?: string | null,
    public clAdresse?: string | null,
    public clCodePostal?: string | null,
    public clVille?: string | null,
    public clCountry?: string | null,
    public clEmail?: string | null,
    public clNumeroMobile?: string | null,
    public clNumeroFax?: string | null,
    public clNumeroFixe?: string | null,
    public clDateCreation?: dayjs.Dayjs | null,
    public clDateUpdate?: dayjs.Dayjs | null,
    public clStatus?: string | null,
    public clNumeroSiret?: string | null,
    public commandeClients?: ICommandeClient[] | null,
    public factureAchats?: IFactureAchat[] | null,
    public contactClients?: IContactClient[] | null,
    public civilitecl?: ICiviliteClient | null
  ) {}
}

export function getClientIdentifier(client: IClient): number | undefined {
  return client.id;
}
