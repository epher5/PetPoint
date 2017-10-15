package com.lai.petpoint

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BewareGeoController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        def aggressives = AggressivePet.findAll()
        render(view: 'index', model:[aggressives: aggressives])
    }

    def show(BewareGeo bewareGeo) {
        respond bewareGeo
    }

    def create() {
        respond new BewareGeo(params)
    }

    @Transactional
    def save(BewareGeo bewareGeo) {
        if (bewareGeo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (bewareGeo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond bewareGeo.errors, view:'create'
            return
        }

        bewareGeo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'bewareGeo.label', default: 'BewareGeo'), bewareGeo.id])
                redirect bewareGeo
            }
            '*' { respond bewareGeo, [status: CREATED] }
        }
    }

    def edit(BewareGeo bewareGeo) {
        respond bewareGeo
    }

    @Transactional
    def update(BewareGeo bewareGeo) {
        if (bewareGeo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (bewareGeo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond bewareGeo.errors, view:'edit'
            return
        }

        bewareGeo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'bewareGeo.label', default: 'BewareGeo'), bewareGeo.id])
                redirect bewareGeo
            }
            '*'{ respond bewareGeo, [status: OK] }
        }
    }

    @Transactional
    def delete(BewareGeo bewareGeo) {

        if (bewareGeo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        bewareGeo.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'bewareGeo.label', default: 'BewareGeo'), bewareGeo.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'bewareGeo.label', default: 'BewareGeo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
