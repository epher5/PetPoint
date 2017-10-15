package com.lai.petpoint

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class ProviderController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Provider.list(params), model:[providerCount: Provider.count()]
    }

    def show(Provider provider) {
        respond provider
    }

    def create() {
        respond new Provider(params)
    }

    @Transactional
    def save(Provider provider) {
        if (provider == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (provider.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond provider.errors, view:'create'
            return
        }

        provider.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'provider.label', default: 'Provider'), provider.id])
                redirect provider
            }
            '*' { respond provider, [status: CREATED] }
        }
    }

    def edit(Provider provider) {
        respond provider
    }

    @Transactional
    def update(Provider provider) {
        if (provider == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (provider.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond provider.errors, view:'edit'
            return
        }

        provider.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'provider.label', default: 'Provider'), provider.id])
                redirect provider
            }
            '*'{ respond provider, [status: OK] }
        }
    }

    @Transactional
    def delete(Provider provider) {

        if (provider == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        provider.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'provider.label', default: 'Provider'), provider.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'provider.label', default: 'Provider'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
