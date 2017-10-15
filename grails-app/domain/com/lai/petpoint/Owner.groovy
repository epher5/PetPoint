package com.lai.petpoint

class Owner {

    String name
    String phone
    String email

    static hasMany = [pets: Pet]

    static constraints = {
        name (nullable: false)
        phone (phone: true)
        email (email: true, unique: true)
    }

}
