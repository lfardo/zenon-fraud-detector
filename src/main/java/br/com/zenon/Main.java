package br.com.zenon;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() throws IOException {

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
    }
}
