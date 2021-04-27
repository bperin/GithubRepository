package com.brianperin.squaredirectory.util

import com.brianperin.squaredirectory.model.response.Employee

/**
 * Validate required fields and type
 */
fun Employee.validate() {
    org.valiktor.validate(this) {
        validate(Employee::id).isNotNull()
        validate(Employee::fullName).isNotNull()
        validate(Employee::phoneNumber).hasSize(10)
        validate(Employee::emailAddress).isEmail()
    }
}

/**
 * Validate entire array
 */
fun Employees.validate() {
    this.employees.forEach { employee ->
        employee.validate()
    }
}

/**
 * Readable string for enum
 */
fun EmployeeType.toDisplay(): String {
    return when (this) {
        EmployeeType.FULL_TIME -> "Full time"
        EmployeeType.PART_TIME -> "Part time"
        EmployeeType.CONTRACTOR -> "Contractor"
    }
}