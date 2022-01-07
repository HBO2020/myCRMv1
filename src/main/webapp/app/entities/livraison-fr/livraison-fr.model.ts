import dayjs from 'dayjs/esm';
import { ILigneLivFournisseur } from 'app/entities/ligne-liv-fournisseur/ligne-liv-fournisseur.model';
import { ICommandeFournisseur } from 'app/entities/commande-fournisseur/commande-fournisseur.model';
import { IFactureAchat } from 'app/entities/facture-achat/facture-achat.model';

export interface ILivraisonFr {
  id?: number;
  bonLivIdent?: number | null;
  livFrDate?: dayjs.Dayjs | null;
  livFrDateUpdate?: dayjs.Dayjs | null;
  livDateEffet?: dayjs.Dayjs | null;
  bonLivTotal?: number | null;
  ligneLivFournisseurs?: ILigneLivFournisseur[] | null;
  commandeFournisseurs?: ICommandeFournisseur[] | null;
  factureAchats?: IFactureAchat[] | null;
}

export class LivraisonFr implements ILivraisonFr {
  constructor(
    public id?: number,
    public bonLivIdent?: number | null,
    public livFrDate?: dayjs.Dayjs | null,
    public livFrDateUpdate?: dayjs.Dayjs | null,
    public livDateEffet?: dayjs.Dayjs | null,
    public bonLivTotal?: number | null,
    public ligneLivFournisseurs?: ILigneLivFournisseur[] | null,
    public commandeFournisseurs?: ICommandeFournisseur[] | null,
    public factureAchats?: IFactureAchat[] | null
  ) {}
}

export function getLivraisonFrIdentifier(livraisonFr: ILivraisonFr): number | undefined {
  return livraisonFr.id;
}
