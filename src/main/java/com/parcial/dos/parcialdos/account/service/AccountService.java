package com.parcial.dos.parcialdos.account.service;

import com.parcial.dos.parcialdos.account.dto.AccountOwnerBalanceDTO;
import com.parcial.dos.parcialdos.account.dto.AccountRequestDTO;
import com.parcial.dos.parcialdos.account.dto.AccountResponseDTO;
import com.parcial.dos.parcialdos.account.entity.Account;
import com.parcial.dos.parcialdos.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    // Convertir Entity a ResponseDTO
    private AccountResponseDTO convertToResponseDTO(Account account) {
        return new AccountResponseDTO(
                account.getId(),
                account.getAccountNumber(),
                account.getOwnerName(),
                account.getBalance(),
                account.getActive()
        );
    }

    // Convertir RequestDTO a Entity
    private Account convertToEntity(AccountRequestDTO dto) {
        Account account = new Account();
        account.setAccountNumber(dto.getNumeroCuenta());
        account.setOwnerName(dto.getDueno());
        account.setBalance(dto.getBalanceActual());
        account.setActive(true);
        return account;
    }

    @Override
    public AccountResponseDTO createAccount(AccountRequestDTO accountRequestDTO) {
        // Verificar si ya existe una cuenta con ese número
        if (accountRepository.existsByAccountNumber(accountRequestDTO.getNumeroCuenta())) {
            throw new RuntimeException("Ya existe una cuenta con el número: " + accountRequestDTO.getNumeroCuenta());
        }

        // Convertir DTO a Entity y guardar
        Account account = convertToEntity(accountRequestDTO);
        Account savedAccount = accountRepository.save(account);

        // Convertir Entity guardado a ResponseDTO y retornar
        return convertToResponseDTO(savedAccount);
    }

    @Override
    public List<AccountResponseDTO> getAllAccounts() {
        // Obtener todas las cuentas
        List<Account> accounts = accountRepository.findAll();

        // Convertir cada Entity a ResponseDTO
        return accounts.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AccountResponseDTO getAccountById(Long id) {
        // Buscar cuenta por ID, si no existe lanzar excepción
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con ID: " + id));

        return convertToResponseDTO(account);
    }

    @Override
    public String updateAccountBalance(Long id, AccountRequestDTO accountRequestDTO) {
        // Buscar cuenta existente
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada"));

        // Guardar el balance anterior
        BigDecimal balanceAnterior = existingAccount.getBalance();

        // ✅ CORRECCIÓN: Actualizar SOLO el balance, ignorar otros campos
        existingAccount.setBalance(accountRequestDTO.getBalanceActual());

        // Guardar los cambios
        accountRepository.save(existingAccount);

        // ✅ CORRECCIÓN: Retornar mensaje EXACTO como pide el profesor
        return String.format("La cuenta %s fue actualizada: balanceAnterior=%.2f, balanceActual=%.2f",
                existingAccount.getAccountNumber(), balanceAnterior, accountRequestDTO.getBalanceActual());
    }

    @Override
    public void deleteAccount(Long id) {
        // Verificar si la cuenta existe
        if (!accountRepository.existsById(id)) {
            throw new RuntimeException("Cuenta no encontrada con ID: " + id);
        }

        // Eliminar la cuenta
        accountRepository.deleteById(id);
    }

    @Override
    public AccountOwnerBalanceDTO getAccountByNumber(String numeroCuenta) {
        // Buscar cuenta por número
        Account account = accountRepository.findByAccountNumber(numeroCuenta)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con número: " + numeroCuenta));

        // Retornar solo dueño y balance
        return new AccountOwnerBalanceDTO(account.getOwnerName(), account.getBalance());
    }
}