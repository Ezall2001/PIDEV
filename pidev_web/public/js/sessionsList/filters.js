/** @format */

const parseQueryParams = () => {
	const queryParams = Object.fromEntries(
		new URLSearchParams(window.location.search).entries(),
	)

	return queryParams
}

const encodeQueryParams = (base, queryParams) => {
	const href = base.concat(
		'?',
		Object.entries(queryParams)
			.map(kv => kv.map(encodeURIComponent).join('='))
			.join('&'),
	)

	return href
}

export const initFilters = () => {
	const queryParams = parseQueryParams()

	$('.creator-filter a').on('click', e => {
		e.preventDefault()
		const base = window.location.pathname

		queryParams['owner'] = 1
		const href = encodeQueryParams(base, queryParams)

		window.location.assign(href)
	})
}
