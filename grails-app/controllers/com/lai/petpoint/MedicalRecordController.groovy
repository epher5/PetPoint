package com.lai.petpoint

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class MedicalRecordController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        Pet pet
        def medicalRecords = []
        if (params.petId) {
            pet = Pet.get(params.petId)
            println("pet params ${pet}")
            medicalRecords = pet.medicalRecords
        }
        if (!pet) {
            respond([error: 'Invalid pet'], status: 400)
        }
        println("------ pet ${pet.id}")
        render(view: 'index', model:[medicalRecordList: medicalRecords, medicalRecordCount: medicalRecords.size(), petId: pet.id, pet: pet])
    }

    def show(MedicalRecord medicalRecord) {
        println("show params ${params}")
        Pet pet = Pet.findById(params.petId)
        respond medicalRecord, model: [pet: pet]
    }

    def create() {
        log.info("create params ${params}")
        println("create params ${params}")
        Pet pet = Pet.findById(params.petId)
        render(view: 'create', model: [medicalRecord: new MedicalRecord(params), petId: pet.id, pet: pet])
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
            respond medicalRecord.errors, view:'create', model: [medicalRecord: new MedicalRecord(params), petId: pet.id, pet: pet]
            return
        }

        medicalRecord.save flush:true

        Pet pet = Pet.findById(params.petId)
        pet.addToMedicalRecords(medicalRecord)
        pet.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'medicalRecord.label', default: 'MedicalRecord'), medicalRecord.id])
                redirect(action: "show", id: medicalRecord.id, params: [petId: pet.id])
            }
            '*' { respond medicalRecord, [status: CREATED, model: [petId: pet.id]] }
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
                redirect(action: "show", id: medicalRecord.id, params: [petId: pet.id])
            }
            '*' { respond medicalRecord, [status: OK, model: [petId: pet.id]] }
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
                redirect(action: "index", method:"GET", id: medicalRecord.id, params: [petId: pet.id])
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'medicalRecord.label', default: 'MedicalRecord'), params.id])
                redirect(action: "index", method: "GET", id: medicalRecord.id, params: [petId: pet.id])
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
