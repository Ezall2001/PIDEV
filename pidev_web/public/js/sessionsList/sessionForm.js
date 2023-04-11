/** @format */

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

export const initSessionForm = () => {
	const courseInput = $('#course-select')
	const priceInput = $('#price-input')
	const maxPlacesInput = $('#max-places-input')
	const dateInput = $('#dates-input')
	const dateHelpers = $('.date-helpers')
	const timeInput = $('#time-input')
	const topicsInput = $('#topics-input')
	const confirmButton = $('.confirm-session-form')

	priceInput.on('change', () => {
		const value = priceInput.val()
		if (value == '' || +value < 0) priceInput.val(0)
	})

	maxPlacesInput.on('change', function () {
		const value = maxPlacesInput.val()
		if (+value < 1) maxPlacesInput.val(1)
	})

	dateInput.flatpickr({
		minDate: 'today',
		dateFormat: 'Y-m-d',
		mode: 'multiple',
	})
	dateHelpers.find('.error-text').hide()

	timeInput.ionRangeSlider({
		grid: !0,
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

	confirmButton.on('click', () => {
		if (dateInput.val() == '') {
			dateHelpers.find('.help-text').hide()
			dateHelpers.find('.error-text').show()
			return
		}

		const form = document.createElement('form')
		form.setAttribute('method', 'POST')
		form.setAttribute('action', '/sessionsList/addSession')

		form.appendChild(courseInput.get(0))
		form.appendChild(priceInput.get(0))
		form.appendChild(maxPlacesInput.get(0))
		form.appendChild(dateInput.get(0))
		form.appendChild(timeInput.get(0))
		form.appendChild(topicsInput.get(0))

		$(document.body).append(form)

		form.submit()
	})
}
