$(document).ready(function() {
	$("#buttonAdd2Cart").on("click", function(e){
		addToCart();
	});
});

function addToCart() {
	cantidad = $("#cantidad" + productoId).val();
	
	url = contextPath + "carrito/add/" + productoId + "/" + cantidad;
	
	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(crsfHeaderName, csrfValue);
		}
	}).done(function(response){
		$("#modalTitle").text("Carrito de Compras");
		$("#modalBody").text(response);
		$("#myModal").modal();
	}).fail(function() {
		$("#modalTitle").text("Carrito de Compras");
		$("#modalBody").text("Error mientras se agrego el producto al carrito de compras.");
		$("#myModal").modal();
	});
}