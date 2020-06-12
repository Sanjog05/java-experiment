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
        client.registerEventListener(event -> System.out.printf("%s\n", event));
        /* Insert 1 row
        Event{header=EventHeaderV4{timestamp=1591958619000, eventType=GTID, serverId=223344, headerLength=19, dataLength=46, nextPosition=582, flags=0}, data=GtidEventData{flags=0, gtid='294f815e-aa32-11ea-9580-0242ac110003:3'}}
        Event{header=EventHeaderV4{timestamp=1591958619000, eventType=QUERY, serverId=223344, headerLength=19, dataLength=56, nextPosition=657, flags=8}, data=QueryEventData{threadId=2, executionTime=0, errorCode=0, database='test_db', sql='BEGIN'}}
        Event{header=EventHeaderV4{timestamp=1591958619000, eventType=TABLE_MAP, serverId=223344, headerLength=19, dataLength=39, nextPosition=715, flags=0}, data=TableMapEventData{tableId=108, database='test_db', table='test_table', columnTypes=15, columnMetadata=765, columnNullability={0}}}
        Event{header=EventHeaderV4{timestamp=1591958619000, eventType=EXT_WRITE_ROWS, serverId=223344, headerLength=19, dataLength=21, nextPosition=755, flags=0}, data=WriteRowsEventData{tableId=108, includedColumns={0}, rows=[
            [[B@49167dab]
        ]}}
        Event{header=EventHeaderV4{timestamp=1591958619000, eventType=XID, serverId=223344, headerLength=19, dataLength=12, nextPosition=786, flags=0}, data=XidEventData{xid=13}}
         */

        /* Insert 2 rows in transaction
        Event{header=EventHeaderV4{timestamp=1591958769000, eventType=GTID, serverId=223344, headerLength=19, dataLength=46, nextPosition=851, flags=0}, data=GtidEventData{flags=0, gtid='294f815e-aa32-11ea-9580-0242ac110003:4'}}
        Event{header=EventHeaderV4{timestamp=1591958759000, eventType=QUERY, serverId=223344, headerLength=19, dataLength=56, nextPosition=926, flags=8}, data=QueryEventData{threadId=2, executionTime=0, errorCode=0, database='test_db', sql='BEGIN'}}
        Event{header=EventHeaderV4{timestamp=1591958759000, eventType=TABLE_MAP, serverId=223344, headerLength=19, dataLength=39, nextPosition=984, flags=0}, data=TableMapEventData{tableId=108, database='test_db', table='test_table', columnTypes=15, columnMetadata=765, columnNullability={0}}}
        Event{header=EventHeaderV4{timestamp=1591958759000, eventType=EXT_WRITE_ROWS, serverId=223344, headerLength=19, dataLength=21, nextPosition=1024, flags=0}, data=WriteRowsEventData{tableId=108, includedColumns={0}, rows=[
            [[B@70269bc5]
        ]}}
        Event{header=EventHeaderV4{timestamp=1591958763000, eventType=TABLE_MAP, serverId=223344, headerLength=19, dataLength=39, nextPosition=1082, flags=0}, data=TableMapEventData{tableId=108, database='test_db', table='test_table', columnTypes=15, columnMetadata=765, columnNullability={0}}}
        Event{header=EventHeaderV4{timestamp=1591958763000, eventType=EXT_WRITE_ROWS, serverId=223344, headerLength=19, dataLength=21, nextPosition=1122, flags=0}, data=WriteRowsEventData{tableId=108, includedColumns={0}, rows=[
            [[B@a274b4f]
        ]}}
        Event{header=EventHeaderV4{timestamp=1591958769000, eventType=XID, serverId=223344, headerLength=19, dataLength=12, nextPosition=1153, flags=0}, data=XidEventData{xid=16}}
         */
        client.connect(1000 * 5L);
    }
}
