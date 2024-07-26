import { Produit } from "./produit.interface";
import { User } from "./user.interface";

export interface  ComandeDb{
  id:number;
  qte_com :number;
  user:User;
  produit:Produit;
  statut_com :string;
  date_com:Date;
}
