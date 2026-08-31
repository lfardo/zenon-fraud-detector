package br.com.zenon;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

public class TransactionReport {

    private record ReportTransaction(BigDecimal amount, boolean isFraud){
    }

    public record Statistics (long totalTransaction, long totalFrauds, BigDecimal totalAmount) {

        private final static Statistics ZERO = new Statistics(0,0, BigDecimal.ZERO);

        private Statistics addReportTransaction(ReportTransaction rt) {
            return new Statistics(
                    totalTransaction + 1,
                    totalFrauds + (rt.isFraud ? 1 : 0),
                    totalAmount.add(rt.amount)
            );
        }

        private Statistics add(Statistics other) {
            return new Statistics(
                    totalTransaction + other.totalTransaction(),
                    totalFrauds + other.totalTransaction(),
                    totalAmount.add(other.totalAmount)
            );
        }
    }

    public Statistics generateReport(String filename) {
        Path path = Path.of(filename);
        try (Stream<String> lines = Files.lines(path)) {
             return lines
                .skip(1)
                .map(this::parseReportTransaction)
                 .filter(Optional::isPresent)
                 . map(Optional::get)
                 .reduce(
                     Statistics.ZERO,
                     Statistics::addReportTransaction, Statistics::add);

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo: " + filename, e);
        }
    }

    public Stream<Optional<Transaction>> read(String filename) throws IOException {
        Path path = Path.of(filename);
        return (Stream<Optional<Transaction>>) Files.lines(path)
                .skip(1)
                .map(this::parseLine);
    }

    private Optional<ReportTransaction> parseReportTransaction(String line) {
        try {
            String[] chunks = line.split(",");

            if(chunks[2] == null || chunks[2].trim().isEmpty()) throw new IllegalArgumentException("Amount is null");
            BigDecimal amount = new BigDecimal(chunks[2]);

            boolean isFraud = "1".equals(chunks[9]);

            return Optional.of(new ReportTransaction(amount, isFraud));
        } catch (Exception e) {
            System.err.println("Erro: " + line + " | " + e);
            return Optional.empty();
        }
    }

    private Optional<Transaction> parseLine(String line) {
        try {
            String[] chunks = line.split(",");
            int step = Integer.parseInt(chunks[0]);
            TransactionType type = TransactionType.valueOf(chunks[1]);

            if(chunks[2] == null || chunks[2].trim().isEmpty()) throw new IllegalArgumentException("Amount is null");
            BigDecimal amount = new BigDecimal(chunks[2]);

            TransactionCustomer origin = new TransactionCustomer(chunks[3], new BigDecimal(chunks[4]), new BigDecimal(chunks[5]));
            TransactionCustomer recipient = new TransactionCustomer(chunks[6], new BigDecimal(chunks[7]), new BigDecimal(chunks[8]));
            boolean isFraud = "1".equals(chunks[9]);
            boolean isFlaggedFraud = "1".equals(chunks[10]);

            return Optional.of(new Transaction(step, type, amount, origin, recipient, isFraud, isFlaggedFraud));
        } catch (Exception e) {
            System.err.println("Erro: " + line + " | " + e);
            return Optional.empty();
        }
    }

}
