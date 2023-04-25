/** @format */

import {initFilters} from './filters.js'
import {initSessionForm} from '../sessionForm/sessionForm.js'
import {initDisplaySettings} from './displaySettings.js'

$(document).on('ready', () => (Dropzone.autoDiscover = false))

const initSessionsList = () => {
	initSessionForm()
	initDisplaySettings()
	initFilters()
}

initSessionsList()
