SocketChannelClient and SocketChannelClient test how the following works:
 
 - How to read to ByteBuffer from Channel
 
 - How to write to Channel from ByteBuffer
 
 - Read(buf) from channel is blocked until resource (another socket's writing) is available
 
 - Read(buf) from channel returns -1 when it's the end of resource (another socket closing connection)
 
 - Write(buf) to channel is not blocked