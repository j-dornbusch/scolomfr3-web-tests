$(document)
		.ready(
				function() {
					$('html').addClass(
							$.fn.details.support ? 'details' : 'no-details');
					$('ul.vocab-tree').treed();
					$("form select#root-uri-selector").change(function(e) {
						$("ul.vocab-tree").replaceWith($("#preloader").show());
						$(e.target).closest("form").submit();
					})
					$("select.target-selector").change(function(e) {
						$(e.target).closest("form").submit();
						return false;
					});
					$('#graph-control-tabs a').click(function(e) {
						e.preventDefault()
						$(this).tab('show')
					});
					/**
					 * http://www.bootply.com/ljIOxm3qDi
					 */
					var labels = new Bloodhound({
						datumTokenizer : Bloodhound.tokenizers.obj
								.whitespace('value'),
						queryTokenizer : Bloodhound.tokenizers.whitespace,
						remote : {
							url : 'search/autocomplete?query=%QUERY',
							wildcard : '%QUERY'
						}
					});

					$('[data-toggle="tooltip"]').tooltip({
						'placement' : 'top',
						"html" : true
					});
					$('[data-toggle="popover"]').popover({
						trigger : 'click',
						'placement' : 'top',
						"html" : true
					});
					var updatelabelSearchField = function(e) {
						var val = $('#search-type-toggle').prop('checked');
						switch (val) {
						case true:
							$("#uri").addClass("hidden-form-control")
									.removeAttr("name").hide();
							$("#query").removeClass("hidden-form-control")
									.attr("name", "query").show();
							$('#query').typeahead({
								hint : true,
								highlight : true,
								minLength : 1
							}, {
								source : labels
							});
							break;
						case false:
							$("#query").addClass("hidden-form-control")
									.removeAttr("name");
							$('#query').typeahead('destroy').hide();
							$("#uri").removeClass("hidden-form-control").attr(
									"name", "uri").show();
							break;
						}
					};
					$('#search-type-toggle').bootstrapToggle({
						on : 'Recherche plein texte',
						off : 'Recherche par uri',
						width : 200,
						height : 40
					}).change(updatelabelSearchField);
					updatelabelSearchField();
					$('details').details();
				});
