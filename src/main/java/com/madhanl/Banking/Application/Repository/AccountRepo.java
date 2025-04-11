package com.madhanl.Banking.Application.Repository;

import com.madhanl.Banking.Application.Model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AccountRepo extends JpaRepository<Account, Integer> {

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Account a SET a.balance = a.balance + ?2 WHERE a.id = ?1")
    void depositBalanceByAccountId(int accountId, double balance);


    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE Account a SET a.balance = a.balance - ?2 WHERE a.id = ?1")
    void withdrawAmountByAccountId(int accountId, double balance);

}
