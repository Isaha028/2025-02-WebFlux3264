package com.parcial.dos.parcialdos.account.service;

import com.parcial.dos.parcialdos.account.dto.AccountOwnerBalanceDTO;
import com.parcial.dos.parcialdos.account.dto.AccountRequestDTO;
import com.parcial.dos.parcialdos.account.dto.AccountResponseDTO;

import java.util.List;

// Interfaz que define los métodos del servicio de cuentas
public interface IAccountService {

    // Crear una nueva cuenta
    AccountResponseDTO createAccount(AccountRequestDTO accountRequestDTO);

    // Obtener todas las cuentas
    List<AccountResponseDTO> getAllAccounts();

    // Obtener cuenta por ID
    AccountResponseDTO getAccountById(Long id);

    // Actualizar balance de una cuenta
    String updateAccountBalance(Long id, AccountRequestDTO accountRequestDTO);

    // Eliminar una cuenta
    void deleteAccount(Long id);

    // Buscar cuenta por número y devolver dueño y balance
    AccountOwnerBalanceDTO getAccountByNumber(String numeroCuenta);
}