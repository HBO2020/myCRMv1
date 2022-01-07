import { IClient } from 'app/entities/client/client.model';

export interface IContactClient {
  id?: number;
  contactNameCl?: string | null;
  contactPrenomCl?: string | null;
  contactEmailCl?: string | null;
  contactMobilePhoneCl?: string | null;
  contactStatusCl?: string | null;
  client?: IClient | null;
}

export class ContactClient implements IContactClient {
  constructor(
    public id?: number,
    public contactNameCl?: string | null,
    public contactPrenomCl?: string | null,
    public contactEmailCl?: string | null,
    public contactMobilePhoneCl?: string | null,
    public contactStatusCl?: string | null,
    public client?: IClient | null
  ) {}
}

export function getContactClientIdentifier(contactClient: IContactClient): number | undefined {
  return contactClient.id;
}
