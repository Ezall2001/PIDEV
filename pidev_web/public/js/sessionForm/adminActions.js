/** @format */

import {blockButton} from './dom.js'

export const initAdminActions = () => {
	blockButton.on('click', e => {
		const button = $(e.target)
		const id = button.attr('data-id')
		const isBlocked = button.attr('data-is-blocked') == '1'

		let href = '/admin/'
		if (isBlocked) href += 'unblockSession'
		else href += 'blockSession'
		href += '/' + id

		fetch(href, {method: 'PUT'})
		button.toggleClass('btn-outline-danger')
		button.toggleClass('btn-danger')
	})
}
