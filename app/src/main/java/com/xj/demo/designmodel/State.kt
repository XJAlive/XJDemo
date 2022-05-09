package com.xj.demo.designmodel

interface UserState {

    fun order()

}

class LoginState : UserState {
    override fun order() {
        println("下单成功")
    }
}

class UnLoginState : UserState {
    override fun order() {
        println("抱歉，您未登录")
    }
}


interface IStateManage {
    fun login()
    fun logout()
}

class UserStateManage : IStateManage, UserState {
    var state: UserState = UnLoginState()

    override fun login() {
        state = LoginState()
    }

    override fun logout() {
        state = UnLoginState()
    }

    override fun order() {
        state.order()
    }

}

fun main() {
    val manage = UserStateManage()
    manage.login()//修改状态
    manage.order()
    manage.logout()
    manage.order()
}