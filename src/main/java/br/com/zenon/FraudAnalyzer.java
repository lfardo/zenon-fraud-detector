package br.com.zenon;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.IO.println;

public class FraudAnalyzer {

    private final List<Transaction> transactions;

    public FraudAnalyzer(List<Transaction> transactions) {
        Objects.requireNonNull(transactions);
        this.transactions = transactions;
    }

    public long countFrauds() {
        return fraudsStream()
                .count();
    }

    public List<BigDecimal> findHighestValueFraudsAmounts(int limit) {
        return fraudsStream()
                .sorted(Comparator.comparing(Transaction::amount).reversed())
                .map(Transaction::amount)
                .limit(limit)
                .toList();
    }

    public List<String> findTopSuspiciousClients(int limit) {
        return transactions.stream()
                .filter(Transaction::isFraud)
                .sorted(Comparator.comparing(Transaction::amount).reversed())
                .distinct()
                .limit(limit)
                //.map(Transaction::origin)
                //.map(TransactionCustomer::name)
                .map( transaction -> transaction.origin().name())
                .toList();
    }

    public BigDecimal calculateTotalFraudLoss() {
        return fraudsStream()
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<TransactionType, Long> countFraudsByType() {
        return fraudsStream()
                .collect(Collectors.groupingBy(Transaction::type, Collectors.counting()));
    }

    private Stream<Transaction> fraudsStream() {
        return transactions
                .stream()
                .filter(Transaction::isFraud);
    }
}
