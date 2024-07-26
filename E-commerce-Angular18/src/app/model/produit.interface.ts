export interface Produit{
  id:number;
  code_pro:string;
  nom_pro:string;
  categorie:string;
  description:string;
  prix_pro:number;
  image_pro?:string;
  isLikedByCurrentUser?: boolean; 
  }
