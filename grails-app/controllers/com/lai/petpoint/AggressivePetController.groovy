package com.lai.petpoint

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AggressivePetController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond AggressivePet.list(params), model:[aggressivePetCount: AggressivePet.count()]
    }

    def show(AggressivePet aggressivePet) {
        respond aggressivePet
    }

    def create() {
        respond new AggressivePet(params)
    }

    @Transactional
    def save(AggressivePet aggressivePet) {
        if (aggressivePet == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (aggressivePet.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond aggressivePet.errors, view:'create'
            return
        }

        aggressivePet.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'aggressivePet.label', default: 'AggressivePet'), aggressivePet.id])
                redirect aggressivePet
            }
            '*' { respond aggressivePet, [status: CREATED] }
        }
    }

    def edit(AggressivePet aggressivePet) {
        respond aggressivePet
    }

    @Transactional
    def update(AggressivePet aggressivePet) {
        if (aggressivePet == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (aggressivePet.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond aggressivePet.errors, view:'edit'
            return
        }

        aggressivePet.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'aggressivePet.label', default: 'AggressivePet'), aggressivePet.id])
                redirect aggressivePet
            }
            '*'{ respond aggressivePet, [status: OK] }
        }
    }

    @Transactional
    def delete(AggressivePet aggressivePet) {

        if (aggressivePet == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        aggressivePet.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'aggressivePet.label', default: 'AggressivePet'), aggressivePet.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'aggressivePet.label', default: 'AggressivePet'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
