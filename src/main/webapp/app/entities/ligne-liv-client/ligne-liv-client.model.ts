import { IArticle } from 'app/entities/article/article.model';
import { ILivraisonCl } from 'app/entities/livraison-cl/livraison-cl.model';

export interface ILigneLivClient {
  id?: number;
  livQuantiteCl?: number | null;
  livNmPiecesCl?: number | null;
  livTotalPrixCl?: number | null;
  articles?: IArticle[] | null;
  livraisonCl?: ILivraisonCl | null;
}

export class LigneLivClient implements ILigneLivClient {
  constructor(
    public id?: number,
    public livQuantiteCl?: number | null,
    public livNmPiecesCl?: number | null,
    public livTotalPrixCl?: number | null,
    public articles?: IArticle[] | null,
    public livraisonCl?: ILivraisonCl | null
  ) {}
}

export function getLigneLivClientIdentifier(ligneLivClient: ILigneLivClient): number | undefined {
  return ligneLivClient.id;
}
