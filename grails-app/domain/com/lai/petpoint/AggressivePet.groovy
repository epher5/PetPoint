package com.lai.petpoint

class AggressivePet {

    String breed
    String address1
    String address2
    String city
    String state
    String zip
    String owner
    String phone
    String email
    String aggression

    static constraints = {
        breed (nullable: false)
        address1 (nullable: false)
        address2 (nullable: true)
        city (nullable: false)
        state (nullable: false)
        zip (nullable: false)
        aggression (nullable: true)
        owner (nullable: true)
        phone (nullable: true, phone: true)
        email (nullable: true, email: true)
    }

    String getFullAddress() {
        "${address1}${address2 ?: ''}, ${city}, ${state} ${zip}"
    }
}
