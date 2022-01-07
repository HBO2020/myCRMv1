import { IArticle } from 'app/entities/article/article.model';
import { ICommandeClient } from 'app/entities/commande-client/commande-client.model';

export interface ILigneCmdClient {
  id?: number;
  cmdQnCl?: number | null;
  cmdNmPiecesCl?: number | null;
  articles?: IArticle[] | null;
  commandeClient?: ICommandeClient | null;
}

export class LigneCmdClient implements ILigneCmdClient {
  constructor(
    public id?: number,
    public cmdQnCl?: number | null,
    public cmdNmPiecesCl?: number | null,
    public articles?: IArticle[] | null,
    public commandeClient?: ICommandeClient | null
  ) {}
}

export function getLigneCmdClientIdentifier(ligneCmdClient: ILigneCmdClient): number | undefined {
  return ligneCmdClient.id;
}
