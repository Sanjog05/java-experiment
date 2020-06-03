package io.keweishang.mysqlbinlogconnector;

import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MySQLBinlogReaderApp {

    public static void main(String[] args) throws IOException, TimeoutException {
        BinaryLogClient client = new BinaryLogClient("localhost", 3307, "root", "");
        EventDeserializer eventDeserializer = new EventDeserializer();
        eventDeserializer.setCompatibilityMode(
                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY
        );
        client.setEventDeserializer(eventDeserializer);
        client.registerEventListener(new BinaryLogClient.EventListener() {

            @Override
            public void onEvent(Event event) {
                System.out.printf("%s\n", event);
            }
        });
        client.connect(1000 * 5L);
    }
}
