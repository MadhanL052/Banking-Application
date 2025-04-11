package com.madhanl.Banking.Application.Service;

import com.madhanl.Banking.Application.Model.Account;
import com.madhanl.Banking.Application.Repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired
    private AccountRepo accountRepo;

    public Account createAccount(Account account){
        return accountRepo.save(account);
    }

    public double checkBalance(int accountId){
        Account account = null;
        if(accountRepo.findById(accountId).isPresent()) {
            account = this.readAccount(accountId);
            return account.getBalance();
        } else {
            return 0;
        }

    }

    public void deleteAccount(int accountId){
        if(accountRepo.findById(accountId).isPresent())
            accountRepo.deleteById(accountId);
    }

    public Account readAccount(int accountId){
        return accountRepo.findById(accountId).orElse(new Account());
    }


    public Account updateAccount(int accountId, Account account) {
        if(accountRepo.findById(accountId).isPresent()){
            return accountRepo.save(account);
        } else {
            return this.createAccount(account);
        }
    }

    public Account depositAmountByAccountId(int accountId, double balance){
        accountRepo.depositBalanceByAccountId(accountId , balance);
        return accountRepo.findById(accountId).orElse(new Account());
    }

    public Account withdrawAmountByAccountId(int accountId, double balance){
        accountRepo.withdrawAmountByAccountId(accountId , balance);
        return accountRepo.findById(accountId).orElse(new Account());
    }

    public HashMap<String , Account> transferAmountToAccountId(int sendingAccountId , int receivingAccountId , double balance){

        HashMap<String,  Account> transaction = new HashMap<String, Account>();

        accountRepo.withdrawAmountByAccountId(sendingAccountId , balance);
        transaction.put("Sending Account" , accountRepo.findById(sendingAccountId).orElse(new Account()));
        accountRepo.depositBalanceByAccountId(receivingAccountId , balance);
        transaction.put("Receiving Account" , accountRepo.findById(receivingAccountId).orElse(new Account()));

        return transaction;

    }


}
