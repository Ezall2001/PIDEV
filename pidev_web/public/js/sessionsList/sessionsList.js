/** @format */

import {initFilters} from './filters.js'
import {initSessionForm} from './sessionForm.js'

const sessionsListInit = () => {
	initFilters()
	initSessionForm()

	$('.creator-filter select').selectize({
		maxItems: null,
		valueField: 'id',
		labelField: 'name',
		searchField: 'name',
		options: [
			{id: 1, name: 'test'},
			{id: 2, name: 'test2'},
			{id: 3, name: 'test2'},
			{id: 4, name: 'test2'},
			{id: 5, name: 'test2'},
			{id: 6, name: 'test2'},
			{id: 7, name: 'test2'},
			{id: 8, name: 'test2'},
			{id: 9, name: 'test2'},
		],
		create: false,
	})
}

sessionsListInit()
