package com.lai.petpoint

class MedicalRecord {

    String vaccinations
    String treatment
    Date date

    static constraints = {
        vaccinations (nullable: false)
        treatment (nullable: false)
        date (nullable: false)
    }
}
