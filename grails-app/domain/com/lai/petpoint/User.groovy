package com.lai.petpoint

class User {
    def springSecurityService

    String name
    String email
    String username
    String password
    String phone
    Boolean enabled = true
    Boolean accountExpired = false
    Boolean accountLocked = false
    Boolean passwordExpired = false

    static constraints = {
        name (blank:false, nullable:false, size:3..30)
        email (email:true)
        username (nullable: false)
        password (password: true)
        phone (phone: true)
    }

    static mapping = {
        table "`user`"
    }

    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this).collect { it.role }
    }

    def beforeInsert() {
        encodePassword()
    }
    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }
    protected void encodePassword() {
        password = springSecurityService.encodePassword(password)
    }
    String toString(){
        return name
    }
}
