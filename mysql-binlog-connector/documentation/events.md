# MySQL raw events
The following are the raw events received after each SQL (DDL/DML) statement.

`create table taxify_builder.mytable2 (id int, name varchar(255));`

`Connecting to mysql instance`
- Event{header=EventHeaderV4{timestamp=0, eventType=ROTATE, serverId=223344, headerLength=19, dataLength=28, nextPosition=0, flags=32}, data=RotateEventData{binlogFilename='mysql-bin.000001', binlogPosition=1634}}
- Event{header=EventHeaderV4{timestamp=1591110925000, eventType=FORMAT_DESCRIPTION, serverId=223344, headerLength=19, dataLength=100, nextPosition=0, flags=0}, data=FormatDescriptionEventData{binlogVersion=4, serverVersion='5.7.22-22-log', headerLength=19, dataLength=95, checksumType=CRC32}}


`insert into taxify_builder.mytable2 values (10, "foo");`
- Event{header=EventHeaderV4{timestamp=1591170123000, eventType=GTID, serverId=223344, headerLength=19, dataLength=46, nextPosition=428, flags=0}, data=GtidEventData{flags=0, gtid='344bde49-d928-11e9-846d-0242ac110003:2'}}
- Event{header=EventHeaderV4{timestamp=1591170123000, eventType=QUERY, serverId=223344, headerLength=19, dataLength=49, nextPosition=496, flags=8}, data=QueryEventData{threadId=9, executionTime=0, errorCode=0, database='', sql='BEGIN'}}
- Event{header=EventHeaderV4{timestamp=1591170123000, eventType=TABLE_MAP, serverId=223344, headerLength=19, dataLength=45, nextPosition=560, flags=0}, data=TableMapEventData{tableId=874, database='taxify_builder', table='mytable2', columnTypes=3, 15, columnMetadata=0, 765, columnNullability={0, 1}}}
- Event{header=EventHeaderV4{timestamp=1591170123000, eventType=EXT_WRITE_ROWS, serverId=223344, headerLength=19, dataLength=26, nextPosition=605, flags=0}, data=WriteRowsEventData{tableId=874, includedColumns={0, 1}, rows=[
    [10, [B@246a48ec]
]}}
- Event{header=EventHeaderV4{timestamp=1591170123000, eventType=XID, serverId=223344, headerLength=19, dataLength=12, nextPosition=636, flags=0}, data=XidEventData{xid=907}}


```
begin;
insert into taxify_builder.mytable2 values (11, "foo");
insert into taxify_builder.mytable2 values (12, "foo");
insert into taxify_builder.mytable2 values (13, "foo");
rollback;
```
// nothing


```
begin;
insert into taxify_builder.mytable2 values (14, "foo");
insert into taxify_builder.mytable2 values (15, "foo");
commit;
```
- Event{header=EventHeaderV4{timestamp=1591170869000, eventType=GTID, serverId=223344, headerLength=19, dataLength=46, nextPosition=701, flags=0}, data=GtidEventData{flags=0, gtid='344bde49-d928-11e9-846d-0242ac110003:3'}}
- Event{header=EventHeaderV4{timestamp=1591170869000, eventType=QUERY, serverId=223344, headerLength=19, dataLength=49, nextPosition=769, flags=8}, data=QueryEventData{threadId=9, executionTime=0, errorCode=0, database='', sql='BEGIN'}}
- Event{header=EventHeaderV4{timestamp=1591170869000, eventType=TABLE_MAP, serverId=223344, headerLength=19, dataLength=45, nextPosition=833, flags=0}, data=TableMapEventData{tableId=874, database='taxify_builder', table='mytable2', columnTypes=3, 15, columnMetadata=0, 765, columnNullability={0, 1}}}
- Event{header=EventHeaderV4{timestamp=1591170869000, eventType=EXT_WRITE_ROWS, serverId=223344, headerLength=19, dataLength=26, nextPosition=878, flags=0}, data=WriteRowsEventData{tableId=874, includedColumns={0, 1}, rows=[
    [14, [B@7db42202]
]}}
- Event{header=EventHeaderV4{timestamp=1591170869000, eventType=TABLE_MAP, serverId=223344, headerLength=19, dataLength=45, nextPosition=942, flags=0}, data=TableMapEventData{tableId=874, database='taxify_builder', table='mytable2', columnTypes=3, 15, columnMetadata=0, 765, columnNullability={0, 1}}}
- Event{header=EventHeaderV4{timestamp=1591170869000, eventType=EXT_WRITE_ROWS, serverId=223344, headerLength=19, dataLength=26, nextPosition=987, flags=0}, data=WriteRowsEventData{tableId=874, includedColumns={0, 1}, rows=[
    [15, [B@b6f3870]
]}}
- Event{header=EventHeaderV4{timestamp=1591170869000, eventType=XID, serverId=223344, headerLength=19, dataLength=12, nextPosition=1018, flags=0}, data=XidEventData{xid=914}}


`update taxify_builder.mytable2 set name = "bar" where id = 15;`
- Event{header=EventHeaderV4{timestamp=1591170899000, eventType=GTID, serverId=223344, headerLength=19, dataLength=46, nextPosition=1083, flags=0}, data=GtidEventData{flags=0, gtid='344bde49-d928-11e9-846d-0242ac110003:4'}}
- Event{header=EventHeaderV4{timestamp=1591170899000, eventType=QUERY, serverId=223344, headerLength=19, dataLength=49, nextPosition=1151, flags=8}, data=QueryEventData{threadId=9, executionTime=0, errorCode=0, database='', sql='BEGIN'}}
- Event{header=EventHeaderV4{timestamp=1591170899000, eventType=TABLE_MAP, serverId=223344, headerLength=19, dataLength=45, nextPosition=1215, flags=0}, data=TableMapEventData{tableId=874, database='taxify_builder', table='mytable2', columnTypes=3, 15, columnMetadata=0, 765, columnNullability={0, 1}}}
- Event{header=EventHeaderV4{timestamp=1591170899000, eventType=EXT_UPDATE_ROWS, serverId=223344, headerLength=19, dataLength=37, nextPosition=1271, flags=0}, data=UpdateRowsEventData{tableId=874, includedColumnsBeforeUpdate={0, 1}, includedColumns={0, 1}, rows=[
    {before=[15, [B@79b4f1a0], after=[15, [B@b6afa5e]}
]}}
- Event{header=EventHeaderV4{timestamp=1591170899000, eventType=XID, serverId=223344, headerLength=19, dataLength=12, nextPosition=1302, flags=0}, data=XidEventData{xid=917}}


`delete from taxify_builder.mytable2 where id = 15;`
- Event{header=EventHeaderV4{timestamp=1591170929000, eventType=GTID, serverId=223344, headerLength=19, dataLength=46, nextPosition=1367, flags=0}, data=GtidEventData{flags=0, gtid='344bde49-d928-11e9-846d-0242ac110003:5'}}
- Event{header=EventHeaderV4{timestamp=1591170929000, eventType=QUERY, serverId=223344, headerLength=19, dataLength=49, nextPosition=1435, flags=8}, data=QueryEventData{threadId=9, executionTime=0, errorCode=0, database='', sql='BEGIN'}}
- Event{header=EventHeaderV4{timestamp=1591170929000, eventType=TABLE_MAP, serverId=223344, headerLength=19, dataLength=45, nextPosition=1499, flags=0}, data=TableMapEventData{tableId=874, database='taxify_builder', table='mytable2', columnTypes=3, 15, columnMetadata=0, 765, columnNullability={0, 1}}}
- Event{header=EventHeaderV4{timestamp=1591170929000, eventType=EXT_DELETE_ROWS, serverId=223344, headerLength=19, dataLength=26, nextPosition=1544, flags=0}, data=DeleteRowsEventData{tableId=874, includedColumns={0, 1}, rows=[
    [15, [B@73ca9c1b]
]}}
- Event{header=EventHeaderV4{timestamp=1591170929000, eventType=XID, serverId=223344, headerLength=19, dataLength=12, nextPosition=1575, flags=0}, data=XidEventData{xid=919}}


`create table taxify_builder.mytable3 (id int, name varchar(255), now timestamp);`
- Event{header=EventHeaderV4{timestamp=1591171499000, eventType=GTID, serverId=223344, headerLength=19, dataLength=46, nextPosition=1640, flags=0}, data=GtidEventData{flags=1, gtid='344bde49-d928-11e9-846d-0242ac110003:6'}}
- Event{header=EventHeaderV4{timestamp=1591171499000, eventType=QUERY, serverId=223344, headerLength=19, dataLength=142, nextPosition=1801, flags=0}, data=QueryEventData{threadId=9, executionTime=0, errorCode=0, database='', sql='create table taxify_builder.mytable3 (id int, name varchar(255), now timestamp)'}}


`insert into taxify_builder.mytable3 values (10, "foo", "2020-01-01");`
- Event{header=EventHeaderV4{timestamp=1591171576000, eventType=GTID, serverId=223344, headerLength=19, dataLength=46, nextPosition=1866, flags=0}, data=GtidEventData{flags=0, gtid='344bde49-d928-11e9-846d-0242ac110003:7'}}
- Event{header=EventHeaderV4{timestamp=1591171576000, eventType=QUERY, serverId=223344, headerLength=19, dataLength=57, nextPosition=1942, flags=8}, data=QueryEventData{threadId=9, executionTime=0, errorCode=0, database='', sql='BEGIN'}}
- Event{header=EventHeaderV4{timestamp=1591171576000, eventType=TABLE_MAP, serverId=223344, headerLength=19, dataLength=47, nextPosition=2008, flags=0}, data=TableMapEventData{tableId=875, database='taxify_builder', table='mytable3', columnTypes=3, 15, 17, columnMetadata=0, 765, 0, columnNullability={0, 1, 2}}}
- Event{header=EventHeaderV4{timestamp=1591171576000, eventType=EXT_WRITE_ROWS, serverId=223344, headerLength=19, dataLength=30, nextPosition=2057, flags=0}, data=WriteRowsEventData{tableId=875, includedColumns={0, 1, 2}, rows=[
    [10, [B@2fae3036, 1577836800000]
]}}
- Event{header=EventHeaderV4{timestamp=1591171576000, eventType=XID, serverId=223344, headerLength=19, dataLength=12, nextPosition=2088, flags=0}, data=XidEventData{xid=923}}


`alter table taxify_builder.mytable3 add column age int;`
- Event{header=EventHeaderV4{timestamp=1591171700000, eventType=GTID, serverId=223344, headerLength=19, dataLength=46, nextPosition=2153, flags=0}, data=GtidEventData{flags=1, gtid='344bde49-d928-11e9-846d-0242ac110003:8'}}
- Event{header=EventHeaderV4{timestamp=1591171700000, eventType=QUERY, serverId=223344, headerLength=19, dataLength=115, nextPosition=2287, flags=0}, data=QueryEventData{threadId=9, executionTime=0, errorCode=0, database='', sql='alter table taxify_builder.mytable3 add column age int'}}


`insert into taxify_builder.mytable3 values (10, "foo", "2020-01-01", 20);`
- Event{header=EventHeaderV4{timestamp=1591171744000, eventType=GTID, serverId=223344, headerLength=19, dataLength=46, nextPosition=2352, flags=0}, data=GtidEventData{flags=0, gtid='344bde49-d928-11e9-846d-0242ac110003:9'}}
- Event{header=EventHeaderV4{timestamp=1591171744000, eventType=QUERY, serverId=223344, headerLength=19, dataLength=57, nextPosition=2428, flags=8}, data=QueryEventData{threadId=9, executionTime=0, errorCode=0, database='', sql='BEGIN'}}
- Event{header=EventHeaderV4{timestamp=1591171744000, eventType=TABLE_MAP, serverId=223344, headerLength=19, dataLength=48, nextPosition=2495, flags=0}, data=TableMapEventData{tableId=876, database='taxify_builder', table='mytable3', columnTypes=3, 15, 17, 3, columnMetadata=0, 765, 0, 0, columnNullability={0, 1, 2, 3}}}
- Event{header=EventHeaderV4{timestamp=1591171744000, eventType=EXT_WRITE_ROWS, serverId=223344, headerLength=19, dataLength=34, nextPosition=2548, flags=0}, data=WriteRowsEventData{tableId=876, includedColumns={0, 1, 2, 3}, rows=[
    [10, [B@359ae9bb, 1577836800000, 20]
]}}
- Event{header=EventHeaderV4{timestamp=1591171744000, eventType=XID, serverId=223344, headerLength=19, dataLength=12, nextPosition=2579, flags=0}, data=XidEventData{xid=927}}
