/** @format */

import {resources, downloadAction, downloadAnchor} from './dom.js'

const resourcePreview = `
<div class="dropify-preview">
  <span class="dropify-render"></span>
  <div class="dropify-infos">
    <div class="dropify-infos-inner">
    </div>
  </div>
</div>`

export const initResources = () => {
	downloadAction.on('click', () =>
		resources.each(index => resources.eq(index).click()),
	)

	resources.each(index => {
		const resource = resources.eq(index)

		resource
			.dropify({
				tpl: {
					filename:
						'<p class="dropify-filename">' +
						resource.attr('data-file-name') +
						' </p>',
					preview: resourcePreview,
				},
			})
			.on('click', e => {
				e.preventDefault()
				const href = resource.attr('data-default-file')
				downloadAnchor.attr('href', href)
				downloadAnchor[0].click()
			})
	})
}
