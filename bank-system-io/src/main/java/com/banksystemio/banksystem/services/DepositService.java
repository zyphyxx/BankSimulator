package com.banksystemio.banksystem.services;

import com.banksystemio.banksystem.entities.Account;
import com.banksystemio.banksystem.entities.Deposit;
import com.banksystemio.banksystem.repositories.DepositRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class DepositService {

    @Autowired
    private DepositRepository depositRepository;
    @Autowired
    private AccountService accountService;

    @Transactional
    public void depositAmount(BigDecimal amount, Long id) {

        if (amount == null) {
            throw new RuntimeException("O valor não pode ser nullo");

        } else if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("O valor não pode ser negativo "+ amount);

        } else {

            Optional<Account> account = accountService.findAccountById(id);

            if (account.isPresent()) {

                Deposit deposit = new Deposit();
                BigDecimal balance = account.get().getBalance();

                try {
                    account.get().setBalance(balance.add(amount));

                    deposit.setAmount(amount);
                    deposit.setAccount(account.get());
                    depositRepository.save(deposit);

                } catch (Exception e) {
                    throw new RuntimeException("Erro ao  fazer o depositar: " + e);
                }

            }
        }
    }

    public List<Deposit> findAllDeposits() {
        return depositRepository.findAll();
    }

}
