package com.lai.petpoint

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AbuserGeoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        def abusers = AnimalAbuser.findAll()
        render(view: 'index', model:[abusers: abusers])
    }

    def show(AbuserGeo abuserGeo) {
        respond abuserGeo
    }

    def create() {
        respond new AbuserGeo(params)
    }

    @Transactional
    def save(AbuserGeo abuserGeo) {
        if (abuserGeo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (abuserGeo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond abuserGeo.errors, view:'create'
            return
        }

        abuserGeo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'abuserGeo.label', default: 'AbuserGeo'), abuserGeo.id])
                redirect abuserGeo
            }
            '*' { respond abuserGeo, [status: CREATED] }
        }
    }

    def edit(AbuserGeo abuserGeo) {
        respond abuserGeo
    }

    @Transactional
    def update(AbuserGeo abuserGeo) {
        if (abuserGeo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (abuserGeo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond abuserGeo.errors, view:'edit'
            return
        }

        abuserGeo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'abuserGeo.label', default: 'AbuserGeo'), abuserGeo.id])
                redirect abuserGeo
            }
            '*'{ respond abuserGeo, [status: OK] }
        }
    }

    @Transactional
    def delete(AbuserGeo abuserGeo) {

        if (abuserGeo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        abuserGeo.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'abuserGeo.label', default: 'AbuserGeo'), abuserGeo.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'abuserGeo.label', default: 'AbuserGeo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
