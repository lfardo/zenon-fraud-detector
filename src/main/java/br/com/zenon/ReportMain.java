package br.com.zenon;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.stream.Stream;

public class ReportMain {

    static void main() {
        TransactionReport transactionReport = new TransactionReport();

        TransactionReport.Statistics statistics = transactionReport.generateReport("data/PS_20174392719_1491204439457_log.csv");
        IO.println("Total de linhas: " + statistics.totalTransaction());
        IO.println("Total de fraudes: " + statistics.totalFrauds());
        IO.println("Valor total transacionado: " + statistics.totalAmount());

        /*
        Stream<Optional<Transaction>> transactionStream = transactionReport.read("data/PS_20174392719_1491204439457_log.csv");

        ReportMain reportMain = new ReportMain(transactionStream);

        IO.println("Total de linhas: " + reportMain.total);
        IO.println("Total de fraudes: " + reportMain.frauds);
        IO.println("Valor total transacionado: " + reportMain.totalTransaction);

         */
    }

    public final Stream<Optional<Transaction>> transactionStream;

    long total = 0;
    long frauds = 0;
    BigDecimal totalTransaction = BigDecimal.ZERO;

    public ReportMain(Stream<Optional<Transaction>> transactionStream) {
        this.transactionStream = transactionStream;

        this.transactionStream.forEach(transaction -> {
            total++;
            if (transaction.isPresent() && transaction.get().isFraud()){
                frauds++;
            }
            totalTransaction = totalTransaction.add(transaction.get().amount());
        });
    }
/*
    public Long countFrauds() {
        return transactionStream
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(Transaction::isFraud)
                .count();

    }

    public Long countLines(){
        return transactionStream.count();
    }

 */
}
