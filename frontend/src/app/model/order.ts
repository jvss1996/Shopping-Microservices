export interface Order {
    id?: number;
    orderNumber?: string;
    skuCode: string;
    price: number;
    quantity: number;
    userDetails: UserDetails;
}

export interface UserDetails {
    firstName: string;
    lastName: string;
    email: string;
}