/** @format */

import {initSessionForm} from '../sessionForm/sessionForm.js'
import {initResources} from './resources.js'

$(document).on('ready', () => (Dropzone.autoDiscover = false))

const initSession = () => {
	initSessionForm()
	initResources()
}

initSession()
