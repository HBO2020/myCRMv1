import dayjs from 'dayjs/esm';
import { ILigneCmdClient } from 'app/entities/ligne-cmd-client/ligne-cmd-client.model';
import { IClient } from 'app/entities/client/client.model';
import { ILivraisonCl } from 'app/entities/livraison-cl/livraison-cl.model';

export interface ICommandeClient {
  id?: number;
  cmdIdenCl?: number | null;
  cmdDateEffetCl?: dayjs.Dayjs | null;
  cmdDateLivraisonCl?: dayjs.Dayjs | null;
  ligneCmdClients?: ILigneCmdClient[] | null;
  client?: IClient | null;
  livraisonCl?: ILivraisonCl | null;
}

export class CommandeClient implements ICommandeClient {
  constructor(
    public id?: number,
    public cmdIdenCl?: number | null,
    public cmdDateEffetCl?: dayjs.Dayjs | null,
    public cmdDateLivraisonCl?: dayjs.Dayjs | null,
    public ligneCmdClients?: ILigneCmdClient[] | null,
    public client?: IClient | null,
    public livraisonCl?: ILivraisonCl | null
  ) {}
}

export function getCommandeClientIdentifier(commandeClient: ICommandeClient): number | undefined {
  return commandeClient.id;
}
