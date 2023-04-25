/** @format */

import {
	modalTitle,
	priceInput,
	maxPlacesInput,
	dateInput,
	dateTextHelper,
	dateErrorHelper,
	timeInput,
	topicsInput,
	imageInput,
	courseInput,
	resourceInput,
	addButton,
	modifyButton,
	deleteButton,
	confirmButton,
	confirmDeleteButton,
	participateButton,
} from './dom.js'

import {setDefaultValues} from './inputs.js'

export const initActions = () => {
	let confirmSessionId = null
	const {pathname} = window.location

	participateButton.on('click', e => {
		const id = $(e.target).attr('data-session-id')
		const href = '/participate/' + id
		window.location.assign(href)
	})

	addButton.on('click', () => {
		modalTitle.text('Créer Séance')
		confirmSessionId = null
		setDefaultValues()
	})

	modifyButton.on('click', e => {
		modalTitle.text('Modifier Séance')
		const id = $(e.target).attr('data-session-id')
		const href = '/getSessionForm/' + id
		confirmSessionId = id
		fetch(href)
			.then(res => res.json())
			.then(setDefaultValues)
	})

	deleteButton.on('click', e => {
		const sessionId = $(e.target).attr('data-session-id')
		confirmDeleteButton.attr('data-session-id', sessionId)
	})

	confirmDeleteButton.on('click', () => {
		const sessionId = confirmDeleteButton.attr('data-session-id')
		const href = `/removeSession/${sessionId}`
		window.location.assign(href)
	})

	confirmButton.on('click', () => {
		if (dateInput.val() == '') {
			dateTextHelper.hide()
			dateErrorHelper.show()
			return
		}

		const href = '/persistSession?redirect=' + pathname
		const resourceInputs = resourceInput().slice(1)

		const idInput = document.createElement('input')
		idInput.value = confirmSessionId || ''
		idInput.name = 'id'

		const form = document.createElement('form')
		form.setAttribute('method', 'POST')
		form.setAttribute('enctype', 'multipart/form-data')
		form.setAttribute('action', href)
		form.appendChild(idInput)
		form.appendChild(courseInput.get(0))
		form.appendChild(priceInput.get(0))
		form.appendChild(maxPlacesInput.get(0))
		form.appendChild(dateInput.get(0))
		form.appendChild(timeInput.get(0))
		form.appendChild(topicsInput.get(0))
		form.appendChild(imageInput().get(0))
		resourceInputs.each(index => form.appendChild(resourceInputs.get(index)))

		$(document.body).append(form)
		form.submit()
	})
}
