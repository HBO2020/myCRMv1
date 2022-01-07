import { IArticle } from 'app/entities/article/article.model';
import { ICommandeFournisseur } from 'app/entities/commande-fournisseur/commande-fournisseur.model';

export interface ILigneCmdFournisseur {
  id?: number;
  cmdQnFr?: number | null;
  cmdNmPieces?: number | null;
  articles?: IArticle[] | null;
  commandeFourniseur?: ICommandeFournisseur | null;
}

export class LigneCmdFournisseur implements ILigneCmdFournisseur {
  constructor(
    public id?: number,
    public cmdQnFr?: number | null,
    public cmdNmPieces?: number | null,
    public articles?: IArticle[] | null,
    public commandeFourniseur?: ICommandeFournisseur | null
  ) {}
}

export function getLigneCmdFournisseurIdentifier(ligneCmdFournisseur: ILigneCmdFournisseur): number | undefined {
  return ligneCmdFournisseur.id;
}
