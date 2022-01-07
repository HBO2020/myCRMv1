import { IArticle } from 'app/entities/article/article.model';

export interface IUniteArticle {
  id?: number;
  uniteCode?: number | null;
  uniteLibelle?: string | null;
  uniteOption?: string | null;
  articles?: IArticle[] | null;
}

export class UniteArticle implements IUniteArticle {
  constructor(
    public id?: number,
    public uniteCode?: number | null,
    public uniteLibelle?: string | null,
    public uniteOption?: string | null,
    public articles?: IArticle[] | null
  ) {}
}

export function getUniteArticleIdentifier(uniteArticle: IUniteArticle): number | undefined {
  return uniteArticle.id;
}
