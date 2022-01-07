import dayjs from 'dayjs/esm';
import { ILigneCmdFournisseur } from 'app/entities/ligne-cmd-fournisseur/ligne-cmd-fournisseur.model';
import { IFournisseur } from 'app/entities/fournisseur/fournisseur.model';
import { ILivraisonFr } from 'app/entities/livraison-fr/livraison-fr.model';

export interface ICommandeFournisseur {
  id?: number;
  cmdIdenFr?: number | null;
  cmdDateEffetFr?: dayjs.Dayjs | null;
  cmdDateLivraisonFr?: dayjs.Dayjs | null;
  ligneCmdFournisseurs?: ILigneCmdFournisseur[] | null;
  fournisseur?: IFournisseur | null;
  livraisonFr?: ILivraisonFr | null;
}

export class CommandeFournisseur implements ICommandeFournisseur {
  constructor(
    public id?: number,
    public cmdIdenFr?: number | null,
    public cmdDateEffetFr?: dayjs.Dayjs | null,
    public cmdDateLivraisonFr?: dayjs.Dayjs | null,
    public ligneCmdFournisseurs?: ILigneCmdFournisseur[] | null,
    public fournisseur?: IFournisseur | null,
    public livraisonFr?: ILivraisonFr | null
  ) {}
}

export function getCommandeFournisseurIdentifier(commandeFournisseur: ICommandeFournisseur): number | undefined {
  return commandeFournisseur.id;
}
