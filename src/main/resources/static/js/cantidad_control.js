$(document).ready(function() {
	$(".minusButton").on("click", function(evt) {
		evt.preventDefault();
		productoId = $(this).attr("pid");
		valor = $("#cantidad" + productoId);
		nuevoValor = parseInt(valor.val()) - 1;
		if (nuevoValor > 0) valor.val(nuevoValor);
	});
	
	$(".plusButton").on("click", function(evt) {
		evt.preventDefault();
		productoId = $(this).attr("pid");
		valor = $("#cantidad" + productoId);
		nuevoValor = parseInt(valor.val()) + 1;
		if (nuevoValor < 10) valor.val(nuevoValor);
	});
});