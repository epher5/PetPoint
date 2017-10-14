package com.lai.petpoint

class Pet {

    String name
    String species
    String breed
    Date birthdate

    static hasMany = [medicalRecords: MedicalRecord]

    static constraints = {
        name (nullable: false)
        species (nullable: false)
        breed (nullable: false)
        birthdate (nullable: false)
    }
}
