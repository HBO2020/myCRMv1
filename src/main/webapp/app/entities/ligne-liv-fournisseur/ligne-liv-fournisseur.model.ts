import { IArticle } from 'app/entities/article/article.model';
import { ILivraisonFr } from 'app/entities/livraison-fr/livraison-fr.model';

export interface ILigneLivFournisseur {
  id?: number;
  livFrQuantite?: number | null;
  livFrNmPieces?: number | null;
  livFrTotalPrix?: number | null;
  articles?: IArticle[] | null;
  livraisonFr?: ILivraisonFr | null;
}

export class LigneLivFournisseur implements ILigneLivFournisseur {
  constructor(
    public id?: number,
    public livFrQuantite?: number | null,
    public livFrNmPieces?: number | null,
    public livFrTotalPrix?: number | null,
    public articles?: IArticle[] | null,
    public livraisonFr?: ILivraisonFr | null
  ) {}
}

export function getLigneLivFournisseurIdentifier(ligneLivFournisseur: ILigneLivFournisseur): number | undefined {
  return ligneLivFournisseur.id;
}
