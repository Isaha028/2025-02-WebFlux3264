package com.parcial.dos.parcialdos.account.repository;

import com.parcial.dos.parcialdos.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // Buscar cuenta por número de cuenta
    Optional<Account> findByAccountNumber(String accountNumber);

    // Verificar si existe una cuenta con ese número
    boolean existsByAccountNumber(String accountNumber);
}