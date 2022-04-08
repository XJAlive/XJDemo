package com.xj.demo.service

import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket
import java.nio.channels.SelectionKey
import java.nio.channels.Selector
import java.nio.channels.ServerSocketChannel

class SocketService {

    /**
     * socket服务端
     */
    fun initServer() {
        val serverSocket = ServerSocket(10000)
        val ip = InetAddress.getLocalHost()
        //等待客户端连接,BIO，阻塞同步
        while (true) {
            //监听新连接
            val socket = serverSocket.accept()
            Thread {
                socket.getInputStream().read()
                //....
                socket.shutdownInput()
                socket.close()
            }.start()
        }

    }

    fun initNioServer() {
        val channel = ServerSocketChannel.open()
        //配置成非阻塞模式，才能注册至selector
        channel.configureBlocking(false)
        channel.socket().bind(InetSocketAddress("192.168.6.40", 10000))
        val selector = Selector.open()
        channel.register(selector, SelectionKey.OP_ACCEPT)
        while (true) {
            //监听有新连接
            selector.select()
            val iterator = selector.selectedKeys().iterator()
            channel.register(iterator.next().selector(), SelectionKey.OP_READ)
        }
    }

    /**
     * socket客户端
     */
    fun initClient() {
        val socket = Socket("192.168.6.40", 10000)
        socket.connect(InetSocketAddress("192.168.6.40", 10000), 30000)//设置连接超时时间
        socket.getInputStream()
        socket.shutdownOutput()
        socket.close()
    }

}