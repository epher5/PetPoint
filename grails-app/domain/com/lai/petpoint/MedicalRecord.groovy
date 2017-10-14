package com.lai.petpoint

class MedicalRecord {

    String diagnosis
    String treatment
    Date date

    static constraints = {
        diagnosis (nullable: false)
        treatment (nullable: false)
        date (nullable: false)
    }
}
