$(document).ready(
		function() {
			$('ul.vocab-tree').treed();
			$("form select#root-uri-selector").change(function(e) {
				$("ul.vocab-tree").replaceWith($("#preloader").show());
				$(e.target).closest("form").submit();
			})
			$('#graph-control-tabs a').click(function(e) {
				e.preventDefault()
				$(this).tab('show')
			});
			/**
			 * http://www.bootply.com/ljIOxm3qDi
			 */
			var states = [ 'Alabama', 'Alaska', 'Arizona', 'Arkansas',
					'California', 'Colorado', 'Connecticut', 'Delaware',
					'Florida', 'Georgia', 'Hawaii', 'Idaho', 'Illinois',
					'Indiana', 'Iowa', 'Kansas', 'Kentucky', 'Louisiana',
					'Maine', 'Maryland', 'Massachusetts', 'Michigan',
					'Minnesota', 'Mississippi', 'Missouri', 'Montana',
					'Nebraska', 'Nevada', 'New Hampshire', 'New Jersey',
					'New Mexico', 'New York', 'North Carolina', 'North Dakota',
					'Ohio', 'Oklahoma', 'Oregon', 'Pennsylvania',
					'Rhode Island', 'South Carolina', 'South Dakota',
					'Tennessee', 'Texas', 'Utah', 'Vermont', 'Virginia',
					'Washington', 'West Virginia', 'Wisconsin', 'Wyoming' ];

			// constructs the suggestion engine
			var labels = new Bloodhound({
				datumTokenizer : Bloodhound.tokenizers.obj.whitespace('value'),
				queryTokenizer : Bloodhound.tokenizers.whitespace,
				remote : {
					url : 'search/autocomplete/%QUERY.json',
					wildcard : '%QUERY'
				}
			});

			$('#query').typeahead({
				hint : true,
				highlight : true,
				minLength : 1
			}, {
				name : 'states',
				source : labels
			});
			$('[data-toggle="tooltip"]').tooltip({
				'placement' : 'top'
			});
			$('[data-toggle="popover"]').popover({
				trigger : 'hover',
				'placement' : 'top'
			});
		});
