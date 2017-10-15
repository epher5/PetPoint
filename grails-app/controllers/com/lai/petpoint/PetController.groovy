package com.lai.petpoint

import grails.transaction.Transactional

import static org.springframework.http.HttpStatus.*

@Transactional(readOnly = true)
class PetController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        log.info("index params ${params}")
        params.max = Math.min(max ?: 10, 100)
        params.sort = 'name'
        Owner owner
        def pets = []
        if (params.ownerId) {
            owner = Owner.get(params.ownerId)
            println("owner params ${owner}")
            pets = owner.pets
        }
        if (!owner) {
            respond([error: 'Invalid owner'], status: 400)
        }
        println("------ owner ${owner.id}")
        render(view: 'index', model:[petList: pets, petCount: pets.size(), ownerId: owner.id, owner: owner])
    }

    def show(Pet pet) {
        println("show params ${params}")
        Owner owner = Owner.findById(params.ownerId)
        respond pet, model: [owner: owner]
    }

    def create() {
        log.info("create params ${params}")
        println("create params ${params}")
        Owner owner = Owner.findById(params.ownerId)
        render(view: 'create', model: [pet: new Pet(params), ownerId: owner.id, owner: owner])
    }

    @Transactional
    def save(Pet pet) {
        println("create params ${params}")
        if (pet == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (pet.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pet.errors, view:'create', model: [pet: new Pet(params), ownerId: owner.id, owner: owner]
            return
        }

        pet.save flush:true

        Owner owner = Owner.findById(params.ownerId)
        owner.addToPets(pet)
        owner.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'pet.label', default: 'Pet'), pet.id])
                redirect(action: "show", id: pet.id, params: [ownerId: owner.id])
            }
            '*' { respond pet, [status: CREATED, model: [ownerId: owner.id]] }
        }
    }

    def edit(Pet pet) {
        respond pet
    }

    @Transactional
    def update(Pet pet) {
        if (pet == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (pet.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond pet.errors, view:'edit'
            return
        }

        pet.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'pet.label', default: 'Pet'), pet.id])
                redirect(action: "show", id: pet.id, params: [ownerId: owner.id])
            }
            '*' { respond pet, [status: OK, model: [ownerId: owner.id]] }
        }
    }

    @Transactional
    def delete(Pet pet) {

        if (pet == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        pet.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'pet.label', default: 'Pet'), pet.id])
                redirect(action: "index", method:"GET", id: pet.id, params: [ownerId: owner.id])
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'pet.label', default: 'Pet'), params.id])
                redirect(action: "index", method:"GET", id: pet.id, params: [ownerId: owner.id])
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
