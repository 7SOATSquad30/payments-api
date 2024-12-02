package br.com.fiap.grupo30.fastfood.payments_api.fixtures;

import br.com.fiap.grupo30.fastfood.payments_api.domain.entities.Payment;

public class PaymentFixtures {
    public static Payment valid() {
        return Payment.fromPersistence(PaymentEntityFixtures.valid());
    }

    public static Payment pending() {
        return Payment.fromPersistence(PaymentEntityFixtures.pending());
    }

    public static Payment collected() {
        return Payment.fromPersistence(PaymentEntityFixtures.collected());
    }

    public static Payment rejected() {
        return Payment.fromPersistence(PaymentEntityFixtures.rejected());
    }
}
