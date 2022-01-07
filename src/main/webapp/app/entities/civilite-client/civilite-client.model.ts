import { IClient } from 'app/entities/client/client.model';

export interface ICiviliteClient {
  id?: number;
  civiliteLibelleCl?: string | null;
  civiliteCodeCl?: number | null;
  clients?: IClient[] | null;
}

export class CiviliteClient implements ICiviliteClient {
  constructor(
    public id?: number,
    public civiliteLibelleCl?: string | null,
    public civiliteCodeCl?: number | null,
    public clients?: IClient[] | null
  ) {}
}

export function getCiviliteClientIdentifier(civiliteClient: ICiviliteClient): number | undefined {
  return civiliteClient.id;
}
