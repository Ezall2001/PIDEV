/** @format */

import {successButton, errorButton} from './dom.js'
import {parseQueryParams} from '../utils/urlParams.js'

export const initAlerts = () => {
	const queryParams = parseQueryParams()
	if (queryParams['success']) successButton.click()
	else if (queryParams['error']) errorButton.click()
}
