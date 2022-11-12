package com.scoringengine.dto

import org.springframework.stereotype.Component
import java.io.Serializable

@Component
class CreateAccountRequest : Serializable {
    var customerNumber: String = ""
}

@Component
class LoanRequest : Serializable {
    var customerNumber: String = ""
    var amount: Int = 0
}