package com.xj.demo.service

class Node {

    var pre: Node? = null
    var next: Node? = null
}

class LinkNode {

    fun add(node: Node) {
        val preNode = Node()
        node.pre = preNode


    }

}