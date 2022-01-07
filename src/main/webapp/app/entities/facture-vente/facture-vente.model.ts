import dayjs from 'dayjs/esm';
import { ILivraisonCl } from 'app/entities/livraison-cl/livraison-cl.model';

export interface IFactureVente {
  id?: number;
  venteIdentFac?: number | null;
  venteDateEffet?: dayjs.Dayjs | null;
  venteDateUpdate?: dayjs.Dayjs | null;
  venteStatusFact?: string | null;
  venteMontantHT?: number | null;
  venteMontantTVA?: number | null;
  venteMontantTTC?: number | null;
  livraisonCl?: ILivraisonCl | null;
}

export class FactureVente implements IFactureVente {
  constructor(
    public id?: number,
    public venteIdentFac?: number | null,
    public venteDateEffet?: dayjs.Dayjs | null,
    public venteDateUpdate?: dayjs.Dayjs | null,
    public venteStatusFact?: string | null,
    public venteMontantHT?: number | null,
    public venteMontantTVA?: number | null,
    public venteMontantTTC?: number | null,
    public livraisonCl?: ILivraisonCl | null
  ) {}
}

export function getFactureVenteIdentifier(factureVente: IFactureVente): number | undefined {
  return factureVente.id;
}
