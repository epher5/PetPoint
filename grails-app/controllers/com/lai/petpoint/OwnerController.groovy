package com.lai.petpoint

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class OwnerController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params.sort = 'name'
        def owners = Owner.list(params)
//        respond owners, model:[ownerCount: Owner.count(), owners: owners]
//        def owners = Owner.sort{ }
        render(view: 'index', model:[ownerCount: Owner.count(), owners: owners])
    }

    def show(Owner owner) {
        respond owner, model: [ownerId: owner.id]

//        render(view: 'show', model: [owner: owner])
    }

    def create() {
        respond new Owner(params)
    }

    @Transactional
    def save(Owner owner) {
        if (owner == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (owner.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond owner.errors, view:'create'
            return
        }

        owner.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'owner.label', default: 'Owner'), owner.id])
                redirect owner
            }
            '*' { respond owner, [status: CREATED] }
        }
    }

    def edit(Owner owner) {
        respond owner
    }

    @Transactional
    def update(Owner owner) {
        if (owner == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (owner.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond owner.errors, view:'edit'
            return
        }

        owner.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'owner.label', default: 'Owner'), owner.id])
                redirect owner
            }
            '*'{ respond owner, [status: OK] }
        }
    }

    @Transactional
    def delete(Owner owner) {

        if (owner == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        owner.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'owner.label', default: 'Owner'), owner.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'owner.label', default: 'Owner'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
