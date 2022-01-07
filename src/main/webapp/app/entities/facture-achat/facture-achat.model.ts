import dayjs from 'dayjs/esm';
import { IFournisseur } from 'app/entities/fournisseur/fournisseur.model';
import { IPayementFournisseur } from 'app/entities/payement-fournisseur/payement-fournisseur.model';
import { ILivraisonFr } from 'app/entities/livraison-fr/livraison-fr.model';
import { IClient } from 'app/entities/client/client.model';
import { IPayementClient } from 'app/entities/payement-client/payement-client.model';

export interface IFactureAchat {
  id?: number;
  achatIdentFac?: number | null;
  achatDateEffet?: dayjs.Dayjs | null;
  achatDateUpdate?: dayjs.Dayjs | null;
  achatStatusFact?: string | null;
  achatMontantHT?: number | null;
  achatMontantTVA?: number | null;
  achatMontantTTC?: number | null;
  fournisseur?: IFournisseur | null;
  payementFr?: IPayementFournisseur | null;
  livraisonFr?: ILivraisonFr | null;
  client?: IClient | null;
  payementCl?: IPayementClient | null;
}

export class FactureAchat implements IFactureAchat {
  constructor(
    public id?: number,
    public achatIdentFac?: number | null,
    public achatDateEffet?: dayjs.Dayjs | null,
    public achatDateUpdate?: dayjs.Dayjs | null,
    public achatStatusFact?: string | null,
    public achatMontantHT?: number | null,
    public achatMontantTVA?: number | null,
    public achatMontantTTC?: number | null,
    public fournisseur?: IFournisseur | null,
    public payementFr?: IPayementFournisseur | null,
    public livraisonFr?: ILivraisonFr | null,
    public client?: IClient | null,
    public payementCl?: IPayementClient | null
  ) {}
}

export function getFactureAchatIdentifier(factureAchat: IFactureAchat): number | undefined {
  return factureAchat.id;
}
