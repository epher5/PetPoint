package com.lai.petpoint

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AnimalAbuserController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond AnimalAbuser.list(params), model:[animalAbuserCount: AnimalAbuser.count()]
    }

    def show(AnimalAbuser animalAbuser) {
        respond animalAbuser
    }

    def create() {
        respond new AnimalAbuser(params)
    }

    @Transactional
    def save(AnimalAbuser animalAbuser) {
        if (animalAbuser == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (animalAbuser.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond animalAbuser.errors, view:'create'
            return
        }

        animalAbuser.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'animalAbuser.label', default: 'AnimalAbuser'), animalAbuser.id])
                redirect animalAbuser
            }
            '*' { respond animalAbuser, [status: CREATED] }
        }
    }

    def edit(AnimalAbuser animalAbuser) {
        respond animalAbuser
    }

    @Transactional
    def update(AnimalAbuser animalAbuser) {
        if (animalAbuser == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (animalAbuser.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond animalAbuser.errors, view:'edit'
            return
        }

        animalAbuser.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'animalAbuser.label', default: 'AnimalAbuser'), animalAbuser.id])
                redirect animalAbuser
            }
            '*'{ respond animalAbuser, [status: OK] }
        }
    }

    @Transactional
    def delete(AnimalAbuser animalAbuser) {

        if (animalAbuser == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        animalAbuser.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'animalAbuser.label', default: 'AnimalAbuser'), animalAbuser.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'animalAbuser.label', default: 'AnimalAbuser'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
