/** @format */

import {initFilters} from './filters.js'
import {initDisplaySettings} from './displaySettings.js'
import {initAdminActions} from '../sessionForm/adminActions.js'

const initAdminSessionsList = () => {
	initDisplaySettings()
	initFilters()
	initAdminActions()
}

initAdminSessionsList()
