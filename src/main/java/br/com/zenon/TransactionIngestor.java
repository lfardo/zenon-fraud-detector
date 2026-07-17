package br.com.zenon;

import java.io.*;
import java.math.BigDecimal;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class TransactionIngestor {

    public List<Transaction> read(String filename) {
        Path path = Path.of(filename);
        try {
            List<String> lines = Files.readAllLines(path);
            return lines.stream()
                    .skip(1)
                    .limit(1000)
                    .map(this::parseTransaction)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Transaction> readOld(String filename) {
        List<Transaction> transactions = new ArrayList<>();
        try(FileInputStream fis =  new FileInputStream(filename);
                Scanner scanner =  new Scanner(fis)) {

            int lineCount = 0;
            while(scanner.hasNextLine() && lineCount++ <= 1000) {
                String line = scanner.nextLine();
                if(lineCount == 1) continue;
                Transaction transaction = parseTransaction(line).get();
                transactions.add(transaction);
            }
            return transactions;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<Transaction> parseTransaction(String line) {
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

    public List<Transaction> processFile(Path path) throws IOException {

        List<Transaction> listTransactions = new ArrayList<>();
        int countLines = 0;
        try(FileChannel channel = FileChannel.open(path, StandardOpenOption.READ);
        Reader reader = Channels.newReader(channel, StandardCharsets.UTF_8.newDecoder(), -1);
        BufferedReader br = new BufferedReader(reader)){
            String line;
            br.readLine();
            while((line = br.readLine()) != null && countLines++ < 1000) {
                String[] arrLine = line.split(",");
                Transaction transaction = processLine(arrLine);
                assert false;
                listTransactions.add(transaction);
            }

        }
        return listTransactions;
    }

    private Transaction processLine(String[] arrLine) {

        return new Transaction(Integer.parseInt(arrLine[0]), TransactionType.valueOf(arrLine[1]), new BigDecimal(arrLine[2]),
                new TransactionCustomer(arrLine[3], new BigDecimal(arrLine[4]), new BigDecimal(arrLine[5])),
                new TransactionCustomer(arrLine[6], new BigDecimal(arrLine[7]), new BigDecimal(arrLine[8])),
                         "1".equals(arrLine[9]), "1".equals(arrLine[10]));

    }


}
