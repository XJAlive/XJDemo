package com.xj.demo.service

import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket

class SocketService {

    /**
     * socket服务端
     */
    fun initServer() {
        val serverSocket = ServerSocket(10000)
        val ip = InetAddress.getLocalHost()
        //等待客户端连接
        while (true) {
            val socket = serverSocket.accept()
            val ips = socket.getInputStream()
            //....
            socket.shutdownInput()
            socket.close()
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