import { ILigneCmdFournisseur } from 'app/entities/ligne-cmd-fournisseur/ligne-cmd-fournisseur.model';
import { ILigneLivFournisseur } from 'app/entities/ligne-liv-fournisseur/ligne-liv-fournisseur.model';
import { IUniteArticle } from 'app/entities/unite-article/unite-article.model';
import { ILigneCmdClient } from 'app/entities/ligne-cmd-client/ligne-cmd-client.model';
import { ILigneLivClient } from 'app/entities/ligne-liv-client/ligne-liv-client.model';

export interface IArticle {
  id?: number;
  artclIden?: number | null;
  artclReference?: string | null;
  artclDesignation?: string | null;
  artclQnStock?: number | null;
  artclImgContentType?: string | null;
  artclImg?: string | null;
  artclSerie?: string | null;
  artclPrixAchat?: number | null;
  artclPxAchatTotal?: number | null;
  artclPxVenteTotal?: number | null;
  ligneCmdFournisseur?: ILigneCmdFournisseur | null;
  ligneLivFournisseur?: ILigneLivFournisseur | null;
  uniteArticle?: IUniteArticle | null;
  ligneCmdClient?: ILigneCmdClient | null;
  ligneLivClient?: ILigneLivClient | null;
}

export class Article implements IArticle {
  constructor(
    public id?: number,
    public artclIden?: number | null,
    public artclReference?: string | null,
    public artclDesignation?: string | null,
    public artclQnStock?: number | null,
    public artclImgContentType?: string | null,
    public artclImg?: string | null,
    public artclSerie?: string | null,
    public artclPrixAchat?: number | null,
    public artclPxAchatTotal?: number | null,
    public artclPxVenteTotal?: number | null,
    public ligneCmdFournisseur?: ILigneCmdFournisseur | null,
    public ligneLivFournisseur?: ILigneLivFournisseur | null,
    public uniteArticle?: IUniteArticle | null,
    public ligneCmdClient?: ILigneCmdClient | null,
    public ligneLivClient?: ILigneLivClient | null
  ) {}
}

export function getArticleIdentifier(article: IArticle): number | undefined {
  return article.id;
}
