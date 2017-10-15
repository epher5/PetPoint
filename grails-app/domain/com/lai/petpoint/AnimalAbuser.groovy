package com.lai.petpoint

class AnimalAbuser {

    String name
    String address1
    String address2
    String city
    String state
    String zip
    String phone
    String email
    String offense

    static constraints = {
        name (nullable: false)
        address1 (nullable: false)
        address2 (nullable: true)
        city (nullable: false)
        state (nullable: false)
        zip (nullable: false)
        phone (nullable: true)
        email (nullable: true)
        offense (nullable: false)
    }

    String getFullAddress() {
        "${address1}${address2 ?: ''}, ${city}, ${state} ${zip}"
    }
}
