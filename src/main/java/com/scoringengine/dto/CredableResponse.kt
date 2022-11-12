package com.scoringengine.dto

import org.springframework.stereotype.Component
import java.io.Serializable

@Component
class CredableResponse : Serializable {
    var title: String = ""
    var message: String = ""
    var detail: String = ""
    var data: Any? = null
}
