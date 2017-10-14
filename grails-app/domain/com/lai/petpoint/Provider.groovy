package com.lai.petpoint

class Provider {

    String name
    String address1
    String address2
    String city
    String state
    String zip
    String phone
    String email
    String contactName

    static constraints = {
        name (nullable: false)
        phone (phone: true)
        email (email: true)
        contactName (nullable: false)
        address1 (nullable: false)
        address2 (nullable: true)
        city (nullable: false)
        state (nullable: false)
        zip (nullable: false)
    }
}
