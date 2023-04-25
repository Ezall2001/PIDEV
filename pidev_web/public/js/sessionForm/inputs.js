/** @format */

import {
	priceInput,
	maxPlacesInput,
	dateInput,
	dateTextHelper,
	dateErrorHelper,
	timeInput,
	topicsInput,
	imageInput,
	resourceInput,
	resourceInputsContainer,
	courseInput,
	imageWrapper,
} from './dom.js'

let dateInputPickerInstance

const buildResourceInput = (index, defaulFile) =>
	`<div class="resource-input-wrapper">
			<input class="resource-input" id="resource-${index}" name="resource-${index}" type="file" data-height="150" data-max-file-size="1M" data-default-file="${defaulFile}"/>
		</div>`

const buildImageInput = defaultImage =>
	`<input id="image-input" name="image" type="file" data-height="90" data-max-file-size="1M" data-allowed-file-extensions="png jpg jpeg" accept=".png, .jpg, .jpeg" data-default-file="${defaultImage}"/>`

const generateTimeValues = () => {
	const values = []
	const minutesValues = [0, 15, 30, 45]
	const hoursValues = new Array(24).fill(0).map((val, index) => index)

	for (const hour of hoursValues)
		for (const minute of minutesValues)
			values.push(
				String(hour).padStart(2, 0) + ':' + String(minute).padStart(2, 0),
			)

	return values
}

const setTimeSliderDefault = (startTime, endTime) => {
	const timeSlider = timeInput.data('ionRangeSlider')
	let from = 72,
		to = 76

	if (startTime) {
		const dateTime = new Date(startTime)
		const hours = dateTime.getHours()
		const minutes = dateTime.getMinutes()
		from = hours * 4 + Math.floor((4 / 45) * minutes)
	}

	if (endTime) {
		const dateTime = new Date(endTime)
		const hours = dateTime.getHours()
		const minutes = dateTime.getMinutes()
		to = hours * 4 + Math.floor((4 / 45) * minutes)
	}

	timeSlider.update({
		from,
		to,
	})
}

const setImageDefault = imageLink => {
	imageWrapper.empty()
	const newImageInput = buildImageInput(
		imageLink == 'images/defaultSessionImage.jpg' ? '' : imageLink,
	)

	imageWrapper.append(newImageInput)
	imageInput().dropify({
		messages: {
			default: 'Déposez votre image ici',
			replace: 'Déposer une autre image pour remplacer celle-ci',
			remove: 'Retirer',
			error: "Ooops, Quelque chose s'est mal passé",
		},
		tpl: {
			filename: '',
		},
	})
}

const setResourceInputsDefaults = resources => {
	resourceInputsContainer.empty()
	let currResourceInputIndex = 0
	let firstResourceInput

	const addFileInput = defaultFile => {
		const defaultFilePath = defaultFile.filePath || ''
		const fileNamePreview = defaultFile
			? '<p class="dropify-filename">' + defaultFile.name + ' </p>'
			: '<p class="dropify-filename"><span class="file-icon"></span> <span class="dropify-filename-inner"></span></p>'

		currResourceInputIndex++
		if (firstResourceInput)
			firstResourceInput.off('change', handleAddResourceInput)

		const newInput = buildResourceInput(currResourceInputIndex, defaultFilePath)
		resourceInputsContainer.prepend(newInput)
		firstResourceInput = resourceInput().first()

		firstResourceInput
			.dropify({
				messages: {
					default: 'Déposez votre fichier ici',
					replace: 'Déposer une autre fichier pour remplacer celui-ci',
					remove: 'Retirer',
					error: "Ooops, Quelque chose s'est mal passé",
				},
				tpl: {
					filename: fileNamePreview,
				},
			})
			.on('change', handleAddResourceInput)
			.on(
				'dropify.afterClear',
				handleRemoveResourceInput(currResourceInputIndex),
			)
	}

	const handleAddResourceInput = () => addFileInput('')

	const handleRemoveResourceInput = index => () => {
		resourceInputsContainer
			.find('#resource-' + index)
			.parent()
			.parent()
			.remove()
	}

	if (resources) resources.forEach(addFileInput)
	addFileInput('')
}

export const setDefaultValues = session => {
	if (session) {
		priceInput.val(session.price)
		maxPlacesInput.val(session.maxPlaces)
		topicsInput.val(session.topics)
		dateInputPickerInstance.setDate([new Date(session.date)])

		courseInput
			.find(`option[value="${session.course.id}"]`)
			.attr('selected', 'selected')

		setTimeSliderDefault(session.startTime, session.endTime)
		setImageDefault(session.imageLink)
		setResourceInputsDefaults(session.resources)
	} else {
		priceInput.val(5)
		maxPlacesInput.val(5)
		topicsInput.val('')
		courseInput.find('option').removeAttr('selected')
		dateInputPickerInstance.setDate([])
		setTimeSliderDefault()
		setImageDefault('')
		setResourceInputsDefaults()
	}
}

export const initInputs = () => {
	dateErrorHelper.hide()

	priceInput.on('change', () => {
		const value = priceInput.val()
		if (value == '' || +value < 0) priceInput.val(0)
	})

	maxPlacesInput.on('change', function () {
		const value = maxPlacesInput.val()
		if (+value < 1) maxPlacesInput.val(1)
	})

	dateInputPickerInstance = dateInput.flatpickr({
		minDate: 'today',
		dateFormat: 'Y-m-d',
		mode: 'single',
		onChange: () => {
			dateTextHelper.show()
			dateErrorHelper.hide()
		},
	})

	timeInput.ionRangeSlider({
		grid: true,
		type: 'double',
		from: 72,
		to: 76,
		values: generateTimeValues(),
		min_interval: 2,
	})

	topicsInput.maxlength({
		warningClass: 'badge bg-success',
		limitReachedClass: 'badge bg-danger',
		alwaysShow: true,
	})

	setImageDefault('')
	setResourceInputsDefaults()
}
