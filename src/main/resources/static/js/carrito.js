$(document).ready(function() {

	$(".minusButton").on("click", function(evt) {
		evt.preventDefault();
		decreaseQuantity($(this));
	});
	
	$(".plusButton").on("click", function(evt) {
		evt.preventDefault();
		increaseQuantity($(this));
	});
	
	$(".link-remove").on("click", function(evt) {
		evt.preventDefault();
		removeFromCart($(this));
	});
	
	updateTotal();
});

function removeFromCart(link) {
	url = link.attr("href");

	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(crsfHeaderName, csrfValue);
		}
	}).done(function(response) {
	
		$("#modalTitle").text("Carrito de compras");
		if(response.includes("removido")) {
			$("#myModal").on("hide.bs.modal", function(e) {
				rowNumber = link.attr("rowNumber");
				removeProduct(rowNumber);
				updateTotal();
			});
		}
		
		$("#modalBody").text(response);
		$("#myModal").modal();
		
	}).fail(function() {
		$("#modalTitle").text("Carrito de compras");
		$("#modalBody").text("Error mientras se agrego el producto al carrito de compras.");
		$("#myModal").modal();
	});
}

function removeProduct(rowNumber) {
	rowId = "row" + rowNumber;
	$("#" + rowId).remove();
}

function decreaseQuantity(link) {
	productoId = link.attr("pid");
	valor = $("#cantidad" + productoId);
	nuevoValor = parseInt(valor.val()) - 1;
	if (nuevoValor > 0){
		valor.val(nuevoValor);
		updateQuantity(productoId, nuevoValor);
	}
}

function increaseQuantity(link) {
	productoId = link.attr("pid");
	valor = $("#cantidad" + productoId);
	nuevoValor = parseInt(valor.val()) + 1;
	if (nuevoValor < 10) {
		valor.val(nuevoValor);
		updateQuantity(productoId, nuevoValor);
	}
}

function updateQuantity(productoId, cantidad) {
	
	url = contextPath + "carrito/update/" + productoId + "/" + cantidad;
	
	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr) {
			xhr.setRequestHeader(crsfHeaderName, csrfValue);
		}
	}).done(function(newSubtotal) {
		updateSubtotal(newSubtotal, productoId);
		updateTotal();
	}).fail(function() {
		$("#modalTitle").text("Carrito de compras");
		$("#modalBody").text("Error mientras se agrego el producto al carrito de compras.");
		$("#myModal").modal();
	});
	
}

function updateSubtotal(newSubtotal, productoId) {
	$("#subtotal" + productoId).text(newSubtotal);
}
function updateTotal() {
	total = 0.0;
	
	$(".productoSubtotal").each(function(index, element) {
		total = total + parseFloat(element.innerHTML);
	});
	
	$("#totalAmount").text("S/. " + total);
}