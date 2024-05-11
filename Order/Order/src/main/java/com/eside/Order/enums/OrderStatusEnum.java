package com.eside.Order.enums;

public enum OrderStatusEnum {
    AWAITING_CONFIRMATION,
    UNDER_NEGOCIATION,
    AWAITING_PROCESSING,
    IN_TRANSIT,
    DELIVERED,
    PAYMENT_RECEIVED,
    CANCELLED,
    CONFIRMED, // La commande a été acceptée par le vendeur
    LISTED_FOR_WAITING,

}
