package br.com.fiap.grupo30.fastfood.payments_api.infrastructure.persistence.repositories;

import br.com.fiap.grupo30.fastfood.payments_api.infrastructure.persistence.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, Long> {}
