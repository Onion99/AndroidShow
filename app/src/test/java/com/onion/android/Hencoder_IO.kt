package com.onion.android

import okio.Buffer
import okio.buffer
import okio.sink
import okio.source
import org.junit.Test
import java.io.*
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket
import java.nio.ByteBuffer
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel
import java.nio.charset.Charset


class Hencoder_IO {

    // 普通写
    @Test
    fun ioWrite() {
        // Stream类都实现了 Closeable,可以close掉
        val fileOutputStream = FileOutputStream("abc.txt")
        try {
            val array = byteArrayOf('a'.toByte(), 'b'.toByte(), 'c'.toByte())
            fileOutputStream.write(array)
        } finally {
            // 每次我们读写资源,都要将其放到内存去操作,所以操作完后,要及时释放资源
            // 不然会一直占用内存资源
            fileOutputStream.close()
        }
        // kotlin 简化
        /*fileOutputStream.use { fileOutputStream ->
            val array = byteArrayOf('a'.toByte(), 'b'.toByte(),'c'.toByte())
            fileOutputStream.write(array)
        }*/
    }

    // 普通读
    @Test
    fun ioRead() {
        // Stream类都实现了 Closeable,可以close掉
        val fileInputStream = FileInputStream("abc.txt")
        /*try {
            println(fileInputStream.read().toChar())
            println(fileInputStream.read().toChar())
        }finally {
            // 每次我们读写资源,都要放到内存去操作,所以操作完后,要及时释放资源
            fileInputStream.close()
        }*/
        // kotlin 简化
        fileInputStream.use { fileInputStream ->
            println(fileInputStream.read().toChar())
            println(fileInputStream.read().toChar())
        }
    }

    // 普通读-读字符串
    @Test
    fun ioReadString() {
        val fileInputStream = FileInputStream("abc.txt")
        fileInputStream.use {
            val reader = InputStreamReader(fileInputStream)
            // 每次读,都会增加额外的消耗,而Buffer的作用就是预先加载更多的数据,避免下次读时增加额外的消耗
            // 内存占用会多一点,但性能会更好
            val bufferedReader = BufferedReader(reader)
            println(bufferedReader.readLine())
            bufferedReader.close()
        }
    }


    // 普通写 - 加缓冲区, 加缓冲区作写入,当超出缓冲大小(默认8192)或者close时,才真正写进文件,避免多次操作带来的消耗
    @Test
    fun ioWriteBuffer() {
        // Stream类都实现了 Closeable,可以close掉
        val fileOutputStream = FileOutputStream("abc.txt")
        try {
            val bufferedOutputStream = BufferedOutputStream(fileOutputStream)
            bufferedOutputStream.write(byteArrayOf('q'.toByte(), 'w'.toByte(), 'r'.toByte()))
            // 用flush 推入 fileOutputStream,不理会缓冲区是否已满,强行写入一次
            // 或者closebufferedOutputStream,会自动推入 fileOutputStream
            bufferedOutputStream.close()
        } finally {
            fileOutputStream.close()
        }
    }

    // 数据复制
    @Test
    fun ioCopy() {
        // Stream类都实现了 Closeable,可以close掉
        val fileInputStream = FileInputStream("abc.txt")
        val fileOutputStream = FileOutputStream("abc_copy.txt")
        val data = ByteArray(1024)
        var read = 0
        // read() -> 读入缓冲区的总字节数，如果由于已到达文件末尾而没有更多数据，则为-1
        while ((fileInputStream.read(data).apply { read = this }) != -1) {
            fileOutputStream.write(data, 0, read)
        }
        fileOutputStream.close()
        fileInputStream.close()
    }

    // 数据复制-加入缓冲,性能更好
    @Test
    fun ioCopyBuffer() {
        // Stream类都实现了 Closeable,可以close掉
        val bufferedInputStream = BufferedInputStream(FileInputStream("abc.txt"))
        val bufferedOutputStream = BufferedOutputStream(FileOutputStream("abc_copy2.txt"))
        val data = ByteArray(1024)
        var read = 0
        // read() -> 读入缓冲区的总字节数，如果由于已到达文件末尾而没有更多数据，则为-1
        while ((bufferedInputStream.read(data).apply { read = this }) != -1) {
            bufferedOutputStream.write(data, 0, read)
        }
        bufferedOutputStream.close()
        bufferedInputStream.close()
    }

    // socket客户端,TCP 连接,数据读写
    @Test
    fun socketClientTest() {
        val socket = Socket("hencoder.com", 80)
        val bufferedWriter = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))
        val bufferedReader = BufferedReader(InputStreamReader(socket.getInputStream()))
        // 请求报文写入
        bufferedWriter.write(
            "GET / HTTP/1.1\n" +
                    "Host: www.12345.com\n\n"
        )
        bufferedWriter.flush()
        var message = ""
        while ((bufferedReader.readLine().apply { message = this }) != null) {
            println(message)
        }
    }

    // socket客户端服务端交互
    @Test
    fun socketServerTest() {
        // 系统默认访问: http://127.0.0.1/
        val serverSocket = ServerSocket(80)
        // 这里一直阻塞等待,等待别人连我们服务器的Socket,这种阻塞处理应该放在子线程中
        val socket = serverSocket.accept()
        val bufferedWriter = BufferedWriter(OutputStreamWriter(socket.getOutputStream()))
        bufferedWriter.write(
            "HTTP/1.1 200 OK\n" +
                    "Date: Mon, 23 May 2005 22:38:34 GMT\n" +
                    "Content-Type: text/html; charset=UTF-8\n" +
                    "Content-Length: 138\n" +
                    "Last-Modified: Wed, 08 Jan 2003 23:11:55 GMT\n" +
                    "Server: Apache/1.3.3.7 (Unix) (Red-Hat/Linux)\n" +
                    "ETag: \"3f80f-1b6-3e1cb03b\"\n" +
                    "Accept-Ranges: bytes\n" +
                    "Connection: close\n" +
                    "\n" +
                    "<html>\n" +
                    "  <head>\n" +
                    "    <title>An Example Page</title>\n" +
                    "  </head>\n" +
                    "  <body>\n" +
                    "    <p>Hello World, this is a very simple HTML document.</p>\n" +
                    "  </body>\n" +
                    "</html>\n\n"
        );
        bufferedWriter.flush()
    }

    // NIO-Channel -阻塞式
    @Test
    fun nIoChannel() {
        val file = RandomAccessFile("abc.txt", "r")
        val channel = file.channel
        // 分配容量上限
        val byteBuffer = ByteBuffer.allocate(1024)
        channel.read(byteBuffer)
        // 重置读取位置,从0开始
        byteBuffer.flip()
        println(Charset.defaultCharset().decode(byteBuffer))
        // 清除此缓冲区。 将位置设置为零,释放资源
        byteBuffer.clear()
    }

    // NIO-Channel - 非阻塞式
    @Test
    fun nIoChannelNoBlock() {
        val serverSocketChannel = ServerSocketChannel.open()
        serverSocketChannel.bind(InetSocketAddress(80))
        // 设定其非阻塞
        serverSocketChannel.configureBlocking(false)
        // 创建监听器
        val selector = Selector.open()
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT)
        selector.select();
        while (true) {
            selector.keys().forEach {
                if (it.isAcceptable) {
                    val socketChannel = serverSocketChannel.accept()
                    val byteBuffer = ByteBuffer.allocate(1024)
                    while (socketChannel.read(byteBuffer) != -1) {
                        // 重置缓冲
                        byteBuffer.flip()
                        //写入
                        socketChannel.write(byteBuffer)
                        // 清除已缓冲区已写完的数据
                        byteBuffer.clear()
                    }
                }
            }
            break
        }
        // 编译后,在终端运行: telnet localhost 80
    }


    // Okio-read
    @Test
    fun okIo() {
        val file = File("abc.txt")
        // val source = Okio.source(file)
        val readSource = file.source()
        val readBuffer = Buffer()
        // 往理面写
        readSource.read(readBuffer, 1024)
        println(readBuffer.readUtf8Line())
    }

    // Okio-read-buffer
    @Test
    fun okIoBuffer() {
        val file = File("abc.txt")
        val readBuffer = file.source().buffer()
        println(readBuffer.readUtf8Line())
    }

    @Test
    fun okIoWriteBuffer() {
        val file = File("abc.txt")
        val writeSink = file.sink()
        val writeBuffer = writeSink.buffer()
        writeBuffer.writeUtf8("我成为了高级技术专家")
        writeBuffer.flush()
    }

    @Test
    fun okIOCopy() {
        try {
            //Get BufferedSource
            val source = FileInputStream("abc_copy.txt").source().buffer()
            //Get BufferedSink
            val sink = FileOutputStream("abc.txt").sink().buffer()
            // 将abc_copy的内容父子到abc.txt
            source.readAll(sink)
            source.close()
            sink.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
    }
}