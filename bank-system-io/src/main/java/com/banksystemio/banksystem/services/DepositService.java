package com.banksystemio.banksystem.services;

import com.banksystemio.banksystem.entities.Account;
import com.banksystemio.banksystem.entities.Deposit;
import com.banksystemio.banksystem.repositories.DepositRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class DepositService {

    @Autowired
    private DepositRepository depositRepository;
    @Autowired
    private AccountService accountService;


    public List<Deposit> findAllDeposits() {
        return depositRepository.findAll();
    }

    @Transactional
    public void depositAmount(BigDecimal amount, Long id) {

        if (amount == null) {
            System.out.println("valor nulo");

        } else if (amount.compareTo(BigDecimal.ZERO) < 0) {
            System.out.println("valor Negativo");

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
                    System.out.println("erro ao depositar: " + e);
                }


            }
        }


    }
}
