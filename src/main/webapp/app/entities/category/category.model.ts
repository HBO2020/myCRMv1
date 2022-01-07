export interface ICategory {
  id?: number;
  categoryName?: string | null;
  categoryDesciption?: string | null;
}

export class Category implements ICategory {
  constructor(public id?: number, public categoryName?: string | null, public categoryDesciption?: string | null) {}
}

export function getCategoryIdentifier(category: ICategory): number | undefined {
  return category.id;
}
