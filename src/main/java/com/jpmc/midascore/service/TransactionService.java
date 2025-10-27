package com.jpmc.midascore.service;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private final UserRepository userRepository;
    private final TransactionRecordRepository transactionRecordRepository;

    public TransactionService(UserRepository userRepository, TransactionRecordRepository transactionRecordRepository) {
        this.userRepository = userRepository;
        this.transactionRecordRepository = transactionRecordRepository;
    }

    @Transactional
    public void processTransaction(Transaction txn) {
        UserRecord sender = userRepository.findById(txn.getSenderId());
        UserRecord recipient = userRepository.findById(txn.getRecipientId());

        if (sender == null || recipient == null) return;
        if (sender.getBalance() < txn.getAmount()) return;

        sender.setBalance(sender.getBalance() - txn.getAmount());
        recipient.setBalance(recipient.getBalance() + txn.getAmount());

        userRepository.save(sender);
        userRepository.save(recipient);

        TransactionRecord record = new TransactionRecord();
        record.setSender(sender);
        record.setRecipient(recipient);
        record.setAmount(txn.getAmount());
        transactionRecordRepository.save(record);
    }
}

