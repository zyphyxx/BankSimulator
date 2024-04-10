package com.banksystemio.banksystem.controllers;

import com.banksystemio.banksystem.entities.TransferRequest;
import com.banksystemio.banksystem.services.OperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/bankio")
public class OperationController {

    @Autowired
    private OperationService operationService;

    @PostMapping("/deposit/{id}")
    public void deposit(@PathVariable Long id, @RequestBody BigDecimal amount) {
       operationService.deposit(id,amount);
    }

    @PostMapping("/withdraw/{id}")
    public void withdraw(@PathVariable Long id,@RequestBody BigDecimal amount) {
        operationService.withdraw(id,amount);
    }

    @GetMapping("/balance/{id}")
    public BigDecimal balance(@PathVariable Long id) {
        return operationService.balance(id);
    }

    // refazer
    @PutMapping("transfer/{originID}")
    public void transfer(@RequestBody TransferRequest transferRequest) {
        operationService.transfer(
                transferRequest.getOriginID(),
                transferRequest.getRecipientID(),
                transferRequest.getAmount(),
                transferRequest.getPasswordFalse());
    }


}
