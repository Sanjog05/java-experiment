package io.keweishang.mysqlbinlogconnector;

import com.github.shyiko.mysql.binlog.event.deserialization.json.JsonBinary;

import java.io.IOException;

public class DecodeBinlogJson {
  public static void main(String[] args) throws IOException {
    //'\x02\x01\x00O\x00\x00\x07\x00\x02\x00H\x00\x12\x00\x04\x00\x16\x00\x06\x00\x0c\x1c\x00\x00&\x00type'
    Object data =
        new byte[] {
          2, 1, 0, 79, 0, 0, 7, 0, 2, 0, 72, 0, 18, 0, 4, 0, 22, 0, 6, 0, 12, 28, 0, 0, 38, 0, 116,
          121, 112, 101
        };
    String jsonString = JsonBinary.parseAsString((byte[]) data);
    System.out.println(jsonString);
  }
}
