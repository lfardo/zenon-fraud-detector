package br.com.zenon;

import java.io.*;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class TransactionIngestor {


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
