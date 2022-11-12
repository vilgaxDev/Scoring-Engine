package com.scoringengine.model

import com.fasterxml.jackson.annotation.JsonIgnore
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "anywair_mobile_all_loan_books")
class LoanBook : java.io.Serializable {

    @Id
    @Column(name = "loanId", nullable = false, unique = true)
    var loanId: String = UUID.randomUUID().toString()
    var numberOfMonths: Int? = 0
    var finalDate: LocalDateTime? = null

    var loanAmount: Int? = 0

    var totalPaid: Int? = 0

    var loanStatus: String? = "pending"
    var approvalDate: LocalDateTime? = null
    var requestedDate: LocalDateTime? = LocalDateTime.now(ZoneId.of("Africa/Nairobi"))
    var lastModifiedDate: LocalDateTime? = LocalDateTime.now(ZoneId.of("Africa/Nairobi"))

    @Column(name = "approval_loan_status", nullable = false)
    var approvalStatus: String? = "pending"

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "customerId", nullable = false)
    var customer: Customer? = null


}
