package com.lai.petpoint

class User {
    String name
    String email
    String username
    String password
    String phone

    static constraints = {
        name (blank:false, nullable:false, size:3..30, matches:"[a-zA-Z1-9_]+")
        email (email:true)
        username (nullable: false)
        password (password: true)
        phone (phone: true)
    }

    String toString(){
        return name
    }
}
