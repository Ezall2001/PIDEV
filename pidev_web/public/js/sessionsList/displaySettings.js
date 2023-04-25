/** @format */

import {
	resultsContainer,
	girdViewButton,
	listViewButton,
	showNSelect,
	pageLink,
	sortDirectionButton,
	sortBySelect,
} from './dom.js'

import {parseQueryParams, encodeQueryParams} from '../utils/urlParams.js'

export const initDisplaySettings = () => {
	const queryParams = parseQueryParams()
	const base = window.location.pathname

	const applySettings = () => {
		const href = encodeQueryParams(base, queryParams)
		window.location.assign(href)
	}

	girdViewButton.on('click', () => {
		resultsContainer.last().removeClass('active')
		resultsContainer.first().addClass('active')
		girdViewButton.removeClass('btn-outline-primary')
		girdViewButton.addClass('btn-primary')
		listViewButton.removeClass('btn-primary')
		listViewButton.addClass('btn-outline-primary')
	})

	listViewButton.on('click', () => {
		resultsContainer.first().removeClass('active')
		resultsContainer.last().addClass('active')
		listViewButton.removeClass('btn-outline-primary')
		listViewButton.addClass('btn-primary')
		girdViewButton.removeClass('btn-primary')
		girdViewButton.addClass('btn-outline-primary')
	})

	showNSelect.on('change', e => {
		const value = e.target.value
		queryParams['limit'] = value
		applySettings()
	})

	pageLink.on('click', e => {
		e.preventDefault()
		const value = $(e.target).attr('data-page')
		queryParams['page'] = value
		applySettings()
	})

	sortDirectionButton.on('click', () => {
		const currentDirection = sortDirectionButton.attr('data-current-direction')
		if (currentDirection === 'ASC') queryParams['sortDirection'] = 'desc'
		else queryParams['sortDirection'] = 'asc'
		applySettings()
	})

	sortBySelect.on('change', e => {
		const value = e.target.value
		queryParams['sortBy'] = value
		applySettings()
	})
}
