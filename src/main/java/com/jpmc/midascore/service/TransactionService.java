package com.jpmc.midascore.service;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.TransactionRecordRepository;
import com.jpmc.midascore.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TransactionService {
    private final UserRepository userRepository;
    private final TransactionRecordRepository transactionRecordRepository;
    private final RestTemplate http;

    @Value("${incentive.url:http://localhost:8080/incentive}")
    private String incentiveUrl;

    public TransactionService(
            UserRepository userRepository,
            TransactionRecordRepository transactionRecordRepository,
            RestTemplate http) {
        this.userRepository = userRepository;
        this.transactionRecordRepository = transactionRecordRepository;
        this.http = http;
    }

    @Transactional
    public void processTransaction(Transaction txn) {
        UserRecord sender = userRepository.findById(txn.getSenderId());
        UserRecord recipient = userRepository.findById(txn.getRecipientId());

        if (sender == null || recipient == null) return;
        if (sender.getBalance() < txn.getAmount()) return;

        Incentive inc = http.postForObject(incentiveUrl, txn, Incentive.class);
        double incentive = inc.getAmount();
        System.out.println(incentive);

        sender.setBalance(sender.getBalance() - txn.getAmount());
        recipient.setBalance(recipient.getBalance() + txn.getAmount() + incentive);

        userRepository.save(sender);
        userRepository.save(recipient);

        TransactionRecord record = new TransactionRecord();
        record.setSender(sender);
        record.setRecipient(recipient);
        record.setAmount(txn.getAmount());
        record.setIncentive(incentive);
        transactionRecordRepository.save(record);
    }
}

