package com.scoringengine.dto

import org.springframework.stereotype.Component
import java.io.Serializable

@Component
class BankRequest : Serializable {
    var customerNumber: String = ""
}


@Component
class SubscribeRequest : Serializable {
    var customerNumber: String = ""
    var username: String = ""
    var password: String = ""
}
