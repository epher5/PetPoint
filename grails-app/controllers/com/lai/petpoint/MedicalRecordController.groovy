package com.lai.petpoint

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional
import grails.plugin.springsecurity.annotation.Secured

@Secured('ROLE_USER')
@Transactional(readOnly = true)
class MedicalRecordController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond MedicalRecord.list(params), model:[medicalRecordCount: MedicalRecord.count()]
    }

    def show(MedicalRecord medicalRecord) {
        respond medicalRecord
    }

    def create() {
        respond new MedicalRecord(params)
    }

    @Transactional
    def save(MedicalRecord medicalRecord) {
        if (medicalRecord == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (medicalRecord.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond medicalRecord.errors, view:'create'
            return
        }

        medicalRecord.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'medicalRecord.label', default: 'MedicalRecord'), medicalRecord.id])
                redirect medicalRecord
            }
            '*' { respond medicalRecord, [status: CREATED] }
        }
    }

    def edit(MedicalRecord medicalRecord) {
        respond medicalRecord
    }

    @Transactional
    def update(MedicalRecord medicalRecord) {
        if (medicalRecord == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (medicalRecord.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond medicalRecord.errors, view:'edit'
            return
        }

        medicalRecord.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'medicalRecord.label', default: 'MedicalRecord'), medicalRecord.id])
                redirect medicalRecord
            }
            '*'{ respond medicalRecord, [status: OK] }
        }
    }

    @Transactional
    def delete(MedicalRecord medicalRecord) {

        if (medicalRecord == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        medicalRecord.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'medicalRecord.label', default: 'MedicalRecord'), medicalRecord.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'medicalRecord.label', default: 'MedicalRecord'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
