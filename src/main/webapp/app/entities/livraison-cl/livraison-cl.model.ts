import dayjs from 'dayjs/esm';
import { ILigneLivClient } from 'app/entities/ligne-liv-client/ligne-liv-client.model';
import { ICommandeClient } from 'app/entities/commande-client/commande-client.model';
import { IFactureVente } from 'app/entities/facture-vente/facture-vente.model';

export interface ILivraisonCl {
  id?: number;
  bonLivIdentCl?: number | null;
  livDateCl?: dayjs.Dayjs | null;
  livDateUpdateCl?: dayjs.Dayjs | null;
  livDateEffetCl?: dayjs.Dayjs | null;
  bonLivTotalCl?: number | null;
  ligneLivClients?: ILigneLivClient[] | null;
  commandeClients?: ICommandeClient[] | null;
  factureVentes?: IFactureVente[] | null;
}

export class LivraisonCl implements ILivraisonCl {
  constructor(
    public id?: number,
    public bonLivIdentCl?: number | null,
    public livDateCl?: dayjs.Dayjs | null,
    public livDateUpdateCl?: dayjs.Dayjs | null,
    public livDateEffetCl?: dayjs.Dayjs | null,
    public bonLivTotalCl?: number | null,
    public ligneLivClients?: ILigneLivClient[] | null,
    public commandeClients?: ICommandeClient[] | null,
    public factureVentes?: IFactureVente[] | null
  ) {}
}

export function getLivraisonClIdentifier(livraisonCl: ILivraisonCl): number | undefined {
  return livraisonCl.id;
}
