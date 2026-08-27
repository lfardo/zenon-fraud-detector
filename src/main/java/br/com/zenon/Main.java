package br.com.zenon;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.lang.IO.println;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() throws IOException {
/*
        Transaction transaction = new Transaction(1,
                TransactionType.PAYMENT,
                new BigDecimal("9839.64"),
                new TransactionCustomer("C1231006815", new BigDecimal("170136.00"),new BigDecimal("160296.36")),
                new TransactionCustomer("M1979787155", new BigDecimal(0), new BigDecimal(0)),
                false,
                false);

        Transaction transaction2 = new Transaction(743,
                TransactionType.CASH_OUT,
                new BigDecimal("850002.52"),
                new TransactionCustomer("C1280323807",new BigDecimal("850002.52"),new BigDecimal("0.00")),
                new TransactionCustomer("C873221189",new BigDecimal("6510099.11"),new BigDecimal( "7360101.63")),
                true,
                false);

        IO.println(transaction);
        IO.println(transaction2);

        IO.println("--------------------------------------------------------");

        TransactionIngestor transactionIngestor = new TransactionIngestor();
        List<Transaction> transactions = transactionIngestor.read("data/PS_20174392719_1491204439457_log.csv");
        transactions.stream().limit(10).forEach(IO::println);

        IO.println("--------------------------------------------------------");

        List<Transaction> transactions2= transactionIngestor.read("data/paysim_with_bad_data.csv");
        IO.println(transactions2.size());
        transactions2.forEach(IO::println);


        /*
        Path filePath = Paths.get("data", "PS_20174392719_1491204439457_log.csv");
        IO.println(filePath.toString());
        IO.println(Path.of("data/PS_20174392719_1491204439457_log.csv"));


        List<Transaction> lista = transactionIngestor.processFile(Path.of(filePath.toUri()));
        int i = 1;
        for (Transaction transactionAux : lista) {
            IO.println(transactionAux);
            if(i++ >= 10) break;
        }*/

/*
        TransactionIngestor transactionIngestor = new TransactionIngestor();
        List<Transaction> transactions = transactionIngestor.read("data/PS_20174392719_1491204439457_log.csv");

        FraudAnalyzer fraudAnalyzer = new FraudAnalyzer(transactions);
        long countFrauds = fraudAnalyzer.countFrauds();
        println("1. Total de Fraudes: " +  countFrauds);

        List<BigDecimal> highestFrauds = fraudAnalyzer.findHighestValueFraudsAmounts(3);
        println("2. Top 3 Fraudes de maior valor:");
        highestFrauds.stream().forEach(amount -> println(String.format("%.2f", amount)));

        List<String> suspiciousClients = fraudAnalyzer.findTopSuspiciousClients(5);
        println("3. Clientes Suspeitos:");
        suspiciousClients.forEach(IO::println);

        BigDecimal totalFraudLoss = fraudAnalyzer.calculateTotalFraudLoss();
        println("4. Prejuizo Total: " + totalFraudLoss);

        Map<TransactionType, Long> fraudsCountByType = fraudAnalyzer.countFraudsByType();
        println("5. Fraudes por Tipo: ");
        fraudsCountByType.forEach((tipo, qtd) -> println(" - %s: %d".formatted(tipo, qtd)));
        
 */

        TransactionIngestor transactionIngestor = new TransactionIngestor();
        List<Transaction> transactions = transactionIngestor.read("data/PS_20174392719_1491204439457_log.csv");
        
        TransactionRepository transactionRepository = new TransactionListRepository(transactions);

        transactionRepository.findByOriginName("C12345")
                .ifPresentOrElse(IO::println, () -> IO.println("Transacao nao encontrada"));

        transactionRepository.findByOriginName("C1231006815")
                .ifPresentOrElse(IO::println, () -> IO.println("Transacao nao encontrada"));


        long start = System.nanoTime();
        Optional<Transaction> listTransaction3 = transactionRepository.findByOriginName("C1868032458");
        IO.println(listTransaction3);
        System.out.println("Tempo da Lista: " + (System.nanoTime() - start)/1_000_000.0 + "ms");


         transactionRepository = new TransactionMapRepository(transactions);

        transactionRepository.findByOriginName("C12345")
                .ifPresentOrElse(IO::println, () -> IO.println("Transacao nao encontrada"));

        transactionRepository.findByOriginName("C1231006815")
                .ifPresentOrElse(IO::println, () -> IO.println("Transacao nao encontrada"));


        start = System.nanoTime();
        Optional<Transaction> listTransaction4 = transactionRepository.findByOriginName("C1868032458");
        IO.println(listTransaction4);
        System.out.println("Tempo do Map: " + (System.nanoTime() - start)/1_000_000.0 + "ms");
    }
}
