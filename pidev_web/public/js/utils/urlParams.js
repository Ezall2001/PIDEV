/** @format */

export const parseQueryParams = () => {
	const queryParams = Object.fromEntries(
		new URLSearchParams(window.location.search).entries(),
	)

	return queryParams
}

export const encodeQueryParams = (base, queryParams) => {
	const href = base.concat(
		'?',
		Object.entries(queryParams)
			.filter(kv => kv[1] != null)
			.filter(kv => kv[0] != 'success' && kv[0] != 'error')
			.map(kv => kv.map(encodeURIComponent).join('='))
			.join('&'),
	)

	return href
}
