package com.lai.petpoint

import grails.transaction.Transactional

@Transactional(readOnly = true)
class HomeController {

    def index() { }
}
