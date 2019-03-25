[![Build Status][travis-img]][travis]

# java-experiment
Little experiments on Java features.

## avro submodule
In order to run the avro submodule, run `mvn compile` first to generate java source code (`io.keweishang.avro.User`)
from avro schema. This is done via a the `avro-maven-plugin`. It generates source code in the `sourceDirectory`
configured in the plugin, which is `${project.basedir}/src/main/avro/`. For convenience, I've committed the generated
source code as well. But it's not necessary.

# Best Practises
- How to use ByteBuffer as a buffer to avoid system call (file I/O or socket I/O)
  1. you want to read n bytes
  2. ensure that the buffer still has >= n bytes remaining unread
    - if it has, read it, update the remaining unread counter
    - if not, you can move the remaining unread bytes to the front of ByteBuffer (compact), then write to the end of ByteBuffer from a Channel.

[travis]: https://travis-ci.org/keweishang/java-experiment
[travis-img]: https://travis-ci.org/keweishang/java-experiment.svg?branch=master
