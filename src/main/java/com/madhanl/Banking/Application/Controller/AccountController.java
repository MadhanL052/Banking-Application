package com.madhanl.Banking.Application.Controller;


import com.madhanl.Banking.Application.Model.Account;
import com.madhanl.Banking.Application.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService service;

    @GetMapping("/read/{accountId}")
    public ResponseEntity<Account> readAccount(@PathVariable int accountId){
        Account account = service.readAccount(accountId);
        if(account != null)
            return new ResponseEntity<>(account , HttpStatus.OK);
        else
            return new ResponseEntity<>(new Account() , HttpStatus.NOT_FOUND);
    }

    @GetMapping("/balance/{accountId}")
    public ResponseEntity<String> checkAccountBalance(@PathVariable int accountId){
        double balance = service.checkBalance(accountId);
        return new ResponseEntity<>(Double.toString(balance) , HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody Account account){
        try {
            Account acc = service.createAccount(account);
            return new ResponseEntity<>(acc, HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{accountId}")
    public ResponseEntity<String> updateAccount(@PathVariable int accountId , @RequestBody Account account){
        Account a = null;
        try {
            a = service.updateAccount(accountId , account);
        } catch(Exception e){
            return new ResponseEntity<>("Failed to update",HttpStatus.BAD_REQUEST);
        }

        if(a != null){
            return new ResponseEntity<>("Update is successful",HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update",HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/delete/{accountId}")
    public void deleteAccount(@PathVariable int accountId){
        service.deleteAccount(accountId);
    }

    //Transactions
    @GetMapping("/deposit/{accountId}/{amount}")
    public ResponseEntity<Account> depositAmountByAccountId(@PathVariable int accountId, @PathVariable double amount){
        Account account = service.depositAmountByAccountId(accountId,amount);
        return new ResponseEntity<>(
                account,
                HttpStatus.OK
        );
    }

    @GetMapping("/withdraw/{accountId}/{amount}")
    public ResponseEntity<Account> withdrawAmountByAccountId(@PathVariable int accountId, @PathVariable double amount){
        Account account = service.withdrawAmountByAccountId(accountId,amount);
        return new ResponseEntity<>(
                account,
                HttpStatus.OK
        );
    }

    @GetMapping("/transfer/from/{sendingAccountId}/to/{receivingAccountId}/{amount}")
    public ResponseEntity<HashMap<String,Account>> transferAmountToAccountId(@PathVariable int sendingAccountId, @PathVariable int receivingAccountId, @PathVariable double amount){

        HashMap<String, Account> transaction = service.transferAmountToAccountId(
                sendingAccountId,
                receivingAccountId,
                amount
        );


        return new ResponseEntity<>(
                transaction,
                HttpStatus.OK
        );
    }





}
