package com.lai.petpoint

class MedicalRecord {

    String vaccinations
    String comments
    Date date

    static belongsTo = [pet: Pet]

    static constraints = {
        vaccinations (nullable: false)
        comments (nullable: true)
        date (nullable: false)
    }
}
