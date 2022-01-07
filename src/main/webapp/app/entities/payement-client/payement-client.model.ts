import dayjs from 'dayjs/esm';
import { IFactureAchat } from 'app/entities/facture-achat/facture-achat.model';

export interface IPayementClient {
  id?: number;
  payementClIdent?: number | null;
  payementClDate?: dayjs.Dayjs | null;
  payementClMode?: string | null;
  payementClEcheance?: dayjs.Dayjs | null;
  payementClMontant?: number | null;
  factureAchats?: IFactureAchat[] | null;
}

export class PayementClient implements IPayementClient {
  constructor(
    public id?: number,
    public payementClIdent?: number | null,
    public payementClDate?: dayjs.Dayjs | null,
    public payementClMode?: string | null,
    public payementClEcheance?: dayjs.Dayjs | null,
    public payementClMontant?: number | null,
    public factureAchats?: IFactureAchat[] | null
  ) {}
}

export function getPayementClientIdentifier(payementClient: IPayementClient): number | undefined {
  return payementClient.id;
}
