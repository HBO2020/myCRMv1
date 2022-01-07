import dayjs from 'dayjs/esm';
import { IFactureAchat } from 'app/entities/facture-achat/facture-achat.model';

export interface IPayementFournisseur {
  id?: number;
  payementFrIdent?: number | null;
  payementFrDate?: dayjs.Dayjs | null;
  payementFrMode?: string | null;
  payementFrEcheance?: dayjs.Dayjs | null;
  payementFrMontant?: number | null;
  factureAchats?: IFactureAchat[] | null;
}

export class PayementFournisseur implements IPayementFournisseur {
  constructor(
    public id?: number,
    public payementFrIdent?: number | null,
    public payementFrDate?: dayjs.Dayjs | null,
    public payementFrMode?: string | null,
    public payementFrEcheance?: dayjs.Dayjs | null,
    public payementFrMontant?: number | null,
    public factureAchats?: IFactureAchat[] | null
  ) {}
}

export function getPayementFournisseurIdentifier(payementFournisseur: IPayementFournisseur): number | undefined {
  return payementFournisseur.id;
}
