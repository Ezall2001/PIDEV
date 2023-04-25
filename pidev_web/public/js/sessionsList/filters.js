/** @format */

import {
	ownedFilterButton,
	baughtFilterButton,
	resetFilterButton,
	creatorFiterSelect,
	priceFilterInput,
	courseFilterInput,
	dateFilterInput,
} from './dom.js'

import {parseQueryParams, encodeQueryParams} from '../utils/urlParams.js'

const generatePriceValues = () =>
	new Array(11).fill(0).map((val, index) => index * 10)

export const initFilters = () => {
	let queryParams = parseQueryParams()
	const base = window.location.pathname

	const applyFilters = () => {
		const href = encodeQueryParams(base, queryParams)
		window.location.assign(href)
	}

	ownedFilterButton.on('click', e => {
		e.preventDefault()
		queryParams = {owner: 1}
		applyFilters()
	})

	baughtFilterButton.on('click', e => {
		e.preventDefault()
		queryParams = {baught: 1}
		applyFilters()
	})

	resetFilterButton.on('click', e => {
		e.preventDefault()
		queryParams = {}
		applyFilters()
	})

	creatorFiterSelect.selectize({
		maxItems: 1,
		create: false,
		onChange: value => {
			queryParams['creator'] = value === 'all' ? null : value
			applyFilters()
		},
	})

	priceFilterInput.ionRangeSlider({
		grid: true,
		from: +priceFilterInput.attr('data-default') / 10,
		values: generatePriceValues(),
		postfix: ' DT',
		max_postfix: '+',
		onFinish: ({from_value}) => {
			queryParams['priceLimit'] = from_value
			applyFilters()
		},
	})

	courseFilterInput.selectize({
		maxItems: 1,
		create: false,
		onChange: value => {
			queryParams['course'] = value === 'all' ? null : value
			applyFilters()
		},
	})

	dateFilterInput.flatpickr({
		dateFormat: 'Y-m-d',
		onChange: (dates, dateStr) => {
			if (dateStr) queryParams['date'] = dateStr
			else queryParams['date'] = null
			applyFilters()
		},
	})
}
